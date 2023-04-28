package stark.reshaper.spike;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"stark.reshaper.spike", "stark.dataworks.boot.autoconfig"})
public class SpikeMain
{
    public static void main(String[] args)
    {
        SpringApplication.run(SpikeMain.class);
    }
}
