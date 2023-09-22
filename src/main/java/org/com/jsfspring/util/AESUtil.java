package org.com.jsfspring.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class AESUtil {

    @Value("${aes.key}")
    private String PRIVATE_KEY;

    public String encrypt(String strToEncrypt) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(PRIVATE_KEY.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Процесс шифрования прерван -" + e.getMessage());
        }
        return null;
    }

    public String decrypt(String encryptedString) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(PRIVATE_KEY.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES"); //todo
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder()
                    .decode(encryptedString)));
        } catch (Exception e) {
            System.out.println("процесс дещифрования прерван -" + e.getMessage());
        }
        return null;
    }
}
