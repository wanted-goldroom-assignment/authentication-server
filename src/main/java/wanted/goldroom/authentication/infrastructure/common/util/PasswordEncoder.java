package wanted.goldroom.authentication.infrastructure.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wanted.goldroom.authentication.domain.exception.ErrorCode;
import wanted.goldroom.authentication.domain.exception.InternalServerException;
import wanted.goldroom.authentication.domain.user.EncryptedPassword;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PasswordEncoder {
    private static final int SALT_LENGTH = 20;
    private static final String ALGORITHM = "SHA-256";

    public static EncryptedPassword encode(String plainText) {
        String salt = getSalt();
        return new EncryptedPassword(
            getEncrypt(plainText, salt),
            salt
        );
    }

    public static String encodeWithSalt(String plainText, String salt) {
        return getEncrypt(plainText, salt);
    }

    private static String getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);

        return bytesToHex(salt);
    }

    private static String getEncrypt(String plainText, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            digest.update((plainText + salt).getBytes());
            byte[] hashedBytes = digest.digest();

            return bytesToHex(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            log.error("""
                | 비밀번호 암호화 실패
                | Error : {}, {}
                """, e, e.getMessage());
            throw new InternalServerException(ErrorCode.PASSWORD_ENCRYPTION_FAIL);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
