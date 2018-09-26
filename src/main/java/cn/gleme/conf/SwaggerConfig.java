package cn.gleme.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket getAppApiInfo() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(outApiInfo())
                .groupName("APP及微信接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.gleme.controller.wxapp"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket getAdminApiInfo() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(outApiInfo())
                .groupName("后台管理")
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.gleme.controller.admin"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket getShopApiInfo() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(outApiInfo())
                .groupName("PC商城接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.gleme.controller.shop"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo outApiInfo() {
        return new ApiInfo(
                "云商API文档", // title 标题
                "云商的各项外部接口信息", // description 描述 标题下
                "1.0.0", // version
                "http://shop.jisuyunshang.com/*", // termsOfService
                new Contact("极速技术团队","","jisukj@jisupg.com"), // contact
                "广州极速信息技术有限公司", // licence
                "www.jscdcn.com" // licence url
        );
    }

}