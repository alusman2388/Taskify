package com.usmantech.taskify.user;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.usmantech.taskify.tasks.Task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	private ObjectId id;
	@Indexed(unique = true)
	@NonNull
	@NotEmpty
	private String userName;
	@NonNull
	private String password;
	private String email;
	private String photo;
	@DBRef
	private List<Task> tasks=new ArrayList<Task>();
	private List<String> roles;
}
