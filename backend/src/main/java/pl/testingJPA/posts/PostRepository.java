package pl.testingJPA.posts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> deleteById(int id);

    Optional<Post> findById(int id);
}

