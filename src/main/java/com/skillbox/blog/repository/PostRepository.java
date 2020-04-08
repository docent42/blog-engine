package com.skillbox.blog.repository;

import com.skillbox.blog.entity.Post;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>,
    JpaSpecificationExecutor<Post> {

  @Query(nativeQuery = true, value = "SELECT DATE(time) FROM be.post WHERE time <= NOW()")
  ArrayList<String> findCountPublicationsOnDateByYear(int year);

  @Query(nativeQuery = true, value = "SELECT EXTRACT(YEAR FROM time) FROM be.post"
      + "WHERE time <= NOW() and WHERE is_active = 1 and moderation_status = 'ACCEPT'")
  TreeSet<Integer> findYearsWherePublicationsPresent();

  Optional<Post> findById(int id);

  @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM be.post p ")
  int findCountPosts();

  @Query(nativeQuery = true, value = "SELECT SUM(view_count) FROM be.post p")
  int findCountAllViews();

  @Query(nativeQuery = true, value = "SELECT MIN(time) FROM be.post p")
  LocalDateTime findFirstPublication();

  @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM be.post p WHERE user_id = :userId")
  int findCountPostsForCurrentUser(int userId);

  @Query(nativeQuery = true, value = "SELECT SUM(view_count) FROM be.post p "
      + "WHERE user_id = :userId")
  int findCountViewsOfPostsForCurrentUser(int userId);

  @Query(nativeQuery = true, value = "SELECT MIN(time) FROM be.post p "
      + "WHERE user_id = :userId")
  LocalDateTime findFirstPublicationForCurrentUser(int userId);

  @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM be.post p WHERE is_active = 1 and moderation_status = 'ACCEPT'")
  int postCountTotal();

  @Query(nativeQuery = true, value = "SELECT * FROM be.post p WHERE is_active = 1 "
      + "AND moderation_status = 'ACCEPT' "
      + "AND time <= NOW()")
  List<Post> findSuitablePosts();

  @Query(nativeQuery = true, value = "SELECT * FROM be.post WHERE is_active = 1 "
      + "AND moderation_status = 'ACCEPT' AND time \\:\\:DATE = :date \\:\\:DATE")
  List<Post> findByDate(String date);

  @Query(nativeQuery = true, value = "SELECT * FROM be.post WHERE is_active = 1 "
      + "AND moderation_status = 'ACCEPT'")
  List<Post> findAllPosts();

  @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM be.post WHERE is_active = 1 "
      + "AND moderation_status = 'NEW' AND moderator_id = :moderatorId")
  int findCountPostsForModeration(int moderatorId);

  @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM be.post WHERE is_active = 1 "
      + "AND moderation_status = :status AND moderator_id = :moderatorId")
  int findCountPostsForModerationByStatus(int moderatorId, String status);

  @Query(nativeQuery = true, value = "SELECT * FROM be.post WHERE is_active = 1 "
      + "AND moderation_status = :status AND moderator_id = :moderatorId")
  List<Post> findPostsForModerationByStatus(int moderatorId, String status);

  @Query(nativeQuery = true, value = "select p.* from be.post p join be.post2tag on post_id = p.id " +
          "join be.tag on tag_id = tag.id where tag.name = :tag " +
          "and p.is_active = 1 and p.moderation_status = 'ACCEPT';")
  List<Post> findAllByTag(String tag);

  @Query(nativeQuery = true, value = "select * from be.post where user_id = :userId and is_active = :isActive " +
          "and moderation_status LIKE :moderationStatus;")
  List<Post> findMyPosts(int userId, int isActive, String moderationStatus);
}