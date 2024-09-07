package wanted.goldroom.authentication.infrastructure.user;

import org.springframework.data.jpa.repository.JpaRepository;

import wanted.goldroom.authentication.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByLoginId(String loginId);
}
