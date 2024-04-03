package stark.reshaper.spike.service.dto.results;

import lombok.Data;

@Data
public class LoginState
{
    private String token;
    private String redirectUrl;
}
