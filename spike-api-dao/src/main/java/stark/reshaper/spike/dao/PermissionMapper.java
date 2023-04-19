package stark.reshaper.spike.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import stark.reshaper.spike.domain.Permission;

@Mapper
public interface PermissionMapper
{
    List<Permission> getAllPermissions();
    String getAllPermissionIdsByRootIds(String rootPermissionIdsString);
    List<Permission> getPermissionsByIds(List<Long> ids);
}