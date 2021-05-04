package com.stefanini.taskmanager.annotation;


import org.aspectj.lang.annotation.Around;

import java.lang.annotation.*;


@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface Notifyable {

}
