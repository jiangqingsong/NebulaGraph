package com.seres.ngcommonserver.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldAutoMapping {
    String method() default "";

    String type();
}
