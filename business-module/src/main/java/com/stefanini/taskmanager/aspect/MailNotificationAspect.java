package com.stefanini.taskmanager.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class MailNotificationAspect {
    private static final Logger log = Logger.getLogger(MailNotificationAspect.class);

//    @Around("execution(* *(..)) && @annotation(Notifyable)")
    public Object sendMail(ProceedingJoinPoint pjp){
        log.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!HEY");
        return null;
    }
}
