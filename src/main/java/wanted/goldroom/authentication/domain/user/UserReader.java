package wanted.goldroom.authentication.domain.user;

public interface UserReader {
    void checkDuplicationLoginId(String loginId);

    User findByLoginId(String loginId);
}
