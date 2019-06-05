package com.gmail.marozalena.onlinemarket.service.impl;

import com.gmail.marozalena.onlinemarket.service.MailClientService;
import com.gmail.marozalena.onlinemarket.service.RandomPasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
public class RandomPasswordServiceImpl implements RandomPasswordService {

    private static final Logger logger = LoggerFactory.getLogger(RandomPasswordServiceImpl.class);

    private final Random random = new SecureRandom();
    private final String chars = "qwertyuiopasdfghjklzxcvbnm";
    private final String numbers = "1234567890";

    private final PasswordEncoder passwordEncoder;
    private final MailClientService mailClientService;

    @Autowired
    public RandomPasswordServiceImpl(PasswordEncoder passwordEncoder,
                                     MailClientService mailClientService) {
        this.passwordEncoder = passwordEncoder;
        this.mailClientService = mailClientService;
    }


    @Override
    public String getRandomPassword(String email) {
        StringBuilder password = new StringBuilder();
        /* optimal length for autogenerated password is 9
        * we get one char in upper case, one char in lower case and one number
        * 9 / 3 = 3
        * so we need to repeat the cycle three times */
        for (int i = 0; i < 3; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
            password.append(Character.toUpperCase(chars.charAt(random.nextInt(chars.length()))));
            password.append(numbers.charAt(random.nextInt(numbers.length())));
        }
        String message = "Your password: " + password;
        mailClientService.prepareAndSend(email, message);
        logger.info("For user with email = " + email + " was generated password: "+password);
        return encoder(password.toString());
    }

    private String encoder(String password) {
        return passwordEncoder.encode(password);
    }

}
