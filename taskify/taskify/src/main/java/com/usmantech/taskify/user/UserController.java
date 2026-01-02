package com.usmantech.taskify.user;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.usmantech.taskify.DTO.UserDTO;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PutMapping
	public ResponseEntity<?> updateUser(@RequestPart UserDTO user,
			@RequestPart(required = false) MultipartFile file) {
		try {
		 userService.UpdateUser(user, file);
		 return new ResponseEntity<>("Updated successfully", HttpStatus.OK);
		 } catch (Exception e) {
		        throw new RuntimeException("Failed to update user data", e);
		    }
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable ObjectId id){
		try {
		userService.deleteUser(id);	
		return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
		} catch (Exception e) {
			throw new RuntimeException("Unable to delete user",e);
		}
	}
	
	
	

}
