package pl.edu.pwr.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurationSupport {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/js/");
	}

	@Override
	@Bean
	public HandlerMapping resourceHandlerMapping() {
		AbstractHandlerMapping handlerMapping = (AbstractHandlerMapping) super.resourceHandlerMapping();
		handlerMapping.setOrder(-1);
		return handlerMapping;
	}

	@Override
	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(converter());
		addDefaultHttpMessageConverters(converters);
	}

	@Bean
	protected MappingJackson2HttpMessageConverter converter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(jsonObjectMapper().getObject());
		return converter;
	}

	@Bean
	public Jackson2ObjectMapperFactoryBean jsonObjectMapper() {
		Jackson2ObjectMapperFactoryBean bean = new Jackson2ObjectMapperFactoryBean();
		bean.setObjectMapper(objectMapper());
		return bean;
	}

	@Bean
	protected ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}
