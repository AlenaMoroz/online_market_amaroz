package com.gmail.marozalena.onlinemarket.service.impl;

import com.gmail.marozalena.onlinemarket.service.RandomPasswordService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
public class RandomPasswordServiceImpl implements RandomPasswordService {

    private final Random random = new SecureRandom();
    private final String chars = "qwertyuiopasdfghjklzxcvbnm";
    private final String numbers = "1234567890";


    @Override
    public String getRandomPassword() {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
            password.append(Character.toUpperCase(chars.charAt(random.nextInt(chars.length()))));
            password.append(numbers.charAt(random.nextInt(numbers.length())));
        }
        return new String(password);
    }
}
