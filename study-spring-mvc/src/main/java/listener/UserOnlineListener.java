package listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// this listener don't need to configure in the web.xml
public class UserOnlineListener implements HttpSessionBindingListener {
	private static Logger logger  = LoggerFactory.getLogger(UserOnlineListener.class);
	
	private String username;
	
	public UserOnlineListener(String theUsername) {
		username = theUsername;
	}
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		logger.debug("one user log in, the user name is {}", username);
		HttpSession session = event.getSession();  
		ServletContext application = session.getServletContext();
		Object temp = application.getAttribute("sessionIdMap");
		if(temp==null){
			Map<String, String> sessionIdMap = new HashMap<String,String>();
			sessionIdMap.put(username, session.getId());
			application.setAttribute("sessionIdMap", sessionIdMap);
		}else{
			Map<String, String> sessionIdMap = (Map<String, String>)temp;
			sessionIdMap.put(username,session.getId());
			application.setAttribute("sessionIdMap", sessionIdMap);
		}
		
		
		


	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		logger.debug("one user log out, the user name is {}", username);
		HttpSession session = event.getSession();  
		ServletContext application = session.getServletContext();
		Object temp = application.getAttribute("sessionIdMap");
		if(temp!=null){
			Map<String, String> sessionIdMap = (Map<String, String>)temp;
			if(sessionIdMap.containsKey(username)){
				sessionIdMap.remove(username);
			}
		}
	}

}
