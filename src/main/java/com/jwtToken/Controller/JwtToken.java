package com.jwtToken.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jwtToken.model.JwtRequest;
import com.jwtToken.model.JwtResponse;
import com.jwtToken.services.CustomUserDetailsService;
import com.jwtToken.util.JwtUtil;

@RestController
public class JwtToken {
	
	//Autowired CustomUserDetailsService
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	//Autowired JwtUtil
	@Autowired
	private JwtUtil JwtUtil;
		
	//Autowired AuthenticationManager
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@RequestMapping(value="/token", method= RequestMethod.POST)
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception{
//		System.out.println(jwtRequest);
		
		try {
			this.authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword())
					);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Bad Credentials");
		}
		
		//fine area...
		UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
		String generateToken = this.JwtUtil.generateToken(userDetails);
//		System.out.println(generateToken);
		
		
		//{"token":"value"}
		
		return ResponseEntity.ok(new JwtResponse(generateToken));
		
		
	}

}
