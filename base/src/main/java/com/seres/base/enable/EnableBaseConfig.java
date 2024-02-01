package com.seres.base.enable;


import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
//@Documented
//@Inherited

//@EnableSwagger
@EnableTraceId
//@EnableSecurity
@EnableGlobalException
@EnableCoustomProperties
@EnableFastJsonConverters
//@EnableFeignRequestTransfer
public @interface EnableBaseConfig {

}
