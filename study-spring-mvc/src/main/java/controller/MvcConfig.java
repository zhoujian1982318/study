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
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import views.UserExcelView;
import views.UserPdfView;

/**
 * @author Rob Winch
 */
@Configuration
//Adding this annotation to an @Configuration class imports the Spring MVC configuration from WebMvcConfigurationSupport, e.g.: 
//To customize the imported configuration, implement the interface WebMvcConfigurer or more likely extend the empty method base class WebMvcConfigurerAdapter and override individual methods
//If WebMvcConfigurer does not expose some advanced setting that needs to be configured, consider removing the @EnableWebMvc annotation and extending directly from WebMvcConfigurationSupport or DelegatingWebMvcConfiguration
//@EnableWebMvc  //removing the @EnableWebMvc annotation, extend directly from WebMvcConfigurationSupport
@ComponentScan
public class MvcConfig  extends WebMvcConfigurationSupport { // 
	private static Logger logger  = LoggerFactory.getLogger(MvcConfig.class);
	
	@Bean  
    public ViewResolver viewResolver() {  
        logger.debug(" configure viewResolver bean");  
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver(); 
        viewResolver.setOrder(2);
        viewResolver.setPrefix("/WEB-INF/jsp/");  
        viewResolver.setSuffix(".html");  
        return viewResolver;  
    }
	
	@Bean  
    public ViewResolver cNviewResolver() {  
        //logger.debug(" configure viewResolver bean");  
		ContentNegotiatingViewResolver cNviewResolver = new ContentNegotiatingViewResolver(); 
		cNviewResolver.setOrder(1);
		cNviewResolver.setContentNegotiationManager(mvcContentNegotiationManager());
//		cNviewResolver.setFavorParameter(false);
//		cNviewResolver.setFavorPathExtension(true);
//		cNviewResolver.setDefaultContentType(MediaType.TEXT_HTML);
//		Map<String,String> mediaTypes = new HashMap<String,String>();
//		mediaTypes.put("json", "application/json");
//		mediaTypes.put("xml", "application/xml");
//		cNviewResolver.setMediaTypes(mediaTypes);
		List<View> defaultViews = new ArrayList<View> ();
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		defaultViews.add(view);
		
		//default IE will save the json to file,  
		//if set text plain value, it will show text directlly on IE
		//if you set ?format=text;
		MappingJackson2JsonView viewText = new MappingJackson2JsonView();
		viewText.setContentType(MediaType.TEXT_PLAIN_VALUE);
		defaultViews.add(viewText);
		
		
		//if we want to map different excel view, we can use spring XmlViewResolver
		// and define in the xml  file
		UserExcelView viewExl = new UserExcelView();
		//viewExl.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
		defaultViews.add(viewExl);
		
		UserPdfView viewPdf = new UserPdfView();
		defaultViews.add(viewPdf);
		
		cNviewResolver.setDefaultViews(defaultViews);
        return cNviewResolver;  
    }
	
//	@Override  
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {  
//	    logger.debug("configure resource handlers");  
//	    registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/");  
//	}
	
	@Bean(name={"/old", "/old.json"} )
    public OldController oldController() {  
		OldController oldController = new OldController();
        return oldController;  
    }
	
	// define message convert bean  is useless on this section,  
	// you can over ride  getMessageConverters method on WebMvcConfigurationSupport, 
	// and set message converts  to RequestMappingHandlerAdapter .
//	@Bean
//	public StringHttpMessageConverter messageConverter(){
//		StringHttpMessageConverter stringMessageconverter = new StringHttpMessageConverter();
//		List<MediaType> supportMediaTypes = new ArrayList<MediaType>();
//		supportMediaTypes.add(MediaType.TEXT_PLAIN);
//		stringMessageconverter.setSupportedMediaTypes(supportMediaTypes);
//		return stringMessageconverter;
//	}
	
//	@Bean
//	public MappingJackson2HttpMessageConverter jsonConverter(){
//		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
//		List<MediaType> supportMediaTypes = new ArrayList<MediaType>();
//		supportMediaTypes.add(MediaType.APPLICATION_JSON);
//		jsonConverter.setSupportedMediaTypes(supportMediaTypes);
//		return jsonConverter;
//	}
	
//	@Bean
//	public HttpMessageConverter<?>[] converters(){
//		HttpMessageConverter<?>[] converters = new  HttpMessageConverter<?> [1];
//		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
//		List<MediaType> supportMediaTypes = new ArrayList<MediaType>();
//		supportMediaTypes.add(MediaType.APPLICATION_JSON);
//		jsonConverter.setSupportedMediaTypes(supportMediaTypes);
//		converters[0] = jsonConverter;
//		return converters;
//	}

	@Override
	protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		// TODO Auto-generated method stub
		configurer.favorPathExtension(false);
		configurer.favorParameter(true);
		
		//define mediaTypes is useless, because the favorPathExtension is false;
		//but can support parameter like ?format=json or ?fomat=j
		Map<String, MediaType> mediaTypes = new HashMap<String, MediaType>();
		mediaTypes.put("json",MediaType.APPLICATION_JSON);
		mediaTypes.put("j",MediaType.APPLICATION_JSON);
		mediaTypes.put("text",MediaType.TEXT_PLAIN);
		mediaTypes.put("xls",MediaType.parseMediaType("application/vnd.ms-excel"));
		mediaTypes.put("pdf",MediaType.parseMediaType("application/pdf"));
		configurer.mediaTypes(mediaTypes);
	}
	
//	@Bean
//	public AnnotationMethodHandlerAdapter requestMappingHandlerAdapter() {
//		AnnotationMethodHandlerAdapter anotationHandlerAdapter  = new  AnnotationMethodHandlerAdapter();
//		HttpMessageConverter<?>[] converters = new  HttpMessageConverter<?> [1];
//		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
//		List<MediaType> supportMediaTypes = new ArrayList<MediaType>();
//		supportMediaTypes.add(MediaType.APPLICATION_JSON);
//		jsonConverter.setSupportedMediaTypes(supportMediaTypes);
//		converters[0] = jsonConverter;
//		anotationHandlerAdapter.setMessageConverters(converters);
//		return anotationHandlerAdapter;
//	}
	
}
