package com.usmantech.taskify.user;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usmantech.taskify.DTO.UserDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user")
@Tag(name = "User API's")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PutMapping
	(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Update user")
	public ResponseEntity<?> updateUser(@RequestPart("user") String userJson,
			@RequestPart(value = "photo",required = false) MultipartFile file) {
		try {
			UserDTO user=new ObjectMapper().readValue(userJson,UserDTO.class);
			userService.UpdateUser(user, file);
			return new ResponseEntity<>("Updated successfully", HttpStatus.OK);
		 } catch (Exception e) {
		        throw new RuntimeException("Failed to update user data", e);
		 }
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete user")
	public ResponseEntity<?> deleteUser(@PathVariable String id){
		try {
		ObjectId objId= new ObjectId(id);
		userService.deleteUser(objId);	
		return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
		} catch (Exception e) {
			throw new RuntimeException("Unable to delete user",e);
		}
	}
}
