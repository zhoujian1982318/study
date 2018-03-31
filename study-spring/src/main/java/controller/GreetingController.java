package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//@RestController
@Controller(value="grCntroller")
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value="/greeting", produces= {MediaType.ALL_VALUE} )//, produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody  Greeting greeting(@RequestParam(value="name", defaultValue="World") String name, HttpServletRequest rawReq) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
    
    @RequestMapping(value="/user")
    public  String greeting(ModelMap model) {
    	Greeting gr = new Greeting(counter.incrementAndGet(),"test");
    	List<String> list = new ArrayList<String>();
    	list.add("first");
    	list.add("second");
    	list.add("three");
    	model.addAttribute("list", list);
    	model.addAttribute("test", gr);
    	model.addAttribute("jason", "zhou");
        return "user";
    }
    
}