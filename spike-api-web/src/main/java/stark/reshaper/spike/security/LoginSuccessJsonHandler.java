package stark.reshaper.spike.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import stark.dataworks.basic.data.json.JsonSerializer;
import stark.dataworks.basic.data.redis.RedisQuickOperation;
import stark.dataworks.boot.web.ServiceResponse;
import stark.reshaper.spike.service.JwtService;
import stark.reshaper.spike.service.constants.SecurityConstants;
import stark.reshaper.spike.service.dto.AccountPrincipal;
import stark.reshaper.spike.service.dto.User;
import stark.reshaper.spike.service.redis.RedisKeyManager;
import stark.reshaper.spike.service.redis.SpikeRedisOperation;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class LoginSuccessJsonHandler implements AuthenticationSuccessHandler
{
    @Autowired
    private JwtService jwtService;

    @Autowired
    private RedisQuickOperation redisQuickOperation;

    @Autowired
    private SpikeRedisOperation redisOperation;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        User user = (User) authentication.getPrincipal();
        cacheAuthentication(user, response);
        writeAuthenticationToken(response, user);
    }

    public String prepareUserLoginInfoToken(User user)
    {


        return "";
    }

    public void writeAuthenticationToken(HttpServletResponse response, User user) throws IOException
    {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        AccountPrincipal accountPrincipal = new AccountPrincipal(user.getId(), user.getUsername());
        String token = jwtService.createToken(accountPrincipal);
        ServiceResponse<String> loginSuccessResponse = ServiceResponse.buildSuccessResponse(token, SecurityConstants.LOGIN_SUCCESS);
        String s = JsonSerializer.serialize(loginSuccessResponse);
        log.info("Login success message = " + s);
        response.getWriter().println(s);

        response.flushBuffer();
    }

    // TODO: Move this method to another class after integration of other login methods.
    public void cacheAuthentication(User user, HttpServletResponse response)
    {
        // Cache user info.
        redisOperation.cacheUser(user);

        // Cache user id.
        long userId = user.getId();
        String userIdKey = UUID.randomUUID().toString();
        Cookie ssoCookie = new Cookie(SecurityConstants.SSO_COOKIE_NAME, userIdKey);
        response.addCookie(ssoCookie);
        redisQuickOperation.set(userIdKey, "" + userId);
    }
}
