package com.jwtToken.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class Home {
	
	@RequestMapping("/welcome")
	public String welcomePage(){
		String text = "this is a private page...";
		text+="this page is not allowed to unauthenticated users..";
		return text;
	}
	
	
	
	@RequestMapping("/user")
	public String getUsers(){
		
		return "{\"name\":\"pankaj\"}";
	}

}
