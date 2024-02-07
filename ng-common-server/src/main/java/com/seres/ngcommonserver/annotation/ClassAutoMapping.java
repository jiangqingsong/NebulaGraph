package com.seres.ngcommonserver.annotation;
import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/5 17:14
 */
public @interface ClassAutoMapping {
    String value() default "";
}
