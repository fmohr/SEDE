package de.upb.sede.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {"de.upb.sede.gateway", "de.upb.sede.gateway.api", "de.upb.sede.gateway.configuration"})
public class GatewaySpringServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewaySpringServerApplication.class, args);
	}

}
