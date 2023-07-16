package stark.reshaper.spike.security.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import stark.dataworks.basic.data.json.JsonSerializer;
import stark.dataworks.basic.data.redis.RedisQuickOperation;
import stark.dataworks.boot.ExceptionLogger;
import stark.reshaper.spike.service.constants.SecurityConstants;
import stark.reshaper.spike.service.dto.LoginInfo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class UsernamePasswordLoginFilter extends UsernamePasswordAuthenticationFilter
{
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException
    {
        // 1. Determine if it is a POST method.
        // 2. Determine if its content type is "application/json".

        // 1.
        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST"))
            throw new AuthenticationServiceException("Authentication method not supported: " + requestMethod);

        // 2.
        String contentType = request.getContentType();
        if (contentType.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE))
        {
            try
            {
                LoginInfo loginInfo = JsonSerializer.deserialize(request.getInputStream(), LoginInfo.class);
                String username = loginInfo.getUsername();
                String password = loginInfo.getPassword();
                String rememberMe = loginInfo.getRememberMe();
                request.setAttribute(SecurityConstants.REMEMBER_ME, rememberMe);
                UsernamePasswordAuthenticationToken authenticationRequest = new UsernamePasswordAuthenticationToken(username, password);
                setDetails(request, authenticationRequest);
                return getAuthenticationManager().authenticate(authenticationRequest);
            }
            catch (IOException e)
            {
                ExceptionLogger.logExceptionInfo(e);
            }
        }

        // Call attemptAuthentication() of super class.
        return super.attemptAuthentication(request, response);
    }
}
