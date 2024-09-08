package wanted.goldroom.authentication.domain.token;

public record TokenInfo(
    String accessToken,
    String refreshToken
) {
}
