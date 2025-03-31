package com.moviespring.moviespring.Controllers.fe;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class registerController {
	@RequestMapping({"/register", "/register.html"})
	public String login() {
		return "fe/register";
	}
}
