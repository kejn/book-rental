package pl.edu.pwr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration
public class ThymeleafConfig {
	
	private static final String prefix = "/WEB-INF/templates/";
	private static final String suffix = ".html";
	private static final String templateMode = "HTML5";
	private static final String characterEncoding = "UTF-8";
	private static final boolean cacheable = false;
	private static final boolean cache = false;
	
	@Bean
	public ThymeleafViewResolver viewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setCache(cache);
		resolver.setCharacterEncoding(characterEncoding);
		return resolver;
	}
	
	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setTemplateResolver(templateResolver());
		return engine;
	}

	@Bean
	public ServletContextTemplateResolver templateResolver() {
		ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
		resolver.setPrefix(prefix);
		resolver.setSuffix(suffix);
		resolver.setTemplateMode(templateMode);
		resolver.setCharacterEncoding(characterEncoding);
		resolver.setCacheable(cacheable);
		return resolver;
	}
	
}
