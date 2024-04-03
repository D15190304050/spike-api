package stark.reshaper.spike.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import stark.reshaper.spike.service.constants.SecurityConstants;
import stark.reshaper.spike.service.dto.AccountPrincipal;

import java.util.Calendar;

@Component
public class JwtService implements ApplicationContextAware, ApplicationListener<WebServerInitializedEvent>
{
    public static final int TOKEN_EXPIRATION_IN_DAY = 30;

    @Value("${spike.jwt-secret}")
    public String secret;

    private JWTCreator.Builder builder;
    private JWTVerifier verifier;
    private Algorithm jwtAlgorithm;

    public String createToken(AccountPrincipal accountPrincipal)
    {
        Calendar expirationTime = Calendar.getInstance();
        expirationTime.add(Calendar.DATE, TOKEN_EXPIRATION_IN_DAY);

        builder.withClaim(SecurityConstants.ACCOUNT_ID, accountPrincipal.getAccountId());
        builder.withClaim(SecurityConstants.USERNAME, accountPrincipal.getUsername());
        builder.withClaim(SecurityConstants.NICKNAME, accountPrincipal.getNickname());

        return builder.withExpiresAt(expirationTime.getTime()).sign(jwtAlgorithm);
    }

    public DecodedJWT verify(String token)
    {
        return verifier.verify(token);
    }

    public AccountPrincipal parseAccountPrincipal(String token)
    {
        DecodedJWT decodedJwt = verify(token);
        Long accountId = decodedJwt.getClaim(SecurityConstants.ACCOUNT_ID).asLong();
        String username = decodedJwt.getClaim(SecurityConstants.USERNAME).asString();
        String nickname = decodedJwt.getClaim(SecurityConstants.NICKNAME).asString();

        if (accountId != null && username != null)
            return new AccountPrincipal(accountId, username, nickname);
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {

    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event)
    {
        builder = JWT.create();
        jwtAlgorithm = Algorithm.HMAC256(secret);
        verifier = JWT.require(jwtAlgorithm).build();
    }
}
