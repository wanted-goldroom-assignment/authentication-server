package wanted.goldroom.authentication.domain.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wanted.goldroom.authentication.infrastructure.common.util.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserReader userReader;
    private final UserStore userStore;

    @Override
    @Transactional
    public UserInfo signup(UserCommand command) {
        userReader.checkDuplicationLoginId(command.getLoginId());

        String encrypted = PasswordEncoder.encode(command.getPassword());
        User user = userStore.store(command.toEntity(encrypted));
        return new UserInfo(user);
    }
}
