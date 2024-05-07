package com.heyu.train.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact();
        contact.setEmail("312690503@qq.com");
        contact.setName("huangjian");
        contact.setUrl("http://doc.xiaominfo.com");
        return new OpenAPI()
                // 增加swagger授权请求头配置
                .info(new Info()
                        .title("12306 后台服务API接口文档")
                        .version("1.0")
                        .contact(contact)
                        .description("Knife4j集成springdoc-openapi示例")
                        .termsOfService("http://doc.xiaominfo.com")
                        .license(new License().name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
