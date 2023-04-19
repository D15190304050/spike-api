package stark.reshaper.spike.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import stark.reshaper.spike.domain.RolePermission;

@Mapper
public interface RolePermissionMapper
{
    List<Long> getPermissionIdsByRoleIds(List<Long> roleIds);
}