package wanted.goldroom.authentication.application.token;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import wanted.goldroom.authentication.domain.token.TokenInfo;
import wanted.goldroom.authentication.domain.token.TokenService;
import wanted.goldroom.authentication.domain.user.UserInfo;

@Service
@RequiredArgsConstructor
public class TokenFacade {
    private final TokenService tokenService;

    public TokenInfo createAuthToken(UserInfo userInfo) {
        return tokenService.createToken(userInfo);
    }
}
