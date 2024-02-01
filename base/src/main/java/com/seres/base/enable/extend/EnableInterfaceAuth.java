package com.seres.base.enable.extend;

import com.seres.base.config.extend.InterfaceAuthConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({InterfaceAuthConfig.class})
public @interface EnableInterfaceAuth {

}
