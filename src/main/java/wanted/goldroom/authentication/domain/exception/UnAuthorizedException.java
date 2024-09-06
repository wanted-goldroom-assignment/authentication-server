package wanted.goldroom.authentication.domain.exception;

public class UnAuthorizedException extends BaseException {

    public UnAuthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
