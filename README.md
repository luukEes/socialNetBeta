# socialNetBeta
<Frontend
The main frontend technology in the socialNetBeta project is Javascript based on the React.js framework.
  
Additional HTML and CSS technologies are used here.
  
The user interface allows the user to register an account and log in to the site.
/>
<Backend
The technology responsible for the backend layer is Java based on the Spring Boot framework.

The logic is built on the functionality of the JPARepository interface.
 
Data exchange is supported by implementing a MySQL database. Using MVN repository - mysql-connector.

Methods provide basic data operations. Each method has exception handling. Basic methods in the application:
- @GetMapping("/users") - method getUsers return all users saved in DataBase,
  
- @PostMapping("/users") - method addUser add single user to DataBase,
 
- @PostMapping("/login") - method userFromDb checking if user exist in DataBase,
 
- @GetMapping("/usersById") - methods searching user in DataBase by ID ,
 
- @DeleteMapping("/deleteUserByID") - method deletes users from DataBase by ID.

The application is constantly being developed, new functionalities will be added over time.
/>
