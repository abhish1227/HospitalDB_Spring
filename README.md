# HospitalDB Spring Boot Backend

Welcome to **HospitalDB**, a beginner-friendly backend project built with **Spring Boot** and **PostgreSQL**. This repository is designed to help new developers get hands-on experience with building real-world RESTful APIs, implementing authentication, managing relational data, and structuring scalable backend systems.

Whether you're just starting out with Spring Boot or looking to deepen your understanding of backend architecture, this project offers a practical learning path. You'll explore key concepts like:

- DTOs and layered architecture
- JWT-based authentication
- Stateless security with Spring Security
- Database integration using JPA and Hibernate
- Error handling and validation
- Modular code organization

By working through this codebase, you'll not only build a hospital management system — you'll also learn how to write clean, maintainable, and production-ready Java backends.

Feel free to fork the repo, experiment, and contribute. Happy coding!

## 🚀Features
- Doctor and Patient management
- Appointment scheduling
- Department assignment with head doctor logic
- RESTful API with DTOs and error handling
- JWT-based authentication with stateless security

## 🛠️Setup Instructions

1. Clone the repository:
   ```bash
    git clone https://github.com/abhish1227/HospitalDB_Spring.git

2. Add your env.properties file inside src/main/resources with DB credentials:
   ```markdown
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    
    jwt.secret=your_jwt_secret_key

3. Ensure application.properties references these values:
    ```
    spring.config.import=optional:classpath:env.properties
    spring.datasource.username=${spring.datasource.username}
    spring.datasource.password=${spring.datasource.password}
   
    jwt.secret=${jwt.secret}
   ```

    Consider placing your ```env.properties``` file in the same path as the ```application.properties```. 

    If not then modify your ```spring.config.import=``` accordingly. 

    In this project, the JWT Token expiration time is set to 10 minutes, but can be modified in the ```hospitalDB/security/AuthUtil.java``` file.


4. Run the application:
    ```markdown
    mvn spring-boot:run

## 🔒Authentication
This project uses JWT (JSON Web Token) for secure, stateless authentication.

### Security Configuration

- Stateless session management (SessionCreationPolicy.STATELESS)

- CSRF protection disabled

- JWT filter integrated before UsernamePasswordAuthenticationFilter

### Public Endpoints:

Accessible without authentication:

    GET /doctors/public/**

    POST /auth/** (e.g., login, signup)

### Protected Endpoints

Requires a valid JWT token in the Authorization header:

    Authorization: Bearer <your_token>

All other endpoints (anyRequest()) are protected by default.

## 🐞Reporting Issues

If you encounter a bug, unexpected behavior, or have a feature request, you can create an issue on GitHub:
### 📝Steps to Create an Issue

- Go to the Issues tab of this repository.

- Click on "New issue".

- Choose a template (if available) or select "Open a blank issue".

- Provide a clear and descriptive title.

- In the description, include:

  - What you expected to happen

  - What actually happened

  - Steps to reproduce the issue (if applicable)

  - Screenshots or logs (if helpful)

- Click "Submit new issue".

Please check existing issues before creating a new one to avoid duplicates.
You can also add labels like bug, enhancement, or question once the issue is created to help triage it faster.
## 🧰Technologies Used
- Java 21
- Spring Boot
- PostgreSQL
- Maven

## 📄License:

This project is licensed under the Apache License 2.0. See [Apache License 2.0](LICENSE) for details.

## 🤝Contributing

Contributions are welcome!  
If you’d like to improve the project, feel free to fork it, open issues, or submit pull requests.

Please follow standard Java/Spring Boot conventions and document your changes clearly.

## 📬Maintainer

**Abhishek Kumar**  
Aspiring Backend Engineer  
Feel free to reach out via [GitHub Issues](https://github.com/abhish1227/HospitalDB_Spring/issues) for bugs or feature requests.
