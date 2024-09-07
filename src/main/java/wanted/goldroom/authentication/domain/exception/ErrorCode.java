package wanted.goldroom.authentication.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // JWT
    INVALID_AUTH_HEADER("Authorization 헤더의 정보가 유효하지 않습니다."),
    EXPIRED_TOKEN("만료된 토큰입니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다."),

    // USER
    ALREADY_EXIST_ID("이미 존재하는 아이디입니다."),

    // PASSWORD
    PASSWORD_ENCRYPTION_FAIL("비밀번호 암호화에 실패했습니다.");
    private final String message;
}
