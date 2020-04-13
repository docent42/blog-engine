package com.skillbox.blog.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "post_comment")
public class PostComment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "parent_id")
  private PostComment parentId;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User userId;

  @ManyToOne
  @JoinColumn(name = "post_id")
  private Post postId;

  @Column(nullable = false)
  private LocalDateTime time;

  @Column
  private String text;
}

