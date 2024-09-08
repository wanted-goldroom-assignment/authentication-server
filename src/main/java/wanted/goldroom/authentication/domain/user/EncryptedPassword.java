package wanted.goldroom.authentication.domain.user;

public record EncryptedPassword(
    String encoded,
    String salt
) {

}
