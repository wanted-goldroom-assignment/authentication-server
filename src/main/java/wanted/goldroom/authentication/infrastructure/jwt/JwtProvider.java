package wanted.goldroom.authentication.infrastructure.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import wanted.goldroom.authentication.domain.exception.ErrorCode;
import wanted.goldroom.authentication.domain.exception.UnAuthorizedException;
import wanted.goldroom.authentication.infrastructure.common.properties.JwtProperties;

@Component
public class JwtProvider {
    private final SecretKey secretKey;
    private final long accessTokenExpirationTime;

    public JwtProvider(JwtProperties jwtProperties) {
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.secretKey().getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpirationTime = jwtProperties.accessTokenExpirationTime();
    }

    public String createAccessToken(String userToken) {
        Date now = new Date();
        Date accessTokenExpiration = new Date(now.getTime() + accessTokenExpirationTime);

        return Jwts.builder()
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .setIssuedAt(now)
            .setExpiration(accessTokenExpiration)
            .setClaims(Map.of("userToken", userToken))
            .compact();
    }

    public void validateToken(final String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new UnAuthorizedException(ErrorCode.EXPIRED_TOKEN);
        } catch (JwtException e) {
            throw new UnAuthorizedException(ErrorCode.INVALID_TOKEN);
        }
    }
}
