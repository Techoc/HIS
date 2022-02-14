package cn.techoc.his.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author techoc
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.OAS_30)
            .apiInfo(apiInfo())
            .enable(true)
            .groupName("ZRJ")
            .select()
            .apis(RequestHandlerSelectors.basePackage("cn.techoc.his.controller"))
            .paths(PathSelectors.ant("/**"))
            .build();
    }


    @SuppressWarnings("all")
    public ApiInfo apiInfo(){
        return new ApiInfo(
            "zrj's api",
            "redis project",
            "v1.0",
            "techoc@foxmail.com", //开发者团队的邮箱
            "techoc", //开发者团队的名称
            "Apache 2.0",  //许可证
            "http://www.apache.org/licenses/LICENSE-2.0" //许可证链接
        );
    }
}
