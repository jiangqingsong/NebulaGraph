package com.seres.base.enable;

import com.seres.base.config.CoustomProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableConfigurationProperties(CoustomProperties.class)
public @interface EnableCoustomProperties {

}
