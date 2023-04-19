package stark.reshaper.spike.service.redis;

import stark.reshaper.spike.service.constants.RedisKeyPrefixes;

public class RedisKeyManager
{
    private RedisKeyManager(){}

    public static String getUserIdKey(long accountId)
    {
        return RedisKeyPrefixes.USER + accountId;
    }
}
