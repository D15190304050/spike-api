package stark.reshaper.spike.service.dto.results;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginSuccessResponse
{
    // Should be account base information, like nickname, avatar_url, ...
    private String nickname;
    private String avatar_url;
    private String token;
}
