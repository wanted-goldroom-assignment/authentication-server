package wanted.goldroom.authentication.infrastructure.token;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import wanted.goldroom.authentication.domain.token.RefreshToken;
import wanted.goldroom.authentication.domain.token.TokenStore;

@Component
@RequiredArgsConstructor
public class TokenStoreImpl implements TokenStore {
    private final TokenRepository tokenRepository;

    @Override
    public RefreshToken store(RefreshToken refreshToken) {
        return tokenRepository.save(refreshToken);
    }
}
