📝 Taskify – Task Management Application

Taskify is a backend RESTful task management application built using Spring Boot and MongoDB.
It allows users to register, authenticate, and manage tasks securely with JWT-based authentication.

🚀 Features
🔐 Authentication & Authorization

User registration (Sign Up)

Secure login using JWT (JSON Web Token)

Role-based access (USER)

Password encryption using BCrypt

👤 User Management

Create and manage user profiles

Upload and update user profile photos

Fetch users using DTO-based responses

Unique username enforcement

✅ Task Management

Create tasks

Update tasks

Delete tasks

Get task by ID

Associate tasks with users

Track task status and priority

Auto-set createdAt and updatedAt

🧱 Clean Architecture

Layered architecture (Controller → Service → Repository)

DTOs for request and response separation

Centralized exception handling

Transaction management

ModelMapper for entity–DTO mapping
