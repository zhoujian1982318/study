package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import listener.UserOnlineListener;

@Controller
@RequestMapping
public class LoginController {
	
	private static Logger logger  = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping(value="/login")
    public String login(@RequestParam(value="username", defaultValue ="default") String username,
    								  @RequestParam(value="password",defaultValue = "no auth") String password,
    								   HttpServletRequest rawReq) {
		HttpSession session = rawReq.getSession(false);
		if(session!=null && session.getAttribute("user_pass")!= null){
			return "success";
		}else{
			if("r2".equals(username) && "r2".equals(password) ){
				session = rawReq.getSession();
				session.setAttribute("user_pass", Boolean.TRUE);
				session.setAttribute("user_key", username);
				session.setAttribute("userlistener", new UserOnlineListener(username));
				return "success";
			}else{
				return "login";
			}
		}	    
    }
	
	@RequestMapping(value="/forcelogout")
    public String forcelogout(HttpSession session) {
		logger.debug("user log in in another place, please login in again");
		session.invalidate();
		return "forcelogout";
    }
}
