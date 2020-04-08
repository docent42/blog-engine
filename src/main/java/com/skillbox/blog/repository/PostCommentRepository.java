package com.skillbox.blog.repository;

import com.skillbox.blog.dto.response.temporary.TemporaryComment;
import com.skillbox.blog.entity.Post;
import com.skillbox.blog.entity.PostComment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Integer> {

  Optional<PostComment> findById(int id);

  @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM be.post_comment WHERE post_id = :postId")
  int findCountOfCommentsByPostId(int postId);

  @Query(value = "SELECT new com.skillbox.blog.dto.response.temporary"
          + ".TemporaryComment(c.id, c.time, u.id, u.name, u.photo, c.text) "
          + "FROM PostComment c JOIN User u ON c.userId = u.id WHERE c.postId = :postId")
  List<TemporaryComment> findListByPostId(@Param("postId") Post postId);
}