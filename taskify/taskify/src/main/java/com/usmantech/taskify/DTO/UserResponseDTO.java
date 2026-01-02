package com.usmantech.taskify.DTO;

import java.util.ArrayList;
import java.util.List;

import com.usmantech.taskify.tasks.Task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
	private String id;
	private String userName;
	private String email;
	private String photo;
	private List<Task> tasks=new ArrayList<Task>();
}
