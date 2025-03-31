package com.moviespring.moviespring.Controllers.fe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller

public class feController {
	@RequestMapping({"/index", "/index.html"})
	public String admin() {
		return "fe/index";
	}
}
