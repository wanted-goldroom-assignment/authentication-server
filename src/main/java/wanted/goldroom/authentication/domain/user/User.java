package wanted.goldroom.authentication.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.goldroom.authentication.infrastructure.common.util.TokenGenerator;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    private static final String PREFIX_USER = "user_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userToken;
    private String loginId;
    private String password;

    @Builder
    public User(String loginId, String password) {
        this.userToken = TokenGenerator.randomCharacterWithPrefix(PREFIX_USER);
        this.loginId = loginId;
        this.password = password;
    }
}
