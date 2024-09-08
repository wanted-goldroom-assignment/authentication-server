package wanted.goldroom.authentication.domain.token;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wanted.goldroom.authentication.domain.user.UserInfo;
import wanted.goldroom.authentication.infrastructure.jwt.JwtProvider;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final JwtProvider jwtProvider;
    private final TokenStore tokenStore;

    @Override
    @Transactional
    public TokenInfo createToken(UserInfo userInfo) {
        String userToken = userInfo.getUserToken();
        String accessToken = jwtProvider.createAccessToken(userToken);
        String refreshToken = jwtProvider.createRefreshToken(userToken);
        tokenStore.store(new RefreshToken(userToken, refreshToken));
        return new TokenInfo(accessToken, refreshToken);
    }
}
