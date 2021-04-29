package com.stefanini.taskmanager.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class LoggerAspect {
    private static final Logger LOGGER = Logger.getLogger(LoggerAspect.class);

//    @Around("execution(* *(..)) && @annotation(Loggable)")
//    public Object around(ProceedingJoinPoint point) throws Throwable {
//        long start = System.currentTimeMillis();
//        Object result = point.proceed();
//
//        LOGGER.info(
//                "#%s(%s): %s in %[msec]s",
//                //MethodSignature.class.cast(point.getSignature()).getMethod().getName(),
//                point.getArgs(),
//                result,
//                System.currentTimeMillis() - start
//        );
//
//        return result;
//    }

//    @Before(value = "@annotation(Loggable)")
//    public void beforeMethod() {
//        long start = System.currentTimeMillis();
//
//        LOGGER.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//    }
//
//    @After(value = "@annotation(Loggable)")
//    public void afterMethod() {
//        long start = System.currentTimeMillis();
//
//        LOGGER.info("%%%%%%%%%%%%%%%%%%%%%%%%%%????????????????!");
//    }
}
