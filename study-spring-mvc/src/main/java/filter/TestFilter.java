package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFilter implements Filter {
	private static Logger logger  = LoggerFactory.getLogger(TestFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.debug("enter the test filter init method");
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.debug("enter the test filter doFilter method");
		chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {
		logger.debug("enter the test filter destroy method");
		
	}

}
