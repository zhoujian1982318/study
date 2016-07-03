package filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoFilter implements Filter {
	private static Logger logger  = LoggerFactory.getLogger(DemoFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		 logger.debug("enter the demo filter init method");
		 ServletContext context = filterConfig.getServletContext();
		 logger.debug("context params is as following: ");
		 for(Enumeration<String> e = context.getInitParameterNames(); e.hasMoreElements();){
			String name = e.nextElement();
			String value = context.getInitParameter(name);
			logger.debug("context param name is {},  value is {}",name ,value);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		 logger.debug("enter the demo filter doFilter method");
		 // this method  only support servlet 3.0, in previous version, you can get servlet context
		 // as followng:  HttpServletRequest.getSession().getServletContext();
		 ServletContext ctx = request.getServletContext();
		 Integer numSessions = (Integer) ctx.getAttribute("numSessions");
		 logger.debug("the number of people online is {}", numSessions);
		 chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {
		logger.debug("enter the demo filter destroy method");
		
	}

}
