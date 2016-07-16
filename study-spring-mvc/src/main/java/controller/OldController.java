/**
 * 
 */
package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @author Administrator
 *
 */
public class OldController implements Controller {

	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		 ModelAndView mv = new ModelAndView();  
	     //添加模型数据 可以是任意的POJO对象  
	     mv.addObject("message", "Hello World!");  
	     //设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面  
	     mv.setViewName("old");
	     return mv;
	}

}
