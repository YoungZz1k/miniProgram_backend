package com.miniProgram.common;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiOperationLog {

    /**
     * API 功能描述
     * @return
     */
    String description() default "";
}
