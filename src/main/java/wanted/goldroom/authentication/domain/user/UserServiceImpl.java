package wanted.goldroom.authentication.domain.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserReader userReader;
    private final UserStore userStore;

    @Override
    @Transactional
    public UserInfo signup(UserCommand command) {
        userReader.checkDuplicationLoginId(command.getLoginId());
        User user = userStore.store(command.toEntity());
        return new UserInfo(user);
    }
}
