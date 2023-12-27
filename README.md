## This service is used for collecting students 

####  part (5) of microservices
* (1) [Eureka Server](https://github.com/domKul/EurekaServer_microservice)
* (2) [Gateway](https://github.com/domKul/Gateway_microservice)
* (3) [Notification service](https://github.com/domKul/Notification_microservice)
* (4) [Course service](https://github.com/domKul/CourseService_microservice)
* (5) [Student service](https://github.com/domKul/Students_microservice)

## Running the Students Server

To run the Students Server, follow these steps:

1. Make sure you have Java installed on your machine.

2. Clone the project sources from the repository.

3. Run the program with IDE or use command `./mvnw spring-boot:run`

4. This service are using in memory DB for more effective usage you can connect with SQL server.
Example for `application.properties`:

```
spring.datasource.url=jdbc:sqlite:mydb
spring.datasource.username=admin
spring.datasource.password=admin
spring.jpa.database=mysql
spring.jpa.show-sql=false
spring.jpa.hibernate.ddl-auto=update
```
and `pom.xml` SQL and Hibernate example:
``` 
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
```




### Student Controller
This controller provides endpoints for managing students in the system.

##### Add Student
* Endpoint: `POST /students`

Description: Adds a new student to the system.

Request Parameters:

+ firstName: The student's first name.
+ lastName: The student's last name.
+ email: The student's email address.
+ status: The student's status, which can be either ACTIVE or INACTIVE.

Request Body:
``` {
  "firstName": "Dominik",
  "lastName": "LastName",
  "email": "dominik.adas@gmail.com",
  "status": "ACTIVE"
    }
 ```

###### Response: 
The added student in JSON format.

#### Get List of Students
Endpoint: `GET /students`

Description: Returns a list of all students in the system.

Request Parameters:

+ status: (Optional) Filter the list by student status.
###### Response:

A list of students in JSON format.

```
[
{
    "id": 20000,
    "firstName": "firstName",
    "lastName": "lastName",
    "email": "firstName.lastName@dgmail.com",
    "status": "ACTIVE"
},
{
    "id": 20001,
    "firstName": "firstName",
    "lastName": "lastName",
    "email": "firstName.lastName@dgmail.com",
    "status": "INACTIVE"
}
]
```

#### Get Student by Id
Endpoint: `GET /students/{id}`

Description: Returns the student with the specified ID.

Request Parameters:

id: The ID of the student to retrieve.
###### Response:

The student with the specified ID in JSON format.
``` {
  "firstName": "Dominik",
  "lastName": "LastName",
  "email": "dominik.adas@gmail.com",
  "status": "ACTIVE"
    }
 ```


#### Modify Student
Endpoint: `PUT /students/{id}`

Description: Modifies the student with the specified ID.

Request Parameters:

+ id: The ID of the student to modify.

Request Body:

```
{
"firstName": "NewFirstName",
"lastName": "NewLastName",
"email": "new.email@gmail.com",
"status": "INACTIVE"
}
```
###### Response:

The updated student in JSON format.

#### Partially Modify Student
Endpoint: `PATCH /students/{id}`

Description: Partially modifies the student with the specified ID.

Request Parameters:

+ id: The ID of the student to modify.
Request Body:
```
{
"status": "INACTIVE"
}
```
###### Response:
The updated student in JSON format.

#### Get Students by Email
Endpoint: `POST /students/email`

Description: Returns a list of students whose email addresses match the specified list.

Request Parameters:

+ emailAddresses: A list of email addresses in JSON format.
+ 
Request Body:

```["email1@gmail.com", "email2@gmail.com"]```

Response:
A list of students corresponding to the provided email addresses in JSON format.
