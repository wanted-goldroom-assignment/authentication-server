package wanted.goldroom.authentication.interfaces.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDto {

    public record SignupRequest(
        @NotBlank(message = "로그인 아이디를 입력해주세요.")
        @Size(min = 4, max = 10, message = "최소 4자 이상, 10자 이하로 입력해주세요.")
        String loginId,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Size(min = 8, max = 15, message = "최소 8자 이상, 15자 이하로 입력해주세요.")
        @Pattern(regexp = "[a-zA-Z0-9\\W_]{8,15}", message = "영문, 숫자, 특수문자 조합으로 이루어진 8~15자의 비밀번호를 입력해주세요.")
        String password
    ) {

    }

    public record SignupResponse(
        String userToken
    ) {

    }

    public record LoginRequest(
        @NotBlank(message = "로그인 아이디를 입력해주세요.")
        @Size(min = 4, max = 10, message = "최소 4자 이상, 10자 이하로 입력해주세요.")
        String loginId,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Size(min = 8, max = 15, message = "최소 8자 이상, 15자 이하로 입력해주세요.")
        @Pattern(regexp = "[a-zA-Z0-9\\W_]{8,15}", message = "영문, 숫자, 특수문자 조합으로 이루어진 8~15자의 비밀번호를 입력해주세요.")
        String password
    ) {

    }

    public record LoginResponse(
        String accessToken,
        String refreshToken
    ) {

    }
}
