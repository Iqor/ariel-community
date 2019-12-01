package com.igor;

import com.igor.configuration.SecurityConfig;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ArielApplication.class);
	}


	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {SecurityConfig.class};
	}

}
