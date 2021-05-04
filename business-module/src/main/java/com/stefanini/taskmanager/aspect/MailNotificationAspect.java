package com.stefanini.taskmanager.aspect;

import com.stefanini.taskmanager.entity.Task;
import com.stefanini.taskmanager.entity.User;
import com.stefanini.taskmanager.utils.mailer.Mailer;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
public class MailNotificationAspect {
    private static final Logger log = Logger.getLogger(MailNotificationAspect.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @AfterReturning("execution(* *(..)) && @annotation(com.stefanini.taskmanager.annotation.Notifyable)")
    public void sendMail(JoinPoint point) {
        Class<?> executingClass = point.getClass();
        Signature methodName = point.getSignature();
        String timeStamp = "[" + dateFormat.format(new Date()) + "]";
        User usr;

        try {
            usr = (User) point.getArgs()[0];
            Task task = (Task) point.getArgs()[1];
            String mailSubject = "Notification: user " + usr.getFirstName() + " " + usr.getLastName() + " created";
            String mailContent = "User " + usr.getFirstName() + " " + usr.getLastName() + "identified by " + usr.getUserName() + " has been created.\n" +
                    "Task " + task.getTitle() + " with description:" + task.getDescription() + " has been assigned to " + usr.getUserName();
            Mailer<User> mailer = new Mailer<>();

            MimeMessage message = new MimeMessage(mailer.getSession());
            message.setFrom(new InternetAddress(mailer.getMailAddressFrom()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailer.getMailAddressTo()));
            message.setSubject(mailSubject);
            message.setText(mailContent);

            mailer.sendMail(message);
        } catch (Throwable thr) {
            log.error(timeStamp + " - AROUND EXEC: " + executingClass.getSimpleName() + " - [" + methodName + "]");
            thr.printStackTrace();
        }
    }
}
