package com.dstz.cms.core.manager.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dstz.base.api.dto.PageListDTO;
import com.dstz.base.api.dto.QueryParamDTO;
import com.dstz.base.common.constats.InnerMsgEnum;
import com.dstz.base.common.enums.GlobalApiCodes;
import com.dstz.base.common.exceptions.BusinessException;
import com.dstz.base.common.exceptions.BusinessMessage;
import com.dstz.base.common.utils.BeanCopierUtils;
import com.dstz.base.common.utils.UserContextUtils;
import com.dstz.base.manager.impl.AbBaseManagerImpl;
import com.dstz.base.query.impl.DefaultAbQueryFilter;
import com.dstz.cms.api.constant.CmsStatusCode;
import com.dstz.cms.api.model.FreemarkerData;
import com.dstz.cms.core.entity.CmsComments;
import com.dstz.cms.core.entity.CmsNotify;
import com.dstz.cms.core.entity.CmsNotifyShare;
import com.dstz.cms.core.entity.CmsNotifyUser;
import com.dstz.cms.core.entity.dto.CmsNotifyDTO;
import com.dstz.cms.core.entity.vo.CmsNotifyListVO;
import com.dstz.cms.core.entity.vo.CmsNotifyVO;
import com.dstz.cms.core.helper.CmsHelper;
import com.dstz.cms.core.manager.*;
import com.dstz.cms.core.mapper.CmsNotifyMapper;
import com.dstz.component.mq.api.constants.JmsTypeEnum;
import com.dstz.component.mq.api.model.DefaultJmsDTO;
import com.dstz.component.msg.api.MsgApi;
import com.dstz.component.msg.api.dto.MsgDTO;
import com.dstz.org.api.model.IUser;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static com.dstz.base.common.constats.InnerMsgEnum.NOTIFY;
import static com.dstz.cms.api.constant.CmsConstant.*;
import static com.dstz.cms.api.constant.CmsStatusCode.NOTIFY_DELETE_DISABLED;
import static com.dstz.cms.api.constant.CmsStatusCode.NOTIFY_READ_ONLY;
import static com.dstz.component.mq.api.constants.JmsTypeEnum.INNER;
import static com.dstz.sys.api.constant.SysApiCodes.NAME_DUPLICATE;
import static java.util.stream.Collectors.toList;


/**
 * 系统公告表 通用服务实现类
 *
 * @author niu
 * @since 2022-02-28
 */
@Service("cmsNotifyManager")
public class CmsNotifyManagerImpl extends AbBaseManagerImpl<CmsNotify> implements CmsNotifyManager {

    @Autowired
    private CmsNotifyUserManager cmsNotifyUserManager;
    @Autowired
    private CmsNotifyShareManager cmsNotifyShareManager;
    @Autowired
    private CmsCommentsManager cmsCommentsManager;
    @Autowired
    private CmsNotifyMapper cmsNotifyMapper;
    @Autowired
    private CmsHelper cmsHelper;
    @Autowired
    private MsgApi msgApi;

    /**
     * 公告分页查询
     *
     * @param queryParamDto 查询条件对象
     * @return PageListDTO<CmsNotifyDTO> 公告查询结果分页Page对象
     */
    @Override
    public PageListDTO<CmsNotifyVO> page(QueryParamDTO queryParamDto) {
        PageListDTO<CmsNotify> result = query(new DefaultAbQueryFilter(queryParamDto, null));
        List<CmsNotifyVO> cmsNotifyVOList = result.getRows().stream().map(this::fillingNotify).collect(toList());
        return new PageListDTO<>(result.getPageSize(), result.getPage(), result.getTotal(), cmsNotifyVOList);
    }

    /**
     * 查询指定ID的公告内容
     *
     * @param id 公告ID
     * @return cmsNotifyDto 公告Dto详情对象
     */
    @Override
    public CmsNotifyVO details(String id) {
        CmsNotify cmsNotify = super.getById(id);
        if (STATUS_PUBLISHED == cmsNotify.getStatus()) {
            relationOperate(cmsNotify);
        }
        CmsNotifyVO notify = fillingNotify(cmsNotify);
        //填充关联的评论列表
        notify.setCmsCommentsList(cmsCommentsManager.selectByWrapper(Wrappers.<CmsComments>lambdaQuery()
                .eq(CmsComments::getCommentType, COMMENT_TYPE_NOTIFY).eq(CmsComments::getMsgId, cmsNotify.getId())));
        return notify;
    }

    /**
     * 新增公告
     *
     * @param cmsNotifyDTO 公告DTO对象
     */
    @Override
    public void save(CmsNotifyDTO cmsNotifyDTO) {
        validate(null, cmsNotifyDTO);
        try {
            create(cmsNotifyDTO);
        } catch (DataIntegrityViolationException e) {
            String errorStr = e.getLocalizedMessage();
            if (errorStr.contains("Data too long for column")) {
                String str = errorStr.substring(errorStr.indexOf("'") + 1);
                String column = str.substring(0, str.indexOf("'"));
                throw new BusinessException(CmsStatusCode.COLUMN_TOO_LONG.formatMessage("字段[{}]太长,超出数据库字段限制!", column));
            } else {
                throw new BusinessException(CmsStatusCode.SAVE_ERROR);
            }
        }
        cmsNotifyShareManager.saveRelation(cmsNotifyDTO);
    }

    /**
     * 修改公告
     *
     * @param cmsNotifyDTO 修改的的公告DTO对象
     */
    @Override
    public void update(CmsNotifyDTO cmsNotifyDTO) {
        CmsNotify notify = super.getById(cmsNotifyDTO.getId());
        validate(notify, cmsNotifyDTO);
        try {
            super.update(cmsNotifyDTO);
        } catch (DataIntegrityViolationException e) {
            String errorStr = e.getLocalizedMessage();
            if (errorStr.contains("Data too long for column")) {
                String str = errorStr.substring(errorStr.indexOf("'") + 1);
                String column = str.substring(0, str.indexOf("'"));
                throw new BusinessException(CmsStatusCode.COLUMN_TOO_LONG.formatMessage("字段[{}]太长,超出数据库字段限制!", column));
            } else {
                throw new BusinessException(CmsStatusCode.SAVE_ERROR);
            }
        }
        //删除旧的部门关联,文件关联等. 并保存新的关联部门
        cmsNotifyShareManager.deleteByNotifyId(notify.getId());
        cmsHelper.deleteFile(cmsNotifyDTO.getAttachments());
        cmsNotifyShareManager.saveRelation(cmsNotifyDTO);
    }

    /**
     * 查看所属公告列表 (筛选公告关联的组织)
     */
    @Override
    public PageListDTO<CmsNotifyListVO> getNotifyPage(QueryParamDTO paramDTO) {
        DefaultAbQueryFilter queryFilter = new DefaultAbQueryFilter(paramDTO);
        queryFilter.addParam("userId", UserContextUtils.getUserId());
        return cmsNotifyMapper.getNotifyPage(queryFilter);
    }

    /**
     * 查询用户未读公告数量
     */
    @Override
    public int queryUnReadCount() {
        //当前用户关联的公告集合
        List<String> notifyIdList = cmsNotifyShareManager.getNotifyListByCurrentUser();
        //剔除已读的公告集合
        notifyIdList.removeAll(cmsNotifyUserManager.getNotifyListByCurrentUser());
        if (CollectionUtil.isEmpty(notifyIdList)) {
            return 0;
        }
        long count = selectCount(Wrappers.<CmsNotify>lambdaQuery().eq(CmsNotify::getStatus, STATUS_PUBLISHED).in(CmsNotify::getId, notifyIdList));
        return Math.toIntExact(count);
    }

    /**
     * 发布公告
     *
     * @param id 公告id
     */
    @Override
    public void releaseNotify(String id) {
        //更改公告信息
        IUser iUser = UserContextUtils.getUser().get();
        CmsNotify cmsNotify = super.getById(id);
        cmsNotify.releaseNotify(iUser.getUserId(), iUser.getFullName());
        update(cmsNotify);
        //创建关联站内消息 (七个参数,1标题,2模板编号,3接收的用户集合,4业务ID,5消息类型,6发送通道集合,7携带参数)
        msgApi.sendMsg(new MsgDTO(
                TITLE,
                NOTIFY_MESSAGE_TEMPLATE,
                cmsNotifyShareManager.getUserListByNotifyId(id),
                id,
                InnerMsgEnum.NOTIFY.getKey(),
                Collections.singletonList(INNER.getType()),
                ImmutableMap.of(
                        "title", cmsNotify.getTitle(),
                        "subject", cmsNotify.getTitle(),
                        "senderName", cmsNotify.getReleaseName(),
                        "sendTime", DateUtil.formatDateTime(cmsNotify.getReleaseTime())))
        );
    }

    /**
     * 下架公告
     *
     * @param id 公告id
     */
    @Override
    public void withdrawNotify(String id) {
        //更改公告信息
        IUser iUser = UserContextUtils.getUser().get();
        CmsNotify cmsNotify = super.getById(id);
        cmsNotify.withdrawNotify(iUser.getUserId(), iUser.getFullName());
        update(cmsNotify);
        //下架后删除已读标记,以及同步删除站内消息
        cmsNotifyUserManager.deleteByNotifyId(cmsNotify.getId());
        //将关联的站内消息置为过期状态
       // cmsInnerMsgManager.expiredRead(NOTIFY, id);
    }

    /**
     * 删除公告集合
     *
     * @param ids 公告ids集合
     */
    @Override
    public int removeByIds(Collection<? extends Serializable> ids) {
        for (CmsNotify cmsNotify : selectByIds(ids.stream().map(Object::toString).collect(Collectors.toList()))) {
            if (STATUS_PUBLISHED == cmsNotify.getStatus()) {
                throw new BusinessException(NOTIFY_DELETE_DISABLED);
            }
            //删除关联的组织信息、用户已读数据、绑定文件信息,站内消息等
            cmsNotifyShareManager.deleteByNotifyId(cmsNotify.getId());
            cmsNotifyUserManager.deleteByNotifyId(cmsNotify.getId());
            cmsHelper.deleteFile(cmsNotify.getAttachments());
            //  cmsInnerMsgManager.expiredRead(NOTIFY, cmsNotify.getId());
        }
        //批量删除公告
        return super.removeByIds(ids);
    }

    /**
     * 补充其他表的内容,填充公告DTO
     *
     * @param cmsNotify 公告实体对象
     * @return CmsNotifyDTO 填充后的DTO对象
     */
    private CmsNotifyVO fillingNotify(CmsNotify cmsNotify) {
        CmsNotifyVO notifyVO = BeanCopierUtils.transformBean(cmsNotify, CmsNotifyVO.class);
        notifyVO.setCmsNotifyShareList(cmsNotifyShareManager.selectByWrapper(Wrappers.<CmsNotifyShare>lambdaQuery()
                .eq(CmsNotifyShare::getNotifyId, cmsNotify.getId())));
        return notifyVO;
    }

    /**
     * 公告关联的相关操作, 判断是否已读, 创建已读对象,更新已读数量
     *
     * @param cmsNotify 公告实体对象
     */
    private void relationOperate(CmsNotify cmsNotify) {
        String userId = UserContextUtils.getUserId();
        //当前用户如果未读公告,创建关联已读关系
        if (cmsNotifyUserManager.getNotifyUser(cmsNotify.getId(), userId) == null) {
            cmsNotifyUserManager.create(new CmsNotifyUser(cmsNotify.getId(), userId));
        }
        //     cmsInnerMsgManager.updateRead(NOTIFY, cmsNotify.getId());
        //无论是否已读,阅读量+1
        cmsNotify.setVisitNum(cmsNotify.getVisitNum() + 1);
        update(cmsNotify);
    }

    /**
     * 查重操作
     *
     * @param old:       之前的旧对象
     * @param cmsNotify: 修改后的新对象
     **/
    private void validate(CmsNotify old, CmsNotify cmsNotify) {
        //过滤状态为已发布的公告
        if (null != old && old.getStatus() == 1) {
            throw new BusinessException(NOTIFY_READ_ONLY);
        }
        //名称查重
        if (old == null || !StrUtil.equals(old.getTitle(), cmsNotify.getTitle())) {
            if (selectCount(Wrappers.lambdaQuery(CmsNotify.class).eq(CmsNotify::getTitle, cmsNotify.getTitle())) > 0) {
                throw new BusinessMessage(NAME_DUPLICATE);
            }
        }
    }

}