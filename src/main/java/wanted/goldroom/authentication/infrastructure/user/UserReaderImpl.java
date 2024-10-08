package wanted.goldroom.authentication.infrastructure.user;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import wanted.goldroom.authentication.domain.exception.BadRequestException;
import wanted.goldroom.authentication.domain.exception.ErrorCode;
import wanted.goldroom.authentication.domain.exception.NotFoundException;
import wanted.goldroom.authentication.domain.user.User;
import wanted.goldroom.authentication.domain.user.UserReader;

@Component
@RequiredArgsConstructor
public class UserReaderImpl implements UserReader {
    private final UserRepository userRepository;

    @Override
    public void checkDuplicationLoginId(String loginId) {
        if (userRepository.existsByLoginId(loginId)) {
            throw new BadRequestException(ErrorCode.ALREADY_EXIST_ID);
        }
    }

    @Override
    public User findByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));
    }
}
