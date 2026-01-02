package com.usmantech.taskify.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.usmantech.taskify.DTO.LoginDTO;
import com.usmantech.taskify.DTO.UserDTO;
import com.usmantech.taskify.user.User;
import com.usmantech.taskify.user.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private UserDetailsServiceImp userDetailsServiceImp;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@GetMapping("/health-ckeck")
	public String healthCheck() {
		return "Working fine ---> Ok";
	}
	
	@PostMapping("/sign-up")
	public ResponseEntity<?> addNewUser(@RequestPart("user") UserDTO user,
	        @RequestPart(required = false) MultipartFile photo) {
		try {
			User entity = userService.addNewUser(user, photo);
		    return new ResponseEntity<>(entity, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("User already exist",HttpStatus.BAD_REQUEST);
		}
	    
	}
	@PostMapping("/log-in")
	public ResponseEntity<String> logIn(@RequestBody LoginDTO user) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
			UserDetails userDetails=userDetailsServiceImp.loadUserByUsername(user.getUserName());
			String jwt=jwtUtil.generateToken(userDetails.getUsername());
			return new ResponseEntity<>(jwt,HttpStatus.OK);
			
		} catch (Exception e) {
			log.error("Exception occured while creating Authenitcation tokem"+e);
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
}
