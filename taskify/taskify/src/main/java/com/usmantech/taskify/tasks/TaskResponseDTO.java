package com.usmantech.taskify.tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDTO {
	private String id;
	private String title;
    private String description;
    private String status;     // TODO, IN_PROGRESS, DONE
    private String priority;   // LOW, MEDIUM, HIGH
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt; 
}
