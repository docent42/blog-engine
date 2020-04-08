package com.skillbox.blog.repository;

import com.skillbox.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

  List<Tag> findAllByNameIn(List<String> tagList);

  List<Tag> findByIdIn(List<Integer> ids);

  List<Tag> findAllByNameContaining(String query);

  @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM be.post2tag p WHERE tag_id = :tagId")
  int findTagLinks(int tagId);

  @Query(nativeQuery = true, value = "SELECT name from be.post2tag JOIN be.tag "
      + "ON be.post2tag.tag_id = be.tag.id WHERE post_id = :postId")
  String[] findByPostId(int postId);
}