package com.usmantech.taskify.tasks;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {
	@Autowired
	private TaskService taskService;
	
	@PostMapping
	public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO) {
		String userName=SecurityContextHolder.getContext().getAuthentication().getName();
		Task task=taskService.createTask(taskDTO, userName);
		return new ResponseEntity<>(task, HttpStatus.CREATED);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable ObjectId id){
		return new ResponseEntity<>(taskService.findTaskById(id), HttpStatus.OK);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> updateTask(@RequestBody TaskDTO dto, @PathVariable ObjectId id){
		return new ResponseEntity<>(taskService.updateTask(dto, id),HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteTask(@PathVariable ObjectId id) {
		String userName=SecurityContextHolder.getContext().getAuthentication().getName();
		taskService.deleteTask(id, userName);
		return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
	}

}
