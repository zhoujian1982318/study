package listener;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// this listener don't need to configure in the web.xml
public class UserOnlineListener implements HttpSessionBindingListener {
	private static Logger logger  = LoggerFactory.getLogger(UserOnlineListener.class);
	
	private String userId;
	
	public UserOnlineListener(String theUserId) {
		userId = theUserId;
	}
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		logger.debug("one user log in, the user id is {}", userId);
		
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		logger.debug("one user log out, the user id is {}", userId);
	}

}
