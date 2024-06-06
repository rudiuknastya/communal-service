package com.example.user.serviceImpl;

import com.example.user.model.authentication.EmailRequest;
import com.example.user.service.MailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;

@Service
public class MailServiceImpl implements MailService {
    private final TemplateEngine templateEngine;
    private final SendGrid sendGrid;
    private final HttpServletRequest httpServletRequest;

    @Value("${sender}")
    private String sender;
    private final Logger logger = LogManager.getLogger(MailServiceImpl.class);

    public MailServiceImpl(TemplateEngine templateEngine, SendGrid sendGrid,
                           HttpServletRequest httpServletRequest) {
        this.templateEngine = templateEngine;
        this.sendGrid = sendGrid;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public void sendToken(String token, EmailRequest emailRequest) {
        logger.info("sendToken() - Sending token " + token + " to email " + emailRequest.email());
        String subject = "Встановлення нового паролю";
        Content content = new Content("text/html", buildContent(token));
        formAndSendMail(subject, emailRequest.email(), content);
        logger.info("sendToken() - Token was sent");
    }
    private String buildContent(String token) {
        String link = formLink(token);
        Context context = new Context();
        context.setVariable("link", link);
        return templateEngine.process("email/passwordResetTokenEmailTemplate", context);
    }

    private String formLink(String token) {
        StringBuffer fullUrl = httpServletRequest.getRequestURL();
        int index = fullUrl.indexOf("user");
        String baseUrl = fullUrl.substring(0, index);
        String link = baseUrl + "user/changePassword?token=" + token;
        return link;
    }
    private void formAndSendMail(String subject, String to, Content content) {
        logger.info("formAndSendMail() - forming email");
        Mail mail = new Mail(getEmailFromAddress(sender), subject, getEmailFromAddress(to), content);
        try {
            sendMail(mail, to);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }
    private void sendMail(Mail mail, String to) throws IOException {
        Request request = buildRequest(mail);
        logger.info("sendMail() - start sending mail to "+ to);
        sendGrid.api(request);
        logger.info("sendMail() - Mail has been send");
    }
    private Request buildRequest(Mail mail) throws IOException {
        logger.info("buildRequest() - Start");
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        logger.info("buildRequest() - End, success build");
        return request;
    }

    private Email getEmailFromAddress(String address) {
        return new Email(address);
    }
}
