package stark.reshaper.spike.security;

import org.junit.Test;
import stark.dataworks.basic.data.json.JsonSerializer;

import java.util.Random;

public class JwtTokenGeneratorTest
{
    @Test
    public void generateNextToken()
    {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789~!@#$%^&*()_+=-";
        int length = 30;

        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
            sb.append(alphabet.charAt(random.nextInt(alphabet.length())));

        // Used to test the import of dataworks.
        String x = JsonSerializer.serialize(sb);
        System.out.println(x);

        // Get the token.
        System.out.println(sb);
    }
}
