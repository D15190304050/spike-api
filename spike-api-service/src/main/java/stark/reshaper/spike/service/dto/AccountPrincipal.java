package stark.reshaper.spike.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountPrincipal
{
    private long accountId;
    private String username;
}
