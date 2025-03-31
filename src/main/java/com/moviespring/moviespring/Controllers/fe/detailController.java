package com.moviespring.moviespring.Controllers.fe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class detailController {
	@RequestMapping("/detail")
	public String admin() {
		return "fe/detail";
	}
}
