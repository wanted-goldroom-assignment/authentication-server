package wanted.goldroom.authentication.infrastructure.token;

import org.springframework.data.jpa.repository.JpaRepository;

import wanted.goldroom.authentication.domain.token.RefreshToken;

public interface TokenRepository extends JpaRepository<RefreshToken, String> {
}
