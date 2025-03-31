package com.moviespring.moviespring.Controllers.fe;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class lobbyController {
	@RequestMapping("/lobby")
	public String login() {
		return "fe/home";
	}

}