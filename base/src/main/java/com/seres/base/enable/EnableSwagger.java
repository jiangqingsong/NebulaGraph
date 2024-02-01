package com.seres.base.enable;

import com.seres.base.config.SwaggerConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SwaggerConfig.class})
public @interface EnableSwagger {

}
