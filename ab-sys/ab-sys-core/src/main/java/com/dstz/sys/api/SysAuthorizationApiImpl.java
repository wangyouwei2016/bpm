package com.dstz.sys.api;

import cn.hutool.core.bean.BeanUtil;
import com.dstz.sys.api.constant.RightsObjectConstants;
import com.dstz.sys.api.dto.SysAuthorizationDTO;
import com.dstz.sys.core.manager.SysAuthorizationManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SysAuthorizationApiImpl implements SysAuthorizationApi {

    private final SysAuthorizationManager sysAuthorizationManager;

    public SysAuthorizationApiImpl(SysAuthorizationManager sysAuthorizationManager) {
        this.sysAuthorizationManager = sysAuthorizationManager;
    }

    @Override
    public Set<String> getUserRights(String userId) {
        return sysAuthorizationManager.getUserRights(userId);
    }

    @Override
    public Map<String, Object> getUserRightsSql(RightsObjectConstants rightsObject, String userId, String targetKey) {
        return sysAuthorizationManager.getUserRightsSql(rightsObject, userId, targetKey);
    }

    @Override
    public List<SysAuthorizationDTO> getAuthorizationByRights(RightsObjectConstants rightsObject, String rightsCode) {
        return BeanUtil.copyToList(sysAuthorizationManager.getByTarget(rightsObject, rightsCode),SysAuthorizationDTO.class);
    }

    @Override
    public void deleteAuthorizationByRights(RightsObjectConstants rightsObject, String rightsTarget) {
        sysAuthorizationManager.deleteAuthorizationByRights(rightsObject, rightsTarget);
    }

}
