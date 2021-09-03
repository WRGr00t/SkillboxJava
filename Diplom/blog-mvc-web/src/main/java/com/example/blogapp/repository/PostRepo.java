package com.example.blogapp.repository;

import com.example.blogapp.entity.Post;
import com.example.blogapp.entity.Status;
import com.example.blogapp.entity.Tag;
import com.example.blogapp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

    Post findPostById(@Param("id") Integer id);

    List<Post> findAllById(Integer id);

    Post findPostByText(@Param("text") String text);

    Post findPostByTitle(@Param("title") String title);

    Post findPostByTitleAndText(String title, String text);

    List<Post> findPostsByTimeBetween(LocalDateTime from, LocalDateTime to);

    List<Post> findAllByUser(@Param("user_id") User user, Pageable pageable);

    List<Post> findAllByTags(Tag tag, Pageable pageable);

    List<Post> findAllByModerationStatus(Status status, Pageable pageable);

    @Query("select p.time from Post p where p.isActive > 0 and p.moderationStatus = 'ACCEPTED'")
    List<LocalDateTime> createDates();

    @Query("select p from Post p order by p.id desc")
    List<Post> postsDesc();

    List<Post> findAllByTagsIn(List<Tag> tags);

    @Query("select p from Post p")
    Page<Post> findAll(Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.isActive > 0 AND p.moderationStatus = 'ACCEPTED' AND p.time < current_timestamp")
    Page<Post> findAllActual(Pageable pageable);

    //@Query("SELECT p FROM Post p WHERE p.isActive > 0 AND p.moderationStatus = 'ACCEPTED' AND p.time < current_timestamp AND (p.text LIKE '%query%' OR p.title LIKE '%query%')")
    //Page<Post> findAllActualLikeQuery (Pageable pageable, @Param("query") String query);
    //Page<Post> findAllByStatus(Status status, Pageable pageable);

    @Query("SELECT p FROM Post p JOIN p.comments c")
    Page<Post> searchByComments(Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.title LIKE %:query%")
    Page<Post> searchByTitle(Pageable pageable, @Param("query") String query);

    Page<Post> findAllByTitleContainingOrTextContaining(Pageable pageable, String title, String text);

    Page<Post> findAllByTimeBetween(Pageable pageable, LocalDateTime from, LocalDateTime to);

    @Query("SELECT p " +
            "FROM Post p " +
            "WHERE p.isActive > 0 AND " +
            "p.moderationStatus = 'ACCEPTED' AND " +
            "p.time < :to AND p.time > :from AND p.time < current_timestamp")
    Page<Post> findAllActualByTimeBetween(Pageable pageable,
                                          @Param("from") LocalDateTime from,
                                          @Param("to") LocalDateTime to);

    Page<Post> findAllByTagsIn(Pageable pageable, List<Tag> tags);

    @Query("SELECT p FROM Post p JOIN p.votes l GROUP BY p ORDER BY SUM(l.value) NULLS LAST")
    Page<Post> showPostByLikes(Pageable pageable);

    @Query("SELECT p FROM Post p JOIN p.comments c GROUP BY p ORDER BY COUNT (c) NULLS LAST")
    Page<Post> showPostByComments(Pageable pageable);
}
