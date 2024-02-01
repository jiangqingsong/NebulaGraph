package com.seres.base.enable.extend;

import com.seres.base.config.extend.DataClearConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({DataClearConfig.class})
public @interface EnableDataClearSchedule {

}
