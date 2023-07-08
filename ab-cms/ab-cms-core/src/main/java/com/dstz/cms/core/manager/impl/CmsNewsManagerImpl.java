package com.dstz.cms.core.manager.impl;

import static com.dstz.base.common.constats.InnerMsgEnum.NEWS;
import static com.dstz.cms.api.constant.CmsConstant.COMMENT_TYPE_NEWS;
import static com.dstz.cms.api.constant.CmsConstant.NEWS_MESSAGE_TEMPLATE;
import static com.dstz.cms.api.constant.CmsConstant.TITLE;
import static com.dstz.cms.api.constant.CmsStatusCode.NEWS_DELETE_DISABLED;
import static com.dstz.cms.api.constant.CmsStatusCode.NEWS_READ_ONLY;
import static com.dstz.component.mq.api.constants.JmsTypeEnum.INNER;
import static java.util.stream.Collectors.toList;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dstz.base.api.dto.PageListDTO;
import com.dstz.base.api.dto.QueryParamDTO;
import com.dstz.base.common.constats.InnerMsgEnum;
import com.dstz.base.common.exceptions.BusinessException;
import com.dstz.base.common.exceptions.BusinessMessage;
import com.dstz.base.common.identityconvert.SysIdentity;
import com.dstz.base.common.utils.BeanCopierUtils;
import com.dstz.base.common.utils.UserContextUtils;
import com.dstz.base.manager.impl.AbBaseManagerImpl;
import com.dstz.base.query.impl.DefaultAbQueryFilter;
import com.dstz.cms.core.entity.CmsComments;
import com.dstz.cms.core.entity.CmsNews;
import com.dstz.cms.core.entity.dto.CmsNewsDTO;
import com.dstz.cms.core.helper.CmsHelper;
import com.dstz.cms.core.manager.CmsCommentsManager;
import com.dstz.cms.core.manager.CmsNewsManager;
import com.dstz.cms.core.mapper.CmsNewsMapper;
import com.dstz.component.msg.api.MsgApi;
import com.dstz.component.msg.api.dto.MsgDTO;
import com.dstz.org.api.UserApi;
import com.dstz.org.api.model.IUser;
import com.dstz.sys.api.constant.SysApiCodes;
import com.google.common.collect.ImmutableMap;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 新闻表 通用服务实现类
 *
 * @author niu
 * @since 2022-03-04
 */
@Service("cmsNewsManager")
public class CmsNewsManagerImpl extends AbBaseManagerImpl<CmsNews> implements CmsNewsManager {

    @Autowired
    private CmsNewsMapper cmsNewsMapper;
    @Autowired
    private CmsCommentsManager cmsCommentsManager;
    @Autowired
    private UserApi userApi;
    @Autowired
    private CmsHelper cmsHelper;
    @Autowired
    private MsgApi msgApi;

    /**
     * 分页列表
     */
    @Override
    public PageListDTO<CmsNews> listJson(QueryParamDTO paramDTO) {
        return cmsNewsMapper.listJson(new DefaultAbQueryFilter(paramDTO));
    }

    /**
     * 首页获取固定两条,精简后的新闻(剔除附件,和其他无用字段)
     */
    @Override
    public PageListDTO<CmsNews> getNewsPage(QueryParamDTO paramDTO) {
        return cmsNewsMapper.getNewsPage(new DefaultAbQueryFilter(paramDTO));
    }


    /**
     * 按实体ID获取实体
     *
     * @param id 实体ID
     * @return CmsNewsDTO 实体Dto记录
     */
    @Override
    public CmsNewsDTO details(String id) {
        CmsNews cmsNews = super.getById(id);
        if (cmsNews.getStatus() == 1) {
            //访问数加1
            cmsNews.setVisitNum(cmsNews.getVisitNum() + 1);
            super.update(cmsNews);
          //  cmsInnerMsgManager.updateRead(NEWS, id);
        }
        return fillingNew(cmsNews);
    }

    /**
     * 新增或修改新闻
     *
     * @param cmsNews 新闻对象
     */
    @Override
    public void saveOrUpdate(CmsNews cmsNews) {
        validate(cmsNews);
        super.createOrUpdate(cmsNews);
    }

    /**
     * 发布新闻
     *
     * @param id 新闻id
     */
    @Override
    public void releaseNews(String id) {
        final IUser currentUser = UserContextUtils.getValidUser();
        
        // 更新新闻状态
        CmsNews cmsNews = cmsNewsMapper.selectById(id);
        cmsNews.setId(id);
        cmsNews.releaseNews(currentUser.getUserId(), currentUser.getFullName());
        cmsNewsMapper.updateById(cmsNews);

        // 获取所有用户
        QueryParamDTO queryParamDTO = new QueryParamDTO();
        queryParamDTO.setLimit(500);
        queryParamDTO.setOffset(0);
        queryParamDTO.setSearchCount(Boolean.FALSE);
        List<SysIdentity> sysIdentityList = new LinkedList<>();
        PageListDTO<? extends IUser> pageListDTO;
        for (; CollUtil.isNotEmpty(pageListDTO = userApi.queryFilter(queryParamDTO)); queryParamDTO.setOffset(queryParamDTO.getOffset() + queryParamDTO.getLimit())) {
            pageListDTO.stream().map(SysIdentity::new).forEach(sysIdentityList::add);
            if (pageListDTO.size() != queryParamDTO.getLimit()) {
                break;
            }
        }
        CollUtil.clear(pageListDTO);
        
        //创建关联站内消息 (七个参数,1标题,2模板编号,3接收的用户集合,4业务ID,5消息类型,6发送通道集合,7携带参数)
        if (CollUtil.isNotEmpty(sysIdentityList)) {
            msgApi.sendMsg(new MsgDTO(
                    TITLE,
                    NEWS_MESSAGE_TEMPLATE,
                    sysIdentityList,
                    id,
                    InnerMsgEnum.NEWS.getKey(),
                    Collections.singletonList(INNER.getType()),
                    ImmutableMap.of(
                            "title", cmsNews.getTitle(),
                            "subject", cmsNews.getTitle(),
                            "senderName", cmsNews.getReleaseName(),
                            "sendTime", DateUtil.formatDateTime(cmsNews.getReleaseTime())))
            );
        }
    }

    /**
     * 下架新闻
     *
     * @param id 新闻id
     */
    @Override
    public void withdrawNews(String id) {
        //更改新闻信息
        IUser iUser = UserContextUtils.getUser().get();
        CmsNews cmsNews = super.getById(id);
        cmsNews.withdrawNews(iUser.getUserId(), iUser.getFullName());
        update(cmsNews);
        //新闻下架后同步站内消息状态为过期
      //  cmsInnerMsgManager.expiredRead(NEWS, id);
    }


    /**
     * 删除新闻集合
     *
     * @param ids 新闻ids集合
     */
    @Override
    public int removeByIds(Collection<? extends Serializable> ids) {
        List<CmsNews> cmsNews = selectByIds(ids.stream().map(Object::toString).collect(toList()));
        for (CmsNews cmsNew : cmsNews) {
            //如果新闻已发布则不删除
            if (cmsNew.getStatus() == 1) {
                throw new BusinessMessage(NEWS_DELETE_DISABLED);
            }
            //删除附件信息
            cmsHelper.deleteFile(cmsNew.getAttachments());
            //删除图片信息
            cmsHelper.deleteFile(cmsNew.getImages());
            //新闻下架后同步站内消息为过期状态
         //   cmsInnerMsgManager.expiredRead(NEWS, cmsNew.getId());
        }
        //批量删除新闻
        return super.removeByIds(ids);
    }

    /**
     * 补充其他表的内容,填充新闻DTO
     *
     * @param cmsNews 新闻实体对象
     * @return CmsNewsDTO 填充后的DTO对象
     */
    public CmsNewsDTO fillingNew(CmsNews cmsNews) {
        CmsNewsDTO cmsNewsDTO = new CmsNewsDTO(cmsCommentsManager.selectByWrapper(Wrappers.<CmsComments>lambdaQuery()
                .eq(CmsComments::getCommentType, COMMENT_TYPE_NEWS).eq(CmsComments::getMsgId, cmsNews.getId())));
        BeanCopierUtils.copyProperties(cmsNews, cmsNewsDTO);
        return cmsNewsDTO;
    }

    /**
     * 查重操作
     *
     * @param cmsNews: 修改后的新对象
     **/
    private void validate(CmsNews cmsNews) {
        //过滤状态为已发布的新闻
        if (StrUtil.isNotEmpty(cmsNews.getId()) && getById(cmsNews.getId()).getStatus() == 1) {
            throw new BusinessException(NEWS_READ_ONLY);
        }
        //名称查重
        if (StrUtil.isEmpty(cmsNews.getId()) || !StrUtil.equals(getById(cmsNews.getId()).getTitle(), cmsNews.getTitle())) {
            if (selectCount(Wrappers.lambdaQuery(CmsNews.class).eq(CmsNews::getTitle, cmsNews.getTitle())) > 0) {
                throw new BusinessMessage(SysApiCodes.NAME_DUPLICATE);
            }
        }
    }

}
