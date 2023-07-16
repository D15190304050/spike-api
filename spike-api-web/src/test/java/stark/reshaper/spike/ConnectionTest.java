package stark.reshaper.spike;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import stark.dataworks.basic.data.redis.RedisQuickOperation;

@SpringBootTest
public class ConnectionTest
{
    @Autowired
    private RedisQuickOperation redisQuickOperation;

    @Test
    public void redisConnectionTest()
    {
        String valueOfK1 = redisQuickOperation.get("k1");
        System.out.println(valueOfK1);
    }
}
