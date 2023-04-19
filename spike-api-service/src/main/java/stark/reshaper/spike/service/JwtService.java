package stark.reshaper.spike.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;
import stark.reshaper.spike.service.constants.SecurityConstants;
import stark.reshaper.spike.service.dto.AccountPrincipal;

import java.util.Calendar;

@Component
public class JwtService
{

    public static final int TOKEN_EXPIRATION_IN_DAY = 1;
    public static final String SECRET = "q4~@x2uC$f@M2x*^e5&DB^L!3~)6@+";

    private final JWTCreator.Builder builder;
    private final JWTVerifier verifier;
    private final Algorithm jwtAlgorithm;

    public JwtService()
    {
        builder = JWT.create();
        jwtAlgorithm = Algorithm.HMAC256(SECRET);
        verifier = JWT.require(jwtAlgorithm).build();
    }

    public String createToken(AccountPrincipal accountPrincipal)
    {
        Calendar expirationTime = Calendar.getInstance();
        expirationTime.add(Calendar.DATE, TOKEN_EXPIRATION_IN_DAY);

        builder.withClaim(SecurityConstants.ACCOUNT_ID, accountPrincipal.getAccountId());
        builder.withClaim(SecurityConstants.USERNAME, accountPrincipal.getUsername());

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

        if (accountId != null && username != null)
            return new AccountPrincipal(accountId, username);
        return null;
    }
}
