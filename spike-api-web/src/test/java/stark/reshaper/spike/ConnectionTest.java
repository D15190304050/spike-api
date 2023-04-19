package stark.reshaper.spike;

import io.minio.errors.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import stark.dataworks.basic.data.redis.RedisQuickOperation;
import stark.dataworks.boot.autoconfig.minio.EasyMinio;
import stark.dataworks.boot.autoconfig.minio.EasyMinioProperties;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@SpringBootTest
public class ConnectionTest
{
    @Autowired
    private RedisQuickOperation redisQuickOperation;

    @Value("${dataworks.easy-minio.endpoint}")
    private String endpoint;

    @Value("${dataworks.easy-minio.access-key}")
    private String accessKey;

    @Value("${dataworks.easy-minio.secret-key}")
    private String secretKey;

    @Test
    public void redisConnectionTest()
    {
        String valueOfK1 = redisQuickOperation.get("k1");
        System.out.println(valueOfK1);
    }

    @Test
    public void minioConnectionTest() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        EasyMinio easyMinio = new EasyMinio(new EasyMinioProperties(endpoint, accessKey, secretKey));

        List<String> bucketNames = easyMinio.listBucketNames();
        System.out.println(bucketNames);
    }
}
