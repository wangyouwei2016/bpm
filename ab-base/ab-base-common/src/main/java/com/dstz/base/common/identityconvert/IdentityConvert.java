package com.dstz.base.common.identityconvert;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.IterUtil;
import com.dstz.base.common.enums.IdentityType;
import com.dstz.org.api.GroupApi;
import com.dstz.org.api.UserApi;
import com.dstz.org.api.model.IUser;
import org.apache.commons.collections4.iterators.IteratorChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户转换
 *
 * @author lightning
 */
@Component
public class IdentityConvert {
    private static final Logger LOGGER = LoggerFactory.getLogger(IdentityConvert.class);

    @Autowired
    GroupApi groupApi;

    @Autowired
    UserApi userApi;

    public IUser convert2User(SysIdentity identity) {
        List<? extends IUser> users = convert2Users(identity);

        if (CollectionUtil.isNotEmpty(users)) {
            return users.get(0);
        }

        return null;

    }

    public List<? extends IUser> convert2Users(SysIdentity identity) {
        //如果为用户
        if (IdentityType.USER.getKey().equals(identity.getType())) {
            IUser user = userApi.getByUserId(identity.getId());
            if (Objects.isNull(user)) {
                LOGGER.error("identity convert 2 users error id[{}],name[{}] not found! ", identity.getId(), identity.getName());
                return new ArrayList<>();
            }
            return CollUtil.newArrayList(user);
        }
        //目前其他均为组类型
        return CollUtil.newArrayList(userApi.getByGroupTypeAndGroupIds(identity.getType(), Collections.singleton(identity.getId())));
    }

    public Iterable<? extends IUser> convert2Users(List<SysIdentity> identities) {
        // 按类型分组出id
        Map<String, Set<String>> identityTypeGroupMap = identities.parallelStream().collect(Collectors.groupingBy(SysIdentity::getType, HashMap::new, Collectors.mapping(SysIdentity::getId, Collectors.toSet())));
        List<Iterator<? extends IUser>> iteratorList = new ArrayList<>(identityTypeGroupMap.size());
        // 获取用户
        Optional.ofNullable(identityTypeGroupMap.remove(IdentityType.USER.getKey())).filter(CollUtil::isNotEmpty).map(userApi::getByUserIds).filter(IterUtil::isNotEmpty).ifPresent(iteratorList::add);
        // 其他类型解析出用户
        CollUtil.forEach(identityTypeGroupMap, (k, v, index) -> Optional.ofNullable(userApi.getByGroupTypeAndGroupIds(k, v)).filter(CollUtil::isNotEmpty).ifPresent(iteratorList::add));
        return () -> new IteratorChain<>(iteratorList);
    }
}
