package todo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ModelAndView hello() {
        LOGGER.info("Start working TestController hello()");
        return new ModelAndView("test", "test", "Test working controller Spring MVC");
    }

    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public String startPageRedirect(Model ui) {
        LOGGER.info("Start working TestController startPageRedirect()");
        ui.addAttribute("redirect", "from Redirect");
        return "redirect:/test";
    }
}
