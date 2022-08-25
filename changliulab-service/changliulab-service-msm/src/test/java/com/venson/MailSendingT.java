package com.venson;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSendingT {
    @Test
    public void mailTest(){

        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.transport.protocol","smtp");
        properties.setProperty("mail.smtp.starttls.require", "true");
        properties.setProperty("mail.smtp.auth", "true");
        sender.setHost("smtp.office365.com");
        sender.setPassword("changliu&hasi");
        sender.setUsername("changliulab@hotmail.com");
        sender.setPort(587);
        sender.setProtocol("smtp");
        sender.setJavaMailProperties(properties);
        MimeMessage mimeMessagemsg = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessagemsg);
        SimpleMailMessage msg = new SimpleMailMessage();
        String subject = "Changliu Lab registration Security Code";
        String code = "12345";
        String content =
//                "<!DOCTYPE html> "
//                + "<head>"
//                        + "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
//                        + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>"
//                +  "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"
//                        + "<title>Changliu Lab account</title>"
//                        + "</head>"
//                        + "<body>"
                         "Changliu Lab account<br>"
                        + "<h2 color=\"#2672EC\">Securrity code</h2><br>"
                        + "Please use the following security code for the Changliu lab account<br>"
                        + "Security code:" + "<span font-size:16px font-weight:bold>" +"123123" + "</span><br>"
                        + "If you didn't request this code, you can safely ignore this email. "
                        + "Someone else might have typed your email address by mistake.\n<br>" +
                        "Thanks,\n" +
                        "The Changliu Lab account team<br>"
//                        + "</body>"
//                + "</html>"
                ;
        String content2 =
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

//        try {
//            helper.setTo(emailUrl);
//            msg.setSubject("Changliu Lab registration Security Code");
//            msg.setText(content);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
        try {
            helper.setFrom("changliulab@hotmail.com");
            helper.setTo("406574996@qq.com");
            helper.setText(content2,true);
            helper.setSubject(subject);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
//        msg.setFrom("changliulab@hotmail.com");
//        msg.setSubject(subject);
//        msg.setText(content);
        sender.send(mimeMessagemsg);
    }
}
