package stark.reshaper.spike.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import stark.reshaper.spike.domain.AccountRole;

@Mapper
public interface AccountRoleMapper
{
    List<Long> getRoleIdsByAccountId(long accountId);
}