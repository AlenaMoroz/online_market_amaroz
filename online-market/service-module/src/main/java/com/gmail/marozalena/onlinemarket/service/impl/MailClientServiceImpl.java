package com.gmail.marozalena.onlinemarket.service.impl;

import com.gmail.marozalena.onlinemarket.service.MailClientService;
import com.gmail.marozalena.onlinemarket.service.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class MailClientServiceImpl implements MailClientService {

    private static final Logger logger = LoggerFactory.getLogger(MailClientServiceImpl.class);

    private JavaMailSender mailSender;

    @Autowired
    public MailClientServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void prepareAndSend(String recipient, String message){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom("online.market.amaroz@gmail.com");
            helper.setTo(recipient);
            helper.setSubject("New Password");
            helper.setText(message);
        };
        try{
            mailSender.send(messagePreparator);
        }catch (MailException e){
            logger.error(e.getMessage(), e);
        }
    }
}
