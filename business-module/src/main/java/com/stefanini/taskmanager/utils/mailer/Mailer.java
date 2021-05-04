package com.stefanini.taskmanager.utils.mailer;

import com.stefanini.taskmanager.annotation.Loggable;
import com.stefanini.taskmanager.utils.DBParamsConfig;
import org.apache.log4j.Logger;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mailer<T> {
    private static final Logger log = Logger.getLogger(Mailer.class);
    private static final Properties mailerProps = DBParamsConfig.getProperties();
    private static final String MAIL_ADDRESS_TO;
    private static final String MAIL_ADDRESS_FROM;
    private static final String MAIL_PASSWORD;
    private static final Properties MAIL_SERVER_PROPERTIES = new Properties();
    private static final Session session;

    static {
        MAIL_ADDRESS_TO = mailerProps.getProperty("mailer.to");
        MAIL_ADDRESS_FROM = mailerProps.getProperty("mailer.from");
        MAIL_PASSWORD = mailerProps.getProperty("mailer.password");
        MAIL_SERVER_PROPERTIES.put("mail.smtp.starttls.enable", mailerProps.getProperty("mail.smtp.starttls.enable"));
        MAIL_SERVER_PROPERTIES.put("mail.smtp.auth", mailerProps.getProperty("mail.smtp.auth"));
        MAIL_SERVER_PROPERTIES.put("mail.smtp.host", mailerProps.getProperty("mail.smtp.host"));
        MAIL_SERVER_PROPERTIES.put("mail.smtp.port", mailerProps.getProperty("mail.smtp.port"));
        MAIL_SERVER_PROPERTIES.put("mail.smtp.ssl.trust", mailerProps.getProperty("mail.smtp.ssl.trust"));

        session = Session.getInstance(MAIL_SERVER_PROPERTIES,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(MAIL_ADDRESS_FROM, MAIL_PASSWORD);
                    }
                });
    }

    @Loggable
    public void sendMail(MimeMessage message) {
        try {
            Transport.send(message);
            log.info("Mail notification sent successfully");
        } catch (MessagingException mex) {
            log.error("Mail notification sent error");
            mex.printStackTrace();
        }
    }

    public Properties getMailerProps() {
        return mailerProps;
    }

    public String getMailAddressTo() {
        return MAIL_ADDRESS_TO;
    }

    public String getMailAddressFrom() {
        return MAIL_ADDRESS_FROM;
    }

    public String getMailPassword() {
        return MAIL_PASSWORD;
    }

    public Properties getMailServerProperties() {
        return MAIL_SERVER_PROPERTIES;
    }

    public Session getSession() {
        return session;
    }
}
