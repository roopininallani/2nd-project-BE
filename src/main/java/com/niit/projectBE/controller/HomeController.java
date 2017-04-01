package com.niit.projectBE.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	private static final Logger Log = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping("/")
	public String welcome(){
		return "/r_home/index";
	}

}
