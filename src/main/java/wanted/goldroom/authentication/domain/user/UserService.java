package wanted.goldroom.authentication.domain.user;

public interface UserService {
    UserInfo signup(UserCommand command);

    UserInfo login(UserCommand command);
}
