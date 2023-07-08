package com.dstz.sys.groovy;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dstz.base.common.identityconvert.SysIdentity;
import com.dstz.base.common.utils.JsonUtils;
import com.dstz.base.common.utils.UserContextUtils;
import com.dstz.groovy.script.api.IScript;
import com.dstz.org.api.model.IGroup;
import com.dstz.org.api.model.IUser;
import com.dstz.sys.core.entity.SysProperties;
import com.dstz.sys.core.manager.SysPropertiesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author jinxia.hou
 * @Name SysScript
 * @description: 系统默认的一些脚本
 * @date 2022/7/2814:13
 */
@Component
public class SysScript implements IScript {

    private static final Logger logger = LoggerFactory.getLogger(SysScript.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private  SysPropertiesManager sysPropertiesManager;


    /**
     * 获取系统属性
     * @param key code
     * @return value
     */
    public String getProperty(String key) {
        SysProperties properties = sysPropertiesManager.selectOne(Wrappers.lambdaQuery(SysProperties.class).eq(SysProperties::getCode,key));
        if (properties!=null){
            return properties.getValue();
        }
        return null;
    }


    public IUser getCurrentUser() {
        IUser user = UserContextUtils.getValidUser();
        return user;
    }

    public String getCurrentGroupName() {
        IGroup iGroup =UserContextUtils.getGroup().get();
        if (iGroup!= null) {
            return iGroup.getGroupName();
        } else {
            return "";
        }
    }

    public String getCurrentUserName() {
        return UserContextUtils.getValidUser().getFullName();
    }

    public Integer executeUpdateSql(String sql, Object ... params) {
        Integer result = jdbcTemplate.update(sql, params);
        return result ;
    }

    public Integer executeIntegerSql(String sql, Object ... params) {
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, params);
        return result ;
    }

    /**
     * 通过一个sql 获取候选人
     * @param sql
     * @param params
     * @return
     */
    public Set<SysIdentity> getIdentityBySql(String sql, Object ... params) {
        List<SysIdentity> list = jdbcTemplate.queryForList(sql, SysIdentity.class,  params);
        return transitionIdentitys(list);
    }

    private Set<SysIdentity> transitionIdentitys(List<SysIdentity> list) {
        Set<SysIdentity> set = new HashSet<>();

        for(SysIdentity identity : list) {
            // 如果SQL 返回的候选人不完整则不予添加
            if(StrUtil.isEmpty(identity.getType()) || StrUtil.isEmpty(identity.getName())
                    || StrUtil.isEmpty(identity.getId())) {

                logger.debug("通过 sql 获取用户候选人失败，sql 返回的 identity 信息不完整，请检查{}", JsonUtils.toJSONString(identity));
                continue;
            }
            set.add(identity);
        }

        return set;
    }
}
