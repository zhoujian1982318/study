package listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionCtListener implements HttpSessionListener {
	private static Logger logger  = LoggerFactory.getLogger(SessionCtListener.class);
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		logger.debug("enter the session counter listener session created method");
		ServletContext ctx = se.getSession().getServletContext();
		Integer numSessions = (Integer) ctx.getAttribute("numSessions");
		if (numSessions == null) {
			numSessions = new Integer(1);
		} else {
			int count = numSessions.intValue();
			numSessions = new Integer(count + 1);
		}
		ctx.setAttribute("numSessions", numSessions);
		logger.debug("the number of session is {}",numSessions);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		logger.debug("enter the session counter listener session destroyed method");
		ServletContext ctx = se.getSession().getServletContext();
		Integer numSessions = (Integer) ctx.getAttribute("numSessions");
		if (numSessions == null) {
			numSessions = new Integer(0);
		} else {
			int count = numSessions.intValue();
			numSessions = new Integer(count - 1);
		}
		ctx.setAttribute("numSessions", numSessions);
		logger.debug("the number of session is {}",numSessions);
	}

}
