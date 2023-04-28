package stark.reshaper.spike.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import stark.dataworks.basic.data.redis.RedisQuickOperation;
import stark.reshaper.spike.service.JwtService;
import stark.reshaper.spike.service.dto.User;

import java.util.concurrent.TimeUnit;

@Component
public class SpikeRedisOperation
{
    @Autowired
    private RedisQuickOperation redisQuickOperation;

    @Autowired
    private RedisKeyManager redisKeyManager;

    public void cacheUser(User user)
    {
        long userId = user.getId();
        String userIdKey = redisKeyManager.getUserIdKey(userId);
        redisQuickOperation.set(userIdKey, user, JwtService.TOKEN_EXPIRATION_IN_DAY, TimeUnit.DAYS);
    }
}
