package stark.reshaper.spike.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserContextService
{
    private UserContextService()
    {

    }

    public static void setAuthentication(UsernamePasswordAuthenticationToken authenticationToken)
    {
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    public static Authentication getAuthentication()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
