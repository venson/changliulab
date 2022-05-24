package com.venson.msmservice.service.impl;

import com.venson.msmservice.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotNull;

@Service
public class MsmServiceImpl implements MsmService  {

//    private final MailProperties mailProperties;
    private final JavaMailSenderImpl sender;

    public MsmServiceImpl(JavaMailSenderImpl javaMailSender, MailProperties mailProperties) {
        this.sender = javaMailSender;
//        this.mailProperties = mailProperties;
    }

    @Override
    public boolean sendCode(@NotNull String emailUrl, String code){
        MimeMessage mimeMessagemsg = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessagemsg);
        String subject = "Changliu Lab registration Security Code";
        String content =
                "<table dir=\"ltr\">"
                        + "<tr><td id=\"i1\" style=\"padding:0; font-family:'Segoe UI Semibold', 'Segoe UI Bold', 'Segoe UI', 'Helvetica Neue Medium', Arial, sans-serif; font-size:17px; color:#707070;\">"
                        +"Changliu Lab account</td></tr>"
                        + "<tr><td id=\"i2\" style=\"padding:0; font-family:'Segoe UI Light', 'Segoe UI', 'Helvetica Neue Medium', Arial, sans-serif; font-size:41px; color:#2672ec;\">"
                        +"Security code</td></tr>"
                        +"<tr><td id=\"i3\" style=\"padding:0; padding-top:25px; font-family:'Segoe UI', Tahoma, Verdana, Arial, sans-serif; font-size:14px; color:#2a2a2a;\">"
                        +"Please use the following security code for the Changliu Lab account .</td></tr>"
                        +"<tr><td id=\"i4\" style=\"padding:0; padding-top:25px; font-family:'Segoe UI', Tahoma, Verdana, Arial, sans-serif; font-size:14px; color:#2a2a2a;\">Security code: "
                        + "<span style=\"font-family:'Segoe UI Bold', 'Segoe UI Semibold', 'Segoe UI', 'Helvetica Neue Medium', Arial, sans-serif; font-size:20px; font-weight:bold; color:#2a2a2a;\">"+code+"</span></td></tr>"
                        +"<tr><td id=\"i6\" style=\"padding:0; padding-top:25px; font-family:'Segoe UI', Tahoma, Verdana, Arial, sans-serif; font-size:14px; color:#2a2a2a;\">"
                        +"If you didn't request this code, you can safely ignore this email. Someone else might have typed your email address by mistake.</td></tr>"
                        +"<tr><td id=\"i7\" style=\"padding:0; padding-top:25px; font-family:'Segoe UI', Tahoma, Verdana, Arial, sans-serif; font-size:14px; color:#2a2a2a;\">Thanks,</td></tr>"
                        +"<tr><td id=\"i8\" style=\"padding:0; font-family:'Segoe UI', Tahoma, Verdana, Arial, sans-serif; font-size:14px; color:#2a2a2a;\">"
                        +"The Changliu Lab account team</td></tr>"
                        +"</table>";
        try {
            helper.setTo(emailUrl);
//            helper.setFrom(mailProperties.getUsername());
            helper.setFrom("changliulab@hotmail.com");
            helper.setSubject(subject);
            helper.setText(content,true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        sender.send(mimeMessagemsg);

        return true;
    }
}
