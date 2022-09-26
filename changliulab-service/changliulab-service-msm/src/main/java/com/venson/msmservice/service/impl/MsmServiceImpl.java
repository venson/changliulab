package com.venson.msmservice.service.impl;

import com.venson.msmservice.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Service
public class MsmServiceImpl implements MsmService  {

    @Autowired
    private JavaMailSender sender;
    @Autowired
    private SpringTemplateEngine springTemplateEngine;


    @Override
    public boolean sendCode(String emailUrl, String code,String codeName, String mailPurpose,Integer expire){
        MimeMessage mimeMessageMsg = sender.createMimeMessage();
        String subject = "Changliu Lab " + mailPurpose ;

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessageMsg,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            HashMap<String,Object> values = new HashMap<>();
            values.put("codeName",codeName);
            values.put("code",code);
            values.put("mailPurpose",mailPurpose);
            values.put("expire", expire);
            Context context = new Context();
            context.setVariables(values);
            // use template to generate email context
            String htmlPage = springTemplateEngine.process("sendCode.html", context);
            helper.setTo(emailUrl);
            try {
                helper.setFrom("info@changliulab.com","Changliulab Admin");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            helper.setSubject(subject);
            helper.setText(htmlPage,true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        sender.send(mimeMessageMsg);


        return true;
    }
}
