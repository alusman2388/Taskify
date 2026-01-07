package com.usmantech.taskify.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalException {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());	
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<String> handleBadRequest(BadRequestException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());	
	}
	
	@ExceptionHandler(FileStorageException.class)
	public ResponseEntity<String> handleFileStorage(FileStorageException ex) {
		log.error("File upload error",ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload error");
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> HandleGeneric(Exception ex) {
		log.error("Unxcepted error",ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Some thing went wrong");
	}
}
