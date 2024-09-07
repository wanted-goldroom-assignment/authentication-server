package wanted.goldroom.authentication.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UserCommand {
    private final String loginId;
    private final String password;

    public User toEntity() {
        return User.builder()
            .loginId(loginId)
            .password(password)
            .build();
    }
}
