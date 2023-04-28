package stark.reshaper.spike.service.redis;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import stark.dataworks.boot.autoconfig.web.LogRedisKeys;
import stark.reshaper.spike.service.constants.RedisKeyPrefixes;

@LogRedisKeys
@Component
@NoArgsConstructor
public class RedisKeyManager
{
    public String getUserIdKey(long accountId)
    {
        return RedisKeyPrefixes.USER + accountId;
    }
}
