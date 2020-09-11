package com.nalidao.v2.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	@Bean
	public Docket apiDoc() {
		return new Docket(DocumentationType.SWAGGER_2)
						.select()
						.apis(RequestHandlerSelectors.basePackage("com.nalidao.v2.product"))
						.paths(PathSelectors.ant("/**"))
						.build()
						.apiInfo(this.apiInfo());
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
					.title("Product Api")
					.description("Api de Produtos")
					.contact(new Contact("PHCorrea", "url", "email@email.com"))
					.version("2.0.0")
					.build();
	}
}
