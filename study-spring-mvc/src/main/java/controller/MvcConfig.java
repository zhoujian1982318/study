/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author Rob Winch
 */
@Configuration
@EnableWebMvc
@ComponentScan
public class MvcConfig extends WebMvcConfigurationSupport {
	private static Logger logger  = LoggerFactory.getLogger(MvcConfig.class);
	
	@Bean  
    public ViewResolver viewResolver() {  
        logger.debug(" configure viewResolver bean");  
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();  
        viewResolver.setPrefix("/WEB-INF/jsp/");  
        viewResolver.setSuffix(".jsp");  
        return viewResolver;  
    }
	
	  @Override  
	  protected void addResourceHandlers(ResourceHandlerRegistry registry) {  
	      logger.debug("configure resource handlers");  
	      registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/");  
	 }
}
