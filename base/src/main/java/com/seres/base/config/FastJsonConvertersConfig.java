package com.seres.base.config;

import com.seres.base.Constants;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

/**
 * FastJsonConverters配置
 */
@Slf4j
@Configuration
public class FastJsonConvertersConfig {

    /**
     * 覆盖方法configureMessageConverters，使用fastJson
     * @return
     */
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);

        //1、定义一个convert转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        fastConverter.setSupportedMediaTypes(supportedMediaTypes);
        //2、添加fastjson的配置信息
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        // 全局配置时间转换格式，优先级低于属性上面添加注解：@JSONField(format = "yyyy-MM-dd HH:mm:ss")
        fastJsonConfig.setDateFormat(Constants.DF_YMDHMS);
        //3、在convert中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);
        //4、将convert添加到converters中
        return new HttpMessageConverters(fastConverter);
    }

}
