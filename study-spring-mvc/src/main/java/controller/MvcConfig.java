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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * @author Rob Winch
 */
@Configuration
//Adding this annotation to an @Configuration class imports the Spring MVC configuration from WebMvcConfigurationSupport, e.g.: 
//To customize the imported configuration, implement the interface WebMvcConfigurer or more likely extend the empty method base class WebMvcConfigurerAdapter and override individual methods
//If WebMvcConfigurer does not expose some advanced setting that needs to be configured, consider removing the @EnableWebMvc annotation and extending directly from WebMvcConfigurationSupport or DelegatingWebMvcConfiguration
//@EnableWebMvc  //removing the @EnableWebMvc annotation, extend directly from WebMvcConfigurationSupport
@ComponentScan
public class MvcConfig extends WebMvcConfigurationSupport {
	private static Logger logger  = LoggerFactory.getLogger(MvcConfig.class);
	
	@Bean  
    public ViewResolver viewResolver() {  
        logger.debug(" configure viewResolver bean");  
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver(); 
        viewResolver.setOrder(2);
        viewResolver.setPrefix("/WEB-INF/jsp/");  
        viewResolver.setSuffix(".jsp");  
        return viewResolver;  
    }
	
	@Bean  
    public ViewResolver cNviewResolver() {  
        //logger.debug(" configure viewResolver bean");  
		ContentNegotiatingViewResolver cNviewResolver = new ContentNegotiatingViewResolver(); 
		cNviewResolver.setOrder(1);
		cNviewResolver.setFavorParameter(false);
		cNviewResolver.setFavorPathExtension(true);
		cNviewResolver.setDefaultContentType(MediaType.TEXT_HTML);
		Map<String,String> mediaTypes = new HashMap<String,String>();
		mediaTypes.put("json", "application/json");
		mediaTypes.put("xml", "application/xml");
		cNviewResolver.setMediaTypes(mediaTypes);
		List<View> defaultViews = new ArrayList<View> ();
		defaultViews.add(new MappingJackson2JsonView());
		cNviewResolver.setDefaultViews(defaultViews);
        return cNviewResolver;  
    }
	
	@Override  
	public void addResourceHandlers(ResourceHandlerRegistry registry) {  
	    logger.debug("configure resource handlers");  
	    registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/");  
	}
}
