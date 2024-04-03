package stark.reshaper.spike.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stark.reshaper.spike.service.constants.SecurityConstants;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginInfo
{
    private String username;
    private String password;
    private String rememberMe;
    private String redirectUrl;
}
