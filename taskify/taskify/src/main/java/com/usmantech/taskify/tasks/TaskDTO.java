package com.usmantech.taskify.tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
	@NotBlank
	private String title;
	@NotBlank
    private String description;
	@NotBlank
    private String status;     // TODO, IN_PROGRESS, DONE
    private String priority;   // LOW, MEDIUM, HIGH
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt; 
}
