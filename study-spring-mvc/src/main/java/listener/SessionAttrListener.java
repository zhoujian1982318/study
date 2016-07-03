package listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//this listener  need to configure in the web.xml
public class SessionAttrListener implements HttpSessionAttributeListener {
	
	private static Logger logger  = LoggerFactory.getLogger(SessionAttrListener.class);
	
	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		logger.debug("enter the session attr listener attributeAdded method");
		
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		logger.debug("enter the session attr listener attributeRemoved method");
		
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		logger.debug("enter the session attr listener attributeReplaced method");
		
	}

}
