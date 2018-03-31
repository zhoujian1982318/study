package listener;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestContextListener  implements ServletContextListener{
	
	private static Logger logger  = LoggerFactory.getLogger(TestContextListener.class);
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.debug("enter the test context listener init method");
		
		ServletContext context = sce.getServletContext();
		logger.debug("context params is as following: ");
		for(Enumeration<String> e = context.getInitParameterNames(); e.hasMoreElements();){
			String name = e.nextElement();
			String value = context.getInitParameter(name);
			logger.debug("context param name is {},  value is {}",name ,value);
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.debug("enter the test context listener destroyed method");
		
	}

}
