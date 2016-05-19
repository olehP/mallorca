package com.mallorca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FacebookController {

	@RequestMapping(value = "facebook/share")
	public String facebookAuth() {
		return "redirect:/connect/facebook";
	}

}
