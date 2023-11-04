package pl.testingJPA.posts;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenerationTime;
import pl.testingJPA.users.User;

import java.util.Optional;


@Entity
@Table(name = "posts")
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @NonNull
    private User user;

    @NonNull
    private String body;

    @NonNull
    private String comments;

    @Column(name = "comments_id")
    private Long commentsId;

    /*
    added some constructor because of loombook ?

     */

    public Post(Optional<User> userFromDb, String postBody) {

    }
}
