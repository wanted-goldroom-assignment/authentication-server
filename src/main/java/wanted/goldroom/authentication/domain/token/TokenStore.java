package wanted.goldroom.authentication.domain.token;

public interface TokenStore {
    RefreshToken store(RefreshToken refreshToken);
}
