package stark.reshaper.spike.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.web.cors.CorsConfigurationSource;
import stark.dataworks.basic.data.redis.RedisQuickOperation;
import stark.reshaper.spike.security.*;
import stark.reshaper.spike.security.filter.TokenLoginFilter;
import stark.reshaper.spike.security.filter.UsernamePasswordLoginFilter;
import stark.reshaper.spike.service.DaoUserDetailService;
import stark.reshaper.spike.service.JwtService;
import stark.reshaper.spike.service.constants.SecurityConstants;
import stark.reshaper.spike.service.redis.SpikeRedisOperation;

@Slf4j
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RedisQuickOperation redisQuickOperation;

    @Autowired
    private JdbcTokenRepositoryImpl tokenRepository;

    @Autowired
    private RememberMeServices rememberMeServices;

    @Autowired
    private LoginSuccessJsonHandler loginSuccessJsonHandler;

    @Autowired
    private LoginFailureJsonHandler loginFailureJsonHandler;

    @Autowired
    private LogoutSuccessJsonHandler logoutSuccessJsonHandler;

    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    @Autowired
    private DaoUserDetailService daoUserDetailService;

    @Autowired
    private SpikeRedisOperation spikeRedisOperation;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    public UsernamePasswordLoginFilter usernamePasswordLoginFilter() throws Exception
    {
        UsernamePasswordLoginFilter loginFilter = new UsernamePasswordLoginFilter();
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        loginFilter.setRememberMeServices(rememberMeServices); // Set the "RememberMeServices" for authentication success.
        loginFilter.setAuthenticationSuccessHandler(loginSuccessJsonHandler); // Handler for authentication success.
        loginFilter.setAuthenticationFailureHandler(loginFailureJsonHandler); // Handler for authentication failure.
        loginFilter.setFilterProcessesUrl(SecurityConstants.DEFAULT_LOGIN_URI);
        return loginFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception
    {
        builder.userDetailsService(daoUserDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
            .mvcMatchers(SecurityConstants.NON_AUTHENTICATE_URIS)
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .rememberMe()
            .alwaysRemember(true)
            .rememberMeServices(rememberMeServices)
            .tokenRepository(tokenRepository)
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(new UnauthorizedEntryPoint())
            .accessDeniedHandler(new NoPermissionHandler())
            .and()
            .formLogin()
            .successHandler(loginSuccessJsonHandler)
            .failureHandler(loginFailureJsonHandler)
            .and()
            .logout()
            .logoutUrl(SecurityConstants.DEFAULT_LOGOUT_URI)
            .logoutSuccessHandler(logoutSuccessJsonHandler)
            .and()
            .cors()
            .configurationSource(corsConfigurationSource)
            .and()
            .csrf()
            .disable() // Should be enabled.
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;

        http.addFilterBefore(new TokenLoginFilter(jwtService, redisQuickOperation, daoUserDetailService, spikeRedisOperation, contextPath), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(usernamePasswordLoginFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
