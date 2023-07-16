package stark.reshaper.spike.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import stark.dataworks.basic.collections.Collections;
import stark.reshaper.spike.dao.*;
import stark.reshaper.spike.domain.AccountBaseInfo;
import stark.reshaper.spike.domain.Permission;
import stark.reshaper.spike.service.dto.User;

import java.util.List;

@Slf4j
@Component
public class DaoUserDetailService implements UserDetailsService, UserDetailsPasswordService
{
    @Autowired
    private AccountBaseInfoMapper accountBaseInfoMapper;

    @Autowired
    private AccountRoleMapper accountRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private PermissionMapper permissionMapper;


    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword)
    {
        log.info("Enter updatePassword() ...");

        int updateCount = accountBaseInfoMapper.updatePasswordByUsername(user.getUsername(), newPassword);
        if (updateCount == 1)
            ((User)user).setPassword(newPassword);

        log.info("Execute updatePassword() success ...");

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        AccountBaseInfo accountBaseInfo = accountBaseInfoMapper.getAccountByUsername(username);
        User user = new User();
        user.setUsername(accountBaseInfo.getUsername());
        user.setPassword(accountBaseInfo.getEncryptedPassword());
        user.setId(accountBaseInfo.getId());
        user.setNickname(accountBaseInfo.getNickname());
        user.setAvatarUrl(accountBaseInfo.getAvatarUrl());
        user.setEmail(accountBaseInfo.getEmail());
        user.setGender(accountBaseInfo.getGender());

        List<Long> rootRoleIds = accountRoleMapper.getRoleIdsByAccountId(accountBaseInfo.getId());

        String rootRoleIdsString = Collections.join(rootRoleIds, ",");
        String roleIdsString = roleMapper.getAllRoleIdsByRootIds(rootRoleIdsString);

        if (StringUtils.hasText(roleIdsString))
        {
            List<Long> roleIds = Collections.parseLongList(roleIdsString.split(","));

            List<Long> rootPermissionIds = rolePermissionMapper.getPermissionIdsByRoleIds(roleIds);

            String rootPermissionIdsString = Collections.join(rootPermissionIds, ",");
            String permissionIdsString = permissionMapper.getAllPermissionIdsByRootIds(rootPermissionIdsString);

            if (StringUtils.hasText(permissionIdsString))
            {
                List<Long> permissionIds = Collections.parseLongList(permissionIdsString.split(","));
                List<Permission> permissions = permissionMapper.getPermissionsByIds(permissionIds);

                user.setPermissions(permissions);
            }
        }

        return user;
    }
}
