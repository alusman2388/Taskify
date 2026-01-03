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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/task")
@Tag(name = "Task API's")
public class TaskController {
	@Autowired
	private TaskService taskService;
	
	@PostMapping
	@Operation(summary = "Add new Task")
	public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO) {
		String userName=SecurityContextHolder.getContext().getAuthentication().getName();
		return new ResponseEntity<>(taskService.createTask(taskDTO, userName), HttpStatus.CREATED);
	}
	
	@GetMapping("{id}")
	@Operation(summary = "Get Task")
	public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable String id){
		ObjectId objId=new ObjectId(id);
		return new ResponseEntity<>(taskService.findTaskById(objId), HttpStatus.OK);
	}
	
	@PutMapping("{id}")
	@Operation(summary = "Update Task")
	public ResponseEntity<?> updateTask(@RequestBody TaskDTO dto, @PathVariable String id){
		ObjectId objId=new ObjectId(id);
		return new ResponseEntity<>(taskService.updateTask(dto, objId),HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	@Operation(summary = "Delete Task")
	public ResponseEntity<?> deleteTask(@PathVariable String id) {
		ObjectId objId=new ObjectId(id);
		String userName=SecurityContextHolder.getContext().getAuthentication().getName();
		taskService.deleteTask(objId, userName);
		return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
	}
}
