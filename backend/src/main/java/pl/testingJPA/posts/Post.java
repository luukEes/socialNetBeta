package pl.testingJPA.posts;

import jakarta.persistence.*;
import lombok.*;
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
    @JoinColumn(name = "user_id") // Specify the column name for the foreign key
    @NonNull
    private User user;


    @NonNull
    private String body;

    @NonNull
    private String comments;

    @Column(name = "comments_id")
    private Long comments_id;

    /*
    added some constructor because of loombook ?

     */

}
