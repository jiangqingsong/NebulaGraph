package com.seres.base.enable.extend;

import com.seres.base.config.extend.IPAuthConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({IPAuthConfig.class})
public @interface EnableIPAuth {

}
