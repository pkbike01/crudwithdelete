package com.jwtToken.MyConfig;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwtToken.services.CustomUserDetailsService;
import com.jwtToken.util.JwtUtil;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//get jwt
		//it starts with Bearer
		//validate
		
		String requestTokenHeader = request.getHeader("Authorization");
		String username=null;
		String jwtToken=null;
		
		//we are checking format
		if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken=requestTokenHeader.substring(7);
			
			try {
				
				username = this.jwtUtil.getUsernameFromToken(jwtToken);
				
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
//				SecurityContextHolder.getContext().getAuthentication()
				UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
				
				//security check
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
//						new UsernamePasswordAuthenticationToken(userDetails, credential->null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
				
				
			}else {
				
				System.out.println("Token is not valid!!!");
			}
			
			
			
		}
		
		filterChain.doFilter(request, response);
			
		}
		
}
