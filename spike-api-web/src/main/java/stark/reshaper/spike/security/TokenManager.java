package stark.reshaper.spike.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Map;

@Component
public class TokenManager
{

    public static final long TOKEN_EXPIRATION = 1000 * 60 * 60 * 24;
    public static final  String SECRET = "q4~@x2uC$f@M2x*^e5&DB^L!3~)6@+";

    private final JWTCreator.Builder builder;
    private final JWTVerifier verifier;
    private final Algorithm jwtAlgorithm;

    public TokenManager()
    {
        builder = JWT.create();
        jwtAlgorithm = Algorithm.HMAC256(SECRET);
        verifier = JWT.require(jwtAlgorithm).build();
    }

    public String getToken(Map<String, String> payloadMap)
    {
        Calendar expirationTime = Calendar.getInstance();
        expirationTime.add(Calendar.DATE, 1);

        payloadMap.forEach(builder::withClaim);

        return builder.withExpiresAt(expirationTime.getTime()).sign(jwtAlgorithm);
    }

    public DecodedJWT verify(String token)
    {
        return verifier.verify(token);
    }

    public String getUsernameFromToken(String token)
    {
        DecodedJWT decodedJwt = verify(token);
        return decodedJwt.getClaim("username").asString();
    }
}
