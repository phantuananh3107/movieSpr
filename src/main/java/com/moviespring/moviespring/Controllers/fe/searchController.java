package com.moviespring.moviespring.Controllers.fe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class searchController {
	@RequestMapping("/search")
	public String admin() {
		return "fe/search";
	}
	
}
