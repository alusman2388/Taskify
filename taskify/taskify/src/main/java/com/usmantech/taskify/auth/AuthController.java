package com.usmantech.taskify.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usmantech.taskify.DTO.LoginDTO;
import com.usmantech.taskify.DTO.UserDTO;
import com.usmantech.taskify.user.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Slf4j
@Tag(name = "Auth API's")
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
	@Operation(summary = "Checking health of API server is working or not")
	public String healthCheck() {
		return "Working fine ---> Ok";
	}
	
	@PostMapping(value = "/sign-up",
		    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Create new user")
	public ResponseEntity<?> addNewUser(@RequestPart("user") String userJson,
	        @RequestPart(required = false) MultipartFile photo) {
		try {
	        UserDTO user = new ObjectMapper().readValue(userJson, UserDTO.class);
		    return new ResponseEntity<>(userService.addNewUser(user, photo), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("User already exist",HttpStatus.BAD_REQUEST);
		}
	    
	}
	@PostMapping("/log-in")
	@Operation(summary = "LogIn")
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
