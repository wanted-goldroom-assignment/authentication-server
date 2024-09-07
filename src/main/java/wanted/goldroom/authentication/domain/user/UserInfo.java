package wanted.goldroom.authentication.domain.user;

import lombok.Getter;

@Getter
public class UserInfo {
    private String userToken;

    public UserInfo(User user) {
        this.userToken = user.getUserToken();
    }
}
