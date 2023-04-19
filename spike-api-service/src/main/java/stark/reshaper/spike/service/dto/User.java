package stark.reshaper.spike.service.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import stark.reshaper.spike.domain.Permission;
import stark.reshaper.spike.domain.Role;
import stark.reshaper.spike.service.constants.SecurityConstants;

import java.util.*;

public class User implements UserDetails
{
    private long id;
    private String username;
    private String password;

    // TODO: Maybe we only need RoleDto & PermissionDto here?
    private List<Role> roles;
    private List<Permission> permissions;

    public User()
    {
        roles = new ArrayList<>();
        permissions = new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(role ->
        {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(SecurityConstants.ROLE_PREFIX + role.getName());
            authorities.add(authority);
        });

        permissions.forEach(permission ->
        {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission.getName());
            authorities.add(authority);
        });

        return authorities;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public List<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(List<Role> roles)
    {
        this.roles = roles;
    }

    public List<Permission> getPermissions()
    {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions)
    {
        this.permissions = permissions;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public String getUsername()
    {
        return username;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }
}
