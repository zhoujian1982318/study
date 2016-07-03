package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import listener.UserOnlineListener;

@Controller
@RequestMapping
public class LoginController {
	
	@RequestMapping(value="/login")
    public @ResponseBody String login(@RequestParam(value="userId", defaultValue="1") String userId, HttpServletRequest rawReq) {
		String uId = userId;
		HttpSession session = rawReq.getSession();
		session.setAttribute("user_id", uId);
		session.setAttribute("userlistener", new UserOnlineListener(uId));
        return "login successfully";
    }
}
