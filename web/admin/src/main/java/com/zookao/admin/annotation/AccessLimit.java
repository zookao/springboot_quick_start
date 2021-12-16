package com.zookao.admin.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimit {

    double perSecond() default Double.MAX_VALUE;

    int timeOut() default 0;

    TimeUnit timeOutUnit() default TimeUnit.MILLISECONDS;
}
