package com.usmantech.taskify.user;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.usmantech.taskify.DTO.UserDTO;
import com.usmantech.taskify.DTO.UserResponseDTO;
import com.usmantech.taskify.exception.FileStorageException;
import com.usmantech.taskify.exception.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	private static final PasswordEncoder PASSWORD_ENCODER=new BCryptPasswordEncoder();
	private static final  String UPLOAD_DIR = "D:/Taskify/images/"; 
    
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public User addNewUser(UserDTO userdto,MultipartFile file) {
		try {
			User user=new User();
			user.setUserName(userdto.getUserName());
			user.setPassword(PASSWORD_ENCODER.encode(userdto.getPassword()));
			user.setEmail(userdto.getEmail());
			user.setRoles(Arrays.asList("USER"));
			if(file!=null &&!file.isEmpty()) {
	            user.setPhoto(storeFile(file));            
			}
			return userRepository.save(user);		
		}  catch (Exception e) {
	        log.error("Failed to add user", e);
	        throw new RuntimeException("Failed to add user");
	    }
	}
	public void addUser(User user) {
		userRepository.save(user);
	}
	
	public User addNewAdmin(UserDTO userdto,MultipartFile file) {
		try {
			User user=new User();
			user.setUserName(userdto.getUserName());
			user.setPassword(PASSWORD_ENCODER.encode(userdto.getPassword()));
			user.setEmail(userdto.getEmail());			user.setRoles(Arrays.asList("USER","ADMIN"));
			if(file!=null &&!file.isEmpty() ) {
	            user.setPhoto(storeFile(file));
			}
			return userRepository.save(user);
		} catch (Exception e) {
			throw new RuntimeException("Failed to add admin");
		}	
	}
	
	 public User getUserByUserName(String userName) { 
			return userRepository.findByUserName(userName)
					.orElseThrow(()->new ResourceNotFoundException("User not found"));
		
	}
	 
	 public List<UserResponseDTO> getAllUsers() {
		 return userRepository.findAll()
				 .stream()
				 .map(user->modelMapper.map(user, UserResponseDTO.class))
				 .collect(Collectors.toList());
	}
	 
	 public void deleteUser(ObjectId id) {
		 if(!userRepository.existsById(id)) {
			 throw new ResourceNotFoundException("User not found with id: "+id);
		 }
			userRepository.deleteById(id);
	}
	 
	 public void UpdateUser(UserDTO user, MultipartFile file) {
				String userName=SecurityContextHolder.getContext().getAuthentication().getName();
				User oldUser=getUserByUserName(userName);
				oldUser.setUserName(user.getUserName());
				  // Update password ONLY if provided
			    if (user.getPassword() != null &&
			        !user.getPassword().isBlank()) {
			        oldUser.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
			    }
				oldUser.setEmail(user.getEmail());
					if(file!=null &&!file.isEmpty() ) {
			            oldUser.setPhoto(storeFile(file));	
					}
					 userRepository.save(oldUser);				
	}
	 
	 
	 private String storeFile(MultipartFile file) {
		    try {
		        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		        Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName);

		        Files.createDirectories(filePath.getParent());
		        Files.write(filePath, file.getBytes());

		        return fileName;
		    } catch (IOException e) {
		        throw new FileStorageException("File upload failed"+e);
		    }
		}	
}
