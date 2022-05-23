package com.venson.msmservice.service.impl;

import com.venson.msmservice.service.MsmService;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MsmServiceImpl implements MsmService  {
    private MailSender mailSender;
    private SimpleMailMessage simpleMailMessage;
    public void setMailSender(MailSender mailSender){
        this.mailSender = mailSender;
    }
    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage){
        this.simpleMailMessage = simpleMailMessage;
    }

    @Override
    public boolean sendCode(String emailUrl){
        SimpleMailMessage msg = new SimpleMailMessage(this.simpleMailMessage);
        msg.setTo(emailUrl);
        msg.setText("");
        return true;
    }
}
