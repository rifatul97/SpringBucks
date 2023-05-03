package com.rifatul.SpringBucks;

import com.rifatul.SpringBucks.security.RsaKeyProperties;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties(RsaKeyProperties.class)
public class SpringBucksApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(SpringBucksApplication.class);
//		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
	}

}
