package wanted.goldroom.authentication.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.goldroom.authentication.common.util.TokenGenerator;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    private static final String PREFIX_USER = "user_";

    @Id
    @GeneratedValue
    private Long id;
    private String userToken;

    @NotBlank(message = "로그인 아이디를 입력해주세요.")
    @Size(min = 4, max = 10, message = "최소 4자 이상, 10자 이하로 입력해주세요.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 15, message = "최소 8자 이상, 15자 이하로 입력해주세요.")
    @Pattern(regexp = "[a-zA-Z0-9\\W_]{8,15}", message = "영문, 숫자, 특수문자 조합으로 이루어진 8~15자의 비밀번호를 입력해주세요.")
    private String password;

    @Builder
    public User(String loginId, String password) {
        this.userToken = TokenGenerator.randomCharacterWithPrefix(PREFIX_USER);
        this.loginId = loginId;
        this.password = password;
    }
}
