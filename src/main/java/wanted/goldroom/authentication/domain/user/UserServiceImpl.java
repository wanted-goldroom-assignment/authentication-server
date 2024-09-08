package wanted.goldroom.authentication.domain.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wanted.goldroom.authentication.domain.exception.BadRequestException;
import wanted.goldroom.authentication.domain.exception.ErrorCode;
import wanted.goldroom.authentication.infrastructure.common.util.PasswordEncoder;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserReader userReader;
    private final UserStore userStore;

    @Override
    @Transactional
    public UserInfo signup(UserCommand command) {
        userReader.checkDuplicationLoginId(command.getLoginId());

        EncryptedPassword encrypted = PasswordEncoder.encode(command.getPassword());
        User user = userStore.store(command.toEntity(encrypted));
        return new UserInfo(user);
    }

    @Override
    public UserInfo login(UserCommand command) {
        User user = validationUserInfo(command);
        return new UserInfo(user);
    }

    private User validationUserInfo(UserCommand command) {
        User user = userReader.findByLoginId(command.getLoginId());
        String encoded = PasswordEncoder.encodeWithSalt(command.getPassword(), user.getSalt());

        if (!user.validatePassword(encoded)) {
            throw new BadRequestException(ErrorCode.MISMATCHED_PASSWORD);
        }
        return user;
    }
}
