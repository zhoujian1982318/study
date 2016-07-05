package filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthFilter implements Filter {
	
	private static Logger logger  = LoggerFactory.getLogger(AuthFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.debug("enter the auth filter init method");
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;  
        HttpServletResponse res = (HttpServletResponse)response;  
        String requestURI = req.getRequestURI().substring(req.getRequestURI().indexOf("/",1), req.getRequestURI().length());  
        logger.debug("the request url is {}",requestURI);
        
        if(requestURI.contains("images")){
        	//if static resouce, allow it
        	chain.doFilter(req, res);
        }else{
	        if(!"/login".equals(requestURI)){
	            //取得session. 如果没有session则自动会创建一个, 我们用false表示没有取得到session则设置为session为空.  
	            HttpSession session = req.getSession(false);  
	            //如果session中没有任何东西.  
	            if(session == null ||session.getAttribute("user_pass")== null){  
	                res.sendRedirect(req.getContextPath() + "/login");  
	                //返回  
	                return;  
	            }else{
	            	String username = (String)session.getAttribute("user_key");
	            	ServletContext application = session.getServletContext();
	        		Object temp = application.getAttribute("sessionIdMap");
	        		if(temp!=null){
	        			Map<String, String> sessionIdMap = (Map<String, String>)temp;
	        			if(sessionIdMap.containsKey(username)){
	        				String sessionId = sessionIdMap.get(username);
	        				logger.debug("session id in the session id map is {}",sessionId);
	        				String curSessionId = session.getId();
	        				logger.debug("the current session id  {}",curSessionId);
	        				if(!sessionId.equals(curSessionId)){
	        					logger.debug("user has already login in another place, force log out");
	        					req.getRequestDispatcher("forcelogout").forward(req, res);
	        	                //返回  
	        	                return;
	        				}
	        			}
	        		}
	            }
	              
	        }  
	        //session中的内容等于登录页面, 则可以继续访问其他区资源.  
	        chain.doFilter(req, res); 
        }
		
	}

	@Override
	public void destroy() {
		logger.debug("enter the auth filter destroy method");
		
	}

}
