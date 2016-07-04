package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import listener.UserOnlineListener;

@Controller
@RequestMapping
public class LoginController {
	
	@RequestMapping(value="/login")
    public String login(@RequestParam(value="username") String username,
    								  @RequestParam(value="password") String password,
    								   HttpServletRequest rawReq) {
		if("r2".equals(username) && "r2".equals(password) ){
			HttpSession session = rawReq.getSession();
			session.setAttribute("user_pass", Boolean.TRUE);
			session.setAttribute("userlistener", new UserOnlineListener(username));
			return "success";
		}else{
			return "login";
		}
		
		
        
    }
}
