package com.stefanini.taskmanager.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.text.SimpleDateFormat;
import java.util.Date;


@Aspect
public class LoggerAspect {
    private static final Logger LOGGER = Logger.getLogger(LoggerAspect.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @Pointcut("execution(* *(..)) && @annotation(com.stefanini.taskmanager.annotation.Loggable)")
    public void logMethod() {
    }

    @Before(value = "logMethod()")
    public void logBeforeMethod(JoinPoint joinPoint) {
        Class<?> executingClass = joinPoint.getThis().getClass();
        Signature methodName = joinPoint.getSignature();
        String timeStamp = "[" + dateFormat.format(new Date()) + "]";

        LOGGER.info(timeStamp + " - BEFORE EXEC: " + executingClass.getSimpleName() + " - [" + methodName + "]");
    }

    @After(value = "logMethod()")
    public void logAfterMethod(JoinPoint joinPoint) {
        Class<?> executingClass = joinPoint.getThis().getClass();
        Signature methodName = joinPoint.getSignature();
        String timeStamp = "[" + dateFormat.format(new Date()) + "]";

        LOGGER.info(timeStamp + " - AFTER EXEC: " + executingClass.getSimpleName() + " - [" + methodName + "]");
    }
}
