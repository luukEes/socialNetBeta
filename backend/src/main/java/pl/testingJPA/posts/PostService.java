package pl.testingJPA.posts;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.testingJPA.users.User;
import pl.testingJPA.users.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/posts")
    public ResponseEntity addPost(@RequestHeader("username") String username, @RequestBody String postBody) {
        Optional<User> userFromDb = userRepository.findByUsername(username);

        if (userFromDb.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Post post = new Post(userFromDb.get(), postBody);
        Post savedPost = postRepository.save(post);

        return ResponseEntity.ok(savedPost);
    }

    //method  search post in db by ID

    @GetMapping("/findPostById")
    @Transactional
    public ResponseEntity findPost(@RequestHeader("id") int id)  {
        Optional<Post> postFromDb = postRepository.findById(id);
        ResponseEntity response = null;

        if (postFromDb.isEmpty()) {
            response= ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else {
            response = ResponseEntity.ok(postFromDb);
        }
        return  response;
    }

    // delete single post by id

    @DeleteMapping("/deletePostById")
    @Transactional
    public ResponseEntity deletePost(@RequestBody Post post) throws JsonProcessingException {
        List<Post> postById = postRepository.deleteById(post.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
