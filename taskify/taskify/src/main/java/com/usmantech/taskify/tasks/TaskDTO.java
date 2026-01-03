package com.usmantech.taskify.tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
	@NotBlank
	@NotEmpty
	private String title;
	@NotBlank	
	@NotEmpty
    private String description;
	@NotBlank
	@NotEmpty
    private String status;     // TODO, IN_PROGRESS, DONE
    private String priority;   // LOW, MEDIUM, HIGH
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt; 
}
