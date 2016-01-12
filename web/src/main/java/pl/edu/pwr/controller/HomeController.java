package pl.edu.pwr.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(HttpSession session) {
		if(session.getAttribute("book") != null) {
			session.removeAttribute("book");
		}
		return "home";
	}
}
