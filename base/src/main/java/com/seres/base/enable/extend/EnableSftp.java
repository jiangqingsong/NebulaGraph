package com.seres.base.enable.extend;

import com.seres.base.config.extend.SftpConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SftpConfig.class})
public @interface EnableSftp {

}
