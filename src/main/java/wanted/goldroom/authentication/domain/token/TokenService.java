package wanted.goldroom.authentication.domain.token;

import wanted.goldroom.authentication.domain.user.UserInfo;

public interface TokenService {
    TokenInfo createToken(UserInfo userInfo);
}
