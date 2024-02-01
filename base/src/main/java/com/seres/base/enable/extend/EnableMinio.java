package com.seres.base.enable.extend;

import com.seres.base.tool.minio.MinioProperties;
import com.seres.base.tool.minio.MinioService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableConfigurationProperties(MinioProperties.class)
@Import({MinioService.class})
public @interface EnableMinio {

}
