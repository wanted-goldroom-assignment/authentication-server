package wanted.goldroom.authentication.domain.exception;

public record ErrorResponse(
    int statusCode,
    String message) {

}
