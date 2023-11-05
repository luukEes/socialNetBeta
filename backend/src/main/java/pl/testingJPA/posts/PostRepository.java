package pl.testingJPA.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> deleteById(int id);

    Optional<Post> findById(int id);

    @Modifying
    @Query(value = "UPDATE comments SET comments_id = comments_id + 1", nativeQuery = true)
    Post incrementCommentsId();

    @Query(value = "SELECT MAX(comments_id) FROM comments", nativeQuery = true)
    Post getMaxCommentsId();
}

