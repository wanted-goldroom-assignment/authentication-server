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

    public User toEntity(EncryptedPassword encrypted) {
        return User.builder()
            .loginId(loginId)
            .password(encrypted.encoded())
            .salt(encrypted.salt())
            .build();
    }
}
