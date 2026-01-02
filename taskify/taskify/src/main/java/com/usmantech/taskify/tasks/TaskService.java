package com.usmantech.taskify.tasks;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usmantech.taskify.exception.ResourceNotFoundException;
import com.usmantech.taskify.user.User;
import com.usmantech.taskify.user.UserRepository;
import com.usmantech.taskify.user.UserService;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Transactional
	public Task createTask(TaskDTO taskDTO,String userName) {
		try {
			User user=userRepository.findByUserName(userName)
					.orElseThrow(() -> new RuntimeException("User not found"));;
			Task task=modelMapper.map(taskDTO,Task.class);
			task.setCreatedAt(LocalDateTime.now());
			task.setUpdatedAt(LocalDateTime.now());
			Task savedTask=taskRepository.save(task);
			user.getTasks().add(savedTask);
			userService.addUser(user);
			return savedTask;
		} catch (Exception e) {
			throw new RuntimeException("Failed to create task");
		}
	}
	public TaskResponseDTO updateTask(TaskDTO taskDTO,ObjectId id) {
		
		Task task= taskRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("task not found"));
		task.setTitle(taskDTO.getTitle());
		task.setDescription(taskDTO.getDescription());
		task.setStatus(taskDTO.getStatus());
		task.setPriority(taskDTO.getPriority());
		task.setDueDate(taskDTO.getDueDate());
		task.setUpdatedAt(LocalDateTime.now());
		return modelMapper.map(taskRepository.save(task),TaskResponseDTO.class);
		
	}
	public TaskResponseDTO findTaskById(ObjectId id) {
		Task task= taskRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Task not Found"));
		return modelMapper.map(task, TaskResponseDTO.class);	
	}
	@Transactional
	public void deleteTask(ObjectId id,String userName) {
				User user=userRepository.findByUserName(userName)
						.orElseThrow(() -> new RuntimeException("User not found"));;
				 taskRepository.findById(id)
						.orElseThrow(()->new ResourceNotFoundException("Task not Found"));
				user.getTasks().removeIf(i->i.getId().equals(id));
				userRepository.save(user);
				taskRepository.deleteById(id);
	}
	

}
