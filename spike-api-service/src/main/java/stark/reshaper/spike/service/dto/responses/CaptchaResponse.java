package stark.reshaper.spike.service.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CaptchaResponse
{
    private String captchaId;
    private String captchaBytes;
}
