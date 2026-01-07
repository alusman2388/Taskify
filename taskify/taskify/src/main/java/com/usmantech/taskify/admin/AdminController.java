package com.usmantech.taskify.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usmantech.taskify.DTO.UserDTO;
import com.usmantech.taskify.DTO.UserResponseDTO;
import com.usmantech.taskify.user.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin API's")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping(value = "create-admin",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Add new Admin")
	public ResponseEntity<?> addNewAdmin(@RequestPart("user") String userJson,
			@RequestPart(value= "photo",required = false) MultipartFile photo){
		try {
			UserDTO user=new ObjectMapper().readValue(userJson, UserDTO.class);
			return new ResponseEntity<>(userService.addNewAdmin(user, photo),HttpStatus.CREATED);
			
		} catch (Exception e) {
			return new ResponseEntity<>("User already exist",HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/get-all-users")
	@Operation(summary = "Get all users")
	public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
			return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
		
	}

}
