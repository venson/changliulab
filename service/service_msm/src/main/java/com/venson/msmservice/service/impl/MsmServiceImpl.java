package com.venson.msmservice.service.impl;

import com.venson.msmservice.service.MsmService;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Service
public class MsmServiceImpl implements MsmService  {

    private final JavaMailSenderImpl sender;
    private final SpringTemplateEngine springTemplateEngine;

    public MsmServiceImpl(JavaMailSenderImpl javaMailSender, SpringTemplateEngine springTemplateEngine) {
        this.sender = javaMailSender;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    public boolean sendCode(String emailUrl, String code, String type ){
        MimeMessage mimeMessageMsg = sender.createMimeMessage();
        String subject = "Changliu Lab " + type ;

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessageMsg,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            HashMap<String,Object> values = new HashMap<>();
            values.put("type",type);
            values.put("code",code);
            Context context = new Context();
            context.setVariables(values);
            // use template to generate email context
            String htmlPage = springTemplateEngine.process("sendCode.html", context);
            helper.setTo(emailUrl);
            helper.setFrom(sender.getUsername());
            helper.setSubject(subject);
            helper.setText(htmlPage,true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        sender.send(mimeMessageMsg);


        return true;
    }
}
