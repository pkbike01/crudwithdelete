package com.jwtToken.services;

import java.util.ArrayList;




import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		//Basically, it returns userDetails
		if(userName.equals("pankaj")) {
			return new User("pankaj","pankaj123", new ArrayList<>());//new ArrayList<>()--> this is authority of user
		}else {
			throw new UsernameNotFoundException("user not found exception!!!");
		}
		
		
	}

}
