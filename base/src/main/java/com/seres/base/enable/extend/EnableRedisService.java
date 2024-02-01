package com.seres.base.enable.extend;

import com.seres.base.tool.redis.RedisService;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({RedisService.class})
public @interface EnableRedisService {

}
