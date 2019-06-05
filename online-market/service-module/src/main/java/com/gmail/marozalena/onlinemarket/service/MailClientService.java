package com.gmail.marozalena.onlinemarket.service;

public interface MailClientService {

    void prepareAndSend(String recipient, String message);
}
