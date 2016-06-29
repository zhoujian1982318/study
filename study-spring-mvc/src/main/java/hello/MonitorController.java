package hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/monitor/clients")
public class MonitorController {
	
	@RequestMapping(value="/greeting")
    public @ResponseBody Greeting greeting(@RequestParam(value="name", defaultValue="World") String theName) {
		String name = theName;
		Greeting greeting = new Greeting();
        return greeting;
    }
}
