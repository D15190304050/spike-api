package stark.reshaper.spike.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stark.reshaper.spike.domain.AccountBaseInfo;

@Mapper
public interface AccountBaseInfoMapper
{
    AccountBaseInfo getAccountByUsername(String username);
    int updatePasswordByUsername(@Param("username") String username, @Param("encryptedPassword") String encryptedPassword);
}