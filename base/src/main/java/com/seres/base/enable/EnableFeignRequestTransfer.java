package com.seres.base.enable;

import com.seres.base.config.FeignRequestTransferConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({FeignRequestTransferConfig.class})
public @interface EnableFeignRequestTransfer {

}
