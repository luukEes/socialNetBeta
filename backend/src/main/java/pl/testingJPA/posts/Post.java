package pl.testingJPA.posts;

import jakarta.persistence.*;
import lombok.*;
import pl.testingJPA.users.User;

@Entity
@Table(name = "posts")
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @NonNull
    private User user;

    @NonNull
    private String body;

}
