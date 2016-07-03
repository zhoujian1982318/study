package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderContextListener  implements ServletContextListener {
	
	private static Logger logger  = LoggerFactory.getLogger(OrderContextListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.debug("enter the order context listener init method");
		ServletContext ctx = sce.getServletContext();
		//add session attr listener
		SessionAttrListener sal = new SessionAttrListener();
		ctx.addListener(sal);
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.debug("enter the order context listener destroyed method");
		
	}

}
