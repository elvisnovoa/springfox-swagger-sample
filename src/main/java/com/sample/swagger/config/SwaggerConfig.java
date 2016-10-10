package com.sample.swagger.config;

import static com.google.common.collect.Lists.newArrayList;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import lombok.Data;
import lombok.EqualsAndHashCode;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@PropertySource("classpath:swagger.properties")
public class SwaggerConfig {

	@Bean
	@Profile("swagger-basic")
	Docket basic() {
		// @formatter:off
		return new Docket(DocumentationType.SWAGGER_2)
			.apiInfo(
				new ApiInfo(
						"SpringFox Sample", // Name of your api
						"Quick demo of a Spring Rest web service with Swagger UI thanks to springfox.", // brief description 
						"0.0.1-SNAPSHOT", // api version
						"Not sure where this shows up", // terms of service
						new Contact(
								"Elvis Novoa", // your name
								"https://github.com/elvisnovoa", // your website 
								"someemail@email.com"), // your contact email
						"Apache 2.0 license", // name of your software license
						"http://www.apache.org/licenses/LICENSE-2.0.html")); // url of your license
		// @formatter:on
	}

	@Bean
	@Profile("swagger-security")
	Docket security() {
		// @formatter:off
		return new Docket(DocumentationType.SWAGGER_2)
			.apiInfo(
				new ApiInfo(
						"SpringFox Sample",
						"Quick demo of springfox with OAuth2 Authorization headers.",  
						"0.0.1-SNAPSHOT",
						"Not sure where this shows up", 
						new Contact(
								"Elvis Novoa", 
								"https://github.com/elvisnovoa",  
								"someemail@email.com"), 
						"Apache 2.0 license", 
						"http://www.apache.org/licenses/LICENSE-2.0.html"))
			.globalOperationParameters(
			        newArrayList(new ParameterBuilder()
			            .name("Authorization")
			            .description("OAuth2 Token")
			            .modelRef(new ModelRef("string"))
			            .parameterType("header")
			            .required(true)
			            .build())); 
		// @formatter:on
	}

	@Bean
	@Profile("swagger-custom")
	Docket custom() {
		// @formatter:off
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
				.paths(PathSelectors.ant("/someClass**/*")).build() // filter out Spring's default controllers (error, actuator, etc)
			.apiInfo(apiInfo());
		// @formatter:on
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@ConfigurationProperties("springfox.api.info")
	public CustomApiInfo apiInfo() {
		return new CustomApiInfo();
	}

	@Data
	@EqualsAndHashCode(callSuper = true)
	public static class CustomApiInfo extends ApiInfo {
		private String version;
		private String title;
		private String description;
		private String termsOfServiceUrl;
		private CustomContact contact;
		private String license;
		private String licenseUrl;

		public CustomApiInfo() {
			super(null, null, null, null, new CustomContact(), null, null);
		}
	}

	@Data
	@EqualsAndHashCode(callSuper = true)
	public static class CustomContact extends Contact {
		private String name;
		private String url;
		private String email;

		public CustomContact() {
			super(null, null, null);
		}
	}
}
