package initializer;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;

import filter.DemoFilter;

public class DyFilterInitializer implements WebApplicationInitializer {
	private static Logger logger  = LoggerFactory.getLogger(DyFilterInitializer.class);
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		logger.debug("enter the DyFilterInitializer onStartup method");
		
		
		logger.debug("add demo filter");
		DemoFilter demoFilter = new DemoFilter();
		FilterRegistration.Dynamic filterRegistration = servletContext.addFilter("demo", demoFilter);
		filterRegistration.addMappingForUrlPatterns(
				EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE), true, "/*");
		
		
//		logger.debug("add test filter");
//		TestFilter testFilter = new TestFilter();
//		FilterRegistration.Dynamic testfilterRegistration = servletContext.addFilter("test", testFilter);
//		testfilterRegistration.addMappingForUrlPatterns(
//				EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE), false, "/*");
		
	}

}
