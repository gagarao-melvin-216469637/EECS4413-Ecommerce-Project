package org.o7planning.sbshoppingcart.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// class used to declare bean methods that may be processed in the Spring container
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	@Bean
	public MessageSource messageSource() {		
		// implementation used to access resource bundles in validation.properties
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		
		// load validation.properties file
		messageSource.setBasename("classpath:validation");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
 
}