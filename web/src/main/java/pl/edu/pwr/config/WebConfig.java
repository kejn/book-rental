package pl.edu.pwr.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = { "pl.edu.pwr" })
@Import({ MvcConfig.class, ThymeleafConfig.class, MailConfig.class })
public class WebConfig {
	
	
	
	
}
