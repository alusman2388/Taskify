package com.usmantech.taskify.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.usmantech.taskify.exception.ResourceNotFoundException;
import com.usmantech.taskify.user.User;
import com.usmantech.taskify.user.UserRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepository.findByUserName(username).orElseThrow(() ->
        new ResourceNotFoundException("User not found"));;
		if(user!=null) {
			UserDetails userDetails=org.springframework.security.core.userdetails.User.builder()
			.username(user.getUserName())
			.password(user.getPassword())
			.roles(user.getRoles().toArray(new String[0]))
			.build();
			return userDetails;
		}
		throw new UsernameNotFoundException("User not found with name: "+username);
		
		
	}
	

}
