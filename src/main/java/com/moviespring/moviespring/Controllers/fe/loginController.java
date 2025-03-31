package com.moviespring.moviespring.Controllers.fe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class loginController {
	@RequestMapping({"/login", "/login.html"})
	public String login() {
		return "fe/login";
	}
	
}
