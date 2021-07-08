package com.example.config;

import com.example.properties.SwaggerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Configuration
// (必选)添加开关注解@EnableOpenApi
@EnableOpenApi
public class SwaggerConfig {

    @Value("${server.port}")
    private String port;

    @Autowired
    private SwaggerProperties swaggerProperties;

    /**
     * 接口分组,此类接口需要token验证,以扫描的包路径来区分
     */
    @Bean(value = "defaultApi")
    public Docket defaultApi() {
        List<Parameter> pars = new ArrayList<Parameter>();

        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("token").description("令牌token")
                .modelRef(new ModelRef("String")).parameterType("header")
                .required(true).build(); //header中的Token参数必填，但是这里不能解决部分接口不需要token参数

        pars.add(tokenPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerProperties.getEnable())
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.controller.auth"))
                .paths(PathSelectors.any())
                .build().groupName("需要token验证").globalOperationParameters(pars).ignoredParameterTypes(HttpServletResponse.class, HttpServletRequest.class);
    }
    /**
     * 接口分组,此类接口无需token验证,以扫描的包路径来区分
     * @return
     */
    @Bean(value = "publicApi")
    public Docket publicApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerProperties.getEnable())
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.controller.pub"))
                .paths(PathSelectors.any())
                .build().groupName("无需token验证").ignoredParameterTypes(HttpServletResponse.class, HttpServletRequest.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("基于Swagger3.0.0的接口文档")
                .description("api接口列表")
                .termsOfServiceUrl("http://192.168.0.104:"+port+"/")
                .version("1.0.0")
                .contact(new Contact("zfy", "", ""))
                .build();
    }


}
