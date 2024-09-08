package wanted.goldroom.authentication.application.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import wanted.goldroom.authentication.domain.user.UserCommand;
import wanted.goldroom.authentication.domain.user.UserInfo;
import wanted.goldroom.authentication.domain.user.UserService;

@Service
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;

    public UserInfo signup(UserCommand command) {
        return userService.signup(command);
    }

    public UserInfo login(UserCommand command) {
        return userService.login(command);
    }
}
