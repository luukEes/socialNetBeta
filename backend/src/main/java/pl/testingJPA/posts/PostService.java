package pl.testingJPA.posts;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.testingJPA.users.ResourceNotFoundException;
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



    @PutMapping("/addcomments")
    public ResponseEntity newComment(@RequestHeader("id") int userId, @RequestBody String comments, @RequestHeader(required = false, name = "comments_id") Long comments_id) {
        // Retrieve the user by its ID
        Optional<User> userById = userRepository.findById(userId);

        // Check if the user exists
        if (userById.isEmpty()) {
            // If the user does not exist, return UNAUTHORIZED status
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Create a new Post object with the retrieved user, comments, and comments_id
        Post post = new Post();
        post.setUser(userById.get());
        post.setComments(comments);
        post.setComments_id(comments_id);

        // Save the new post with the added comments
        try {
            Post savedPost = postRepository.save(post);
            return ResponseEntity.ok(savedPost);
        } catch (Exception e) {
            // Handle the case where comments_id is null, which violates the constraint
            return ResponseEntity.badRequest().body("comments_id cannot be null");
        }
    }


    //method posts does not work !!!!

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
    public ResponseEntity deletePost(@RequestBody Post post) throws ResourceNotFoundException  {

        List<Post> postById = postRepository.deleteById(post.getId());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /*
    create method to edit existing posts
     */
    @PostMapping("/addPost")
    public ResponseEntity newPost(@RequestBody Post post) {

        Post updatePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Post does not exist with id: " + post.getId()));

        ResponseEntity results;

        updatePost.setBody(post.getBody());

        Post userNewData = postRepository.save(post);
        results =  ResponseEntity.ok(userNewData);

        return results;
    }
    @PutMapping("/editPost")
    public ResponseEntity editPost(@RequestBody Post post) {

        Post updatePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Post does not exist with id: " + post.getId()));

        ResponseEntity results;

        updatePost.setBody(post.getBody());

        Post userNewData = postRepository.save(post);
        results =  ResponseEntity.ok(userNewData);

        return results;
    }



}


