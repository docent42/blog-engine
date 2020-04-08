package com.skillbox.blog.repository;

import com.skillbox.blog.entity.PostVoteEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostVoteRepository extends JpaRepository<PostVoteEntity, Integer>,
    JpaSpecificationExecutor<PostVoteEntity> {

  @Query(nativeQuery = true, value = "SELECT value FROM be.post_vote "
      + "WHERE post_id = :postId AND user_id = :userId")
  Optional<Integer> findValueByPostIdAndUserId(int postId, int userId);

  @Query(nativeQuery = true, value = "SELECT * FROM be.post_vote "
      + "WHERE post_id = :postId AND user_id = :userId")
  PostVoteEntity findPostVoteByPostIdAndUserId(int postId, int userId);

  @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM be.post_vote WHERE value = 1")
  int findCountOfAllLikes();

  @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM be.post_vote WHERE value = -1")
  int findCountOfAllDislikes();

  @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM be.post_vote "
      + "WHERE value = 1 AND user_id = :userId")
  int findCountOfLikesForCurrentUser(int userId);

  @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM be.post_vote "
      + "WHERE value = -1 AND user_id = :userId")
  int findCountOfDislikesForCurrentUser(int userId);

  @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM be.post_vote WHERE value = 1 "
      + "AND post_id = :postId")
  int findCountOfLikesById(int postId);

  @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM be.post_vote WHERE value = -1 "
      + "AND post_id = :postId")
  int findCountOfDislikesById(int postId);
}