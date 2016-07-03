package controller;

import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value="/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name, HttpServletRequest rawReq) {
    	HttpSession session = rawReq.getSession();
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
    
}