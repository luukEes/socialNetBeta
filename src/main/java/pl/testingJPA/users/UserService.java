package pl.testingJPA.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    Optional method checking if user is allready added to db ->> true ->> return 422 Unprocessable Content
    otherwise return 200 .ok
     */
    @PostMapping("/users")
    public ResponseEntity addUser(@RequestBody User user) {
        Optional<User> userFromDb = userRepository.findByUsername(user.getUsername());

        ResponseEntity<Object> result = null;

        if (userFromDb.isPresent()) {
            result = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        else {

            User savedUser = userRepository.save(user);
            result =  ResponseEntity.ok(savedUser);
        }

        return result;
    }

    /*
    method userFromDb checking if user exist in DB, false->> 401 .UNAUTHORIZED; 406 code ->> for empty fields

     */

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) {
        ResponseEntity result = null;
        Optional<User> userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb.isEmpty() || wrongPassword(userFromDb, user)) {
            result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().build();
    }

        /*
    method login get user from DB; checking if user & password exists
     */

    private boolean wrongPassword(Optional<User> userFromDb, User user) {
        return !userFromDb.get().getPassword().equals(user.getPassword());
    }

    private boolean correctPassword(Optional<User> userFromDb, User user) {
        return userFromDb.get().getPassword().equals(user.getPassword());
    }

        /*
    method searching users id DB by ID
     */

    @GetMapping("/usersById")
    public ResponseEntity getUsersById(@RequestBody User user) throws JsonProcessingException {
        List<User> usersById = userRepository.findById(user.getId());
        ResponseEntity results = null;
        if (usersById.isEmpty()){
            results = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
}
