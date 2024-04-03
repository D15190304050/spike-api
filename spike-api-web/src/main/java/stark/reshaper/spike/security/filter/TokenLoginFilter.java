package stark.reshaper.spike.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import stark.dataworks.basic.data.json.JsonSerializer;
import stark.dataworks.basic.data.redis.RedisQuickOperation;
import stark.dataworks.boot.web.TokenHandler;
import stark.reshaper.spike.service.JwtService;
import stark.reshaper.spike.service.UserContextService;
import stark.reshaper.spike.service.constants.SecurityConstants;
import stark.reshaper.spike.service.dto.AccountPrincipal;
import stark.reshaper.spike.service.dto.User;
import stark.reshaper.spike.service.redis.RedisKeyManager;
import stark.reshaper.spike.service.redis.SpikeRedisOperation;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TokenLoginFilter extends OncePerRequestFilter
{
    private final JwtService jwtService;
    private final RedisQuickOperation redisQuickOperation;
    private final UserDetailsService userDetailsService;
    private final SpikeRedisOperation spikeRedisOperation;
    private final RedisKeyManager redisKeyManager;
    private final List<String> ignoreUris;

    public TokenLoginFilter(JwtService jwtService, RedisQuickOperation redisQuickOperation, UserDetailsService userDetailsService, SpikeRedisOperation spikeRedisOperation, String contextPath, RedisKeyManager redisKeyManager)
    {
        this.jwtService = jwtService;
        this.redisQuickOperation = redisQuickOperation;
        this.userDetailsService = userDetailsService;
        this.spikeRedisOperation = spikeRedisOperation;
        this.redisKeyManager = redisKeyManager;

        ignoreUris = new ArrayList<>();
        for (String uri : SecurityConstants.NON_AUTHENTICATE_URIS)
            ignoreUris.add(contextPath + uri);
        ignoreUris.add(contextPath + SecurityConstants.DEFAULT_LOGIN_URI);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        String requestURI = request.getRequestURI();
        if (ignoreUris.contains(requestURI))
        {
            filterChain.doFilter(request, response);
            return;
        }

        String token = TokenHandler.getToken(request, SecurityConstants.SSO_COOKIE_NAME);

        if (StringUtils.hasText(token))
        {
            AccountPrincipal accountPrincipal = jwtService.parseAccountPrincipal(token);
            if (accountPrincipal != null)
            {
                long accountId = accountPrincipal.getAccountId();
                String userJson = redisQuickOperation.get(redisKeyManager.getUserIdKey(accountId));
                User user;

                if (StringUtils.hasText(userJson))
                    user = JsonSerializer.deserialize(userJson, User.class);
                else
                {
                    user = (User) userDetailsService.loadUserByUsername(accountPrincipal.getUsername());
                    spikeRedisOperation.cacheUser(user);
                }

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
                UserContextService.setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
