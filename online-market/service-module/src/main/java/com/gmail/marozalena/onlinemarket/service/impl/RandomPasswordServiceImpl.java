package com.gmail.marozalena.onlinemarket.service.impl;

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

    @Autowired
    public RandomPasswordServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public String getRandomPassword(String email) {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
            password.append(Character.toUpperCase(chars.charAt(random.nextInt(chars.length()))));
            password.append(numbers.charAt(random.nextInt(numbers.length())));
        }
        logger.info("For user with email=" + email + " was generated new password"+password);
        return encoder(password.toString());
    }

    private String encoder(String password) {
        return passwordEncoder.encode(password);
    }

}
