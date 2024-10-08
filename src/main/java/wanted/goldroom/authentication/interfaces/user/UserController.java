package wanted.goldroom.authentication.interfaces.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import wanted.goldroom.authentication.application.token.TokenFacade;
import wanted.goldroom.authentication.application.user.UserFacade;
import wanted.goldroom.authentication.domain.token.TokenInfo;
import wanted.goldroom.authentication.domain.user.UserCommand;
import wanted.goldroom.authentication.domain.user.UserInfo;

@RestController
@RequestMapping("/api/users")
public record UserController(
    UserFacade userFacade,
    UserDtoMapper mapper,
    TokenFacade tokenFacade
) {

    @PostMapping("/auth/signup")
    public ResponseEntity<UserDto.SignupResponse> signup(@RequestBody @Valid UserDto.SignupRequest request) {
        UserCommand command = mapper.of(request);
        UserInfo userInfo = userFacade.signup(command);
        UserDto.SignupResponse response = mapper.of(userInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UserDto.LoginResponse> login(@RequestBody @Valid UserDto.LoginRequest request) {
        UserCommand command = mapper.of(request);
        UserInfo userInfo = userFacade.login(command);
        TokenInfo tokenInfo = tokenFacade.createAuthToken(userInfo);
        UserDto.LoginResponse response = mapper.of(tokenInfo);
        return ResponseEntity.ok(response);
    }
}
