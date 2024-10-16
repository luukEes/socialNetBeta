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
    public ResponseEntity newComment(@RequestHeader("id")  int id, @RequestBody String comments)  {
        // Retrieve the user by its ID
        Optional<Post> postbyID = postRepository.findById(id);

        // Check if the user exists
        if (postbyID.isEmpty()) {
            // If the user does not exist, return UNAUTHORIZED status
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        else  {
            // Create a new Post object with the retrieved user, comments, and comments_id
                Post post = new Post();
                post.setComments(comments);
                return ResponseEntity.ok(post);
        }
    }
    //so wee need ask kunolysGPT how add comments in existing post
    // because for now we're creating new POst !!!
    //  YES, YOU ENDED HERE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

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
                .orElseThrow(() -> new ResourceNotFoundException("Post with id does not exist : " + post.getId()));

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


