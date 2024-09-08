package wanted.goldroom.authentication.domain.exception.handler;

import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import wanted.goldroom.authentication.domain.exception.BadRequestException;
import wanted.goldroom.authentication.domain.exception.ErrorResponse;
import wanted.goldroom.authentication.domain.exception.InternalServerException;
import wanted.goldroom.authentication.domain.exception.NotFoundException;
import wanted.goldroom.authentication.domain.exception.UnAuthorizedException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException exception) {
        ErrorResponse body = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            exception.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .distinct()
                .collect(Collectors.joining(", ", "[", "]"))
        );
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getErrorCode().getMessage()));
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnAuthorizedException(UnAuthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getErrorCode().getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getErrorCode().getMessage()));
    }

    @ExceptionHandler(value = {Exception.class, InternalServerException.class})
    public ResponseEntity<ErrorResponse> handleUnExpectedException(Exception e) {
        log.error("""
            | INTERNAL_SERVER_ERROR!
            | Error : {} | {}
            """, e, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "예상치 못한 문제가 발생했습니다. 다시 시도해주세요"));
    }
}
