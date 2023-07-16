package stark.reshaper.spike.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private String password;

    // TODO: Maybe we only need RoleDto & PermissionDto here?
    private List<Role> roles;
    private List<Permission> permissions;

    private String nickname;
    private String avatarUrl;
    private String email;
    private String gender;

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

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getAvatarUrl()
    {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl)
    {
        this.avatarUrl = avatarUrl;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
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
