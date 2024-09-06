package wanted.goldroom.authentication.infrastructure.jwt;

import static org.assertj.core.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import wanted.goldroom.authentication.domain.exception.ErrorCode;
import wanted.goldroom.authentication.domain.exception.UnAuthorizedException;
import wanted.goldroom.authentication.infrastructure.common.properties.JwtProperties;

class JwtProviderTest {
    private final String secretKey = "2901ujr9021urf0u902hf021y90fh9c210hg093hg091h3g90h30gh901hg09h01";
    private final JwtProvider jwtProvider = new JwtProvider(new JwtProperties(secretKey, 10000, 100000));

    @DisplayName("회원의 PK가 payload로 주어지면 엑세스 토큰이 생성되는데 성공한다.")
    @Test
    void whenCreateAccessToken_thenSuccess() {
        // given

        // when
        String accessToken = jwtProvider.createAccessToken("randomUserToken");

        // then
        assertThat(accessToken).isNotBlank();
    }

    @DisplayName("유효하지 않은 토큰이 주어지면 토큰을 검증할 때 예외가 던져진다.")
    @Test
    void givenInvalidToken_thenThrowsException() {
        // given
        String invalidToken = "as.12.34.121";

        // when & then
        assertThatThrownBy(() -> jwtProvider.validateToken(invalidToken))
            .isInstanceOf(UnAuthorizedException.class)
            .extracting("errorCode").isEqualTo(ErrorCode.INVALID_TOKEN);
    }

    @DisplayName("만료된 토큰이 주어지면 토큰을 검증할 때 예외가 던져진다.")
    @Test
    void givenExpiredToken_thenThrowsException() {
        // given
        Date now = new Date();
        String expiredToken = Jwts.builder()
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() - 1))
            .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
            .compact();

        // when & then
        assertThatThrownBy(() -> jwtProvider.validateToken(expiredToken))
            .isInstanceOf(UnAuthorizedException.class)
            .extracting("errorCode").isEqualTo(ErrorCode.EXPIRED_TOKEN);
    }
}
