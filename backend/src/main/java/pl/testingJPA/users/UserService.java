package pl.testingJPA.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserService {

    @Autowired
    UserRepository userRepository;
    /*
    injection ObjectMapper from jackson
    return as object json
     */
    @Autowired
    ObjectMapper objectMapper;

    /*
    method getUsers return all users saved in DB
     */
    @GetMapping("/users")
    public ResponseEntity getUsers() throws JsonProcessingException {

        List<User> users = userRepository.findAll();

        return ResponseEntity.ok(objectMapper.writeValueAsString(users));
    }

    /*
    method addUser add user to Db
    Optional method checking if user is already added to db ->> true ->> return 422 Unprocessable Content
    otherwise return 200 .ok
     */
    @PostMapping("/addusers")
    public ResponseEntity addUser(@RequestBody User user) {

        Optional<User> userFromDb = userRepository.findByUsername(user.getUsername());

        ResponseEntity<Object> result;

        if (userFromDb.isPresent()) {

            result = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();

        } else {

            User savedUser = userRepository.save(user);
            result = ResponseEntity.ok(savedUser);
        }

        return result;
    }

    /*
    method userFromDb checking if user exist in DB, false->> 401 .UNAUTHORIZED; 406 code ->> for empty fields
j
     */

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) {

        ResponseEntity result;

        Optional<User> userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb.isEmpty() || wrongPassword(userFromDb, user)) {

            result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } else {

            result = ResponseEntity.ok().build();
        }
        return result;
    }

        /*
    method login get user from DB; checking if user & password exists
     */

    private boolean wrongPassword(Optional<User> userFromDb, User user) {

        return !userFromDb.get().getPassword().equals(user.getPassword());
    }

        /*
     @GetMapping("/usersById") - methods searching user in DB by ID
     */

    @GetMapping("/usersById")
    public ResponseEntity getUsersById(@RequestBody User user) throws ResourceNotFoundException, JsonProcessingException {

       Optional<User> usersById = userRepository.findById(user.getId());

        ResponseEntity results;

        if (usersById.isEmpty()) {

            results = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else {
            results = ResponseEntity.ok(objectMapper.writeValueAsString(usersById));

        }

        return results;
    }

    /*
    method deletes users  by ID
     */

    @DeleteMapping("/deleteUserByID")
    @Transactional
    public ResponseEntity deleteById(@RequestBody User user) throws JsonProcessingException {

        List<User> usersById = userRepository.deleteById(user.getId());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /*
    write function to updated data for existing user
     */

    @PutMapping("/updateData")

    public ResponseEntity<User> updateData(@RequestBody User user) throws ResourceNotFoundException {

                User updatedUsers = userRepository.findById(user.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + user.getId()));

        ResponseEntity results;

            updatedUsers.setPassword(user.getPassword());
            updatedUsers.setNewpassword(user.getNewpassword());

            User userNewData = userRepository.save(user);
            results =  ResponseEntity.ok(userNewData);

        return results;
    }
    /*
    working properly. Data saved in DB
    Method creates all new data in db, by user id- Admin tools
    OK
     */
    

}

