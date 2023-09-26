package org.com.jsfspring.util;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * отправка mail На почту
 */
@RequiredArgsConstructor
@Service
public class EmailUtil {

    private final JavaMailSender javaMailSender;

    private final Environment environment;

    /**
     *
     * @param toEmail - почта(куда отправить)
     * @param subject - (тема)
     * @param text - (текст письма)
     *
     */
    public void send(String toEmail, String subject, String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setFrom(Objects.requireNonNull(environment.getProperty("spring.mail.username")));
        javaMailSender.send(simpleMailMessage);
    }

    /**
     * Проверка почты на валидность
     * @param email
     * @return true-false
     */
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
