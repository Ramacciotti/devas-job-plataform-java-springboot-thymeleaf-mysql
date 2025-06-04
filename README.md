# Devas

Project aimed at promoting inclusion and diversity in the technology job market by helping women find job opportunities. Developed with Spring Boot, MySQL, Thymeleaf, and other technologies.

![image](https://github.com/user-attachments/assets/f67c8c26-170e-4179-8ed0-16231fe485b5)

## Table of Contents

- [Description](#description)  
- [Technologies](#technologies)  
- [Prerequisites](#prerequisites)  
- [How to Run](#how-to-run)  
- [Contributions](#contributions)  
- [License](#license)  

## Description

The lack of women in the technology job market is a throbbing pain — it is a problem so relevant that it can no longer be ignored. The goal of the **Devas** project is to promote inclusion and diversity within companies and to help women find job opportunities in tech.

## Technologies

- Java 17+  
- Spring Boot  
- Spring Data JPA  
- Thymeleaf  
- MySQL  
- Maven  

## Prerequisites

- Java 17 or higher installed  
- Maven 3.6 or higher installed  
- MySQL database configured and running  
- Docker and Docker Compose installed (for containerized execution)
- GitHub account (to use CI/CD pipeline with Secrets configured)

## How to Run

Docker allows you to run the entire application including the database in containers, isolating dependencies and environment setup.

1) Create a .env file in the project root (and add it to .gitignore) with:

```SPRING_DATASOURCE_MYSQL_ROOT_PASSWORD=x```
```SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/x?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC=x```
```SPRING_DATASOURCE_USERNAME=x ```
```SPRING_DATASOURCE_PASSWORD=x ```

2) Run Docker Compose to build and start the app and MySQL database:

```docker compose up --build```

3) Access the app at http: http://localhost:8080/home

4) To stop and clean containers: 

```docker compose down -v```

## Contributions

This is a personal project; external contributions are not planned.

## License

This project is private, and all rights are reserved by the author.

No part of this code may be copied, modified, distributed, or used without the express permission of the author.

© 2025 [Mariana Ramacciotti]. All rights reserved.
