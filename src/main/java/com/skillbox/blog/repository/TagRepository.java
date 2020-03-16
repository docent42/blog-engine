package com.skillbox.blog.repository;

import com.skillbox.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    List<Tag> findAllByNameIn(List<String> tagList);

    List<Tag> findByIdIn(List<Integer> ids);
}