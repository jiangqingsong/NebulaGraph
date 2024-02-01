package com.seres.base.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import javax.annotation.Resource;

/**
 * Swagger配置
 * 启用Swagger2
 * @date 2021年6月8日 上午9:06:49
 */
@Configuration
@EnableSwagger2WebMvc
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(prefix = "swagger", name = "enable", havingValue = "true", matchIfMissing = false)
public class SwaggerConfig extends WebMvcConfigurationSupport {

    /**
     * 注入
     * swagger属性
     */
    @Resource
    private SwaggerProperties swaggerProperties;

    /**
     * addResourceHandlers
     * @param registry
     *
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 添加访问资源，此处通过网关提供ui，如下配置可去掉
//        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     *
     * createRestApi
     * @return
     */
    @Bean
    public Docket createRestApi() {
        //添加head参数start
//        ParameterBuilder tokenPar = new ParameterBuilder();
//        List<Parameter> pars = new ArrayList<>();
//        tokenPar.name(HeaderAndCookieHttpSessionIdResolver.HEADER_X_AUTH_TOKEN).description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
//        pars.add(tokenPar.build());
        //添加head参数end
        return new Docket(DocumentationType.SWAGGER_2)
                // 全局参数
//                .globalOperationParameters(pars)
                .apiInfo(apiInfo())
                .select()
                // 包路径
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * apiInfo
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //标题
                .title(swaggerProperties.getTitle())
                //描述
                .description(swaggerProperties.getDescription())
                //版本号
                .version(swaggerProperties.getVersion())
                .build();
    }

}