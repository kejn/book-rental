package pl.edu.pwr.controller;

import java.util.HashSet;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.edu.pwr.service.UserService;
import pl.edu.pwr.to.UserTo;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/emailConfirm", method = RequestMethod.POST)
	public String rent(Map<String, Object> params) {
		
		return null;
	}
	
	@RequestMapping(value = "/signIn")
	public String signIn(Map<String, Object> params) {
		UserTo user = new UserTo(null, "", "", "", new HashSet<>());
		params.put("user", user);
		return "signIn";
	}
	
}
