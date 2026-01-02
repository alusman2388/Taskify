package com.usmantech.taskify.tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Document(collection = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
	@Id
	private ObjectId id;
	@NonNull
	private String title;
	@NonNull
    private String description;
	@NonNull
    private String status;     // TODO, IN_PROGRESS, DONE
    private String priority;   // LOW, MEDIUM, HIGH
    private LocalDate dueDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt; 
	

}
