## Technologies 
- Spring Boot (v2.7.4)
- Spring Data JPA
- Spring Validation
- Spring Security + JWT Token
- PostgreSQL
- MySQL
- Mapstruct
- Lombok
- Swagger

## Run the Application

First make sure that the database is up. 
If using Docker, you can use ```docker compose up -d``` command.

Navigate to the root of the project. For building the project using command line, run below command :

``` mvn clean install```

Run service in command line. Navigate to *target* directory. 

``` java -jar docutool.jar ```
