package com.usmantech.taskify.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.usmantech.taskify.DTO.UserDTO;
import com.usmantech.taskify.DTO.UserResponseDTO;
import com.usmantech.taskify.user.User;
import com.usmantech.taskify.user.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<?> addNewAdmin(@RequestPart("user") UserDTO user,
			@RequestPart(value= "photo",required = false) MultipartFile photo){
		try {
			User entity = userService.addNewAdmin(user, photo);
			return new ResponseEntity<>(entity,HttpStatus.CREATED);
			
		} catch (Exception e) {
			return new ResponseEntity<>("User already exist",HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/get-all-users")
	public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
		try {
			List<UserResponseDTO> user=userService.getAllUsers();
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			throw new RuntimeException("No user found");		
			}
	}

}
