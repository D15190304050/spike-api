package stark.reshaper.spike.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import stark.reshaper.spike.domain.Role;

@Mapper
public interface RoleMapper
{
    List<Role> getAllRoles();
    String getAllRoleIdsByRootIds(String rootRoleIdsString);
}