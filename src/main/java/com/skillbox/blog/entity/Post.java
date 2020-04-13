package com.skillbox.blog.entity;

import com.skillbox.blog.entity.enums.ModerationStatus;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "post")
@AllArgsConstructor
@NoArgsConstructor
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false)
  private byte isActive;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ModerationStatus moderationStatus;

  @ManyToOne
  @JoinColumn(name = "moderator_id")
  private User moderatorId;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User userId;

  @Column(columnDefinition = "TIMESTAMP", nullable = false)
  private LocalDateTime time;

  @Column(nullable = false)
  private String title;

  @Column(columnDefinition = "Text", nullable = false)
  private String text;

  @Column(nullable = false)
  private int viewCount;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(
      name = "post2tag",
      joinColumns = {@JoinColumn(name = "post_id")},
      inverseJoinColumns = {@JoinColumn(name = "tag_id")}
  )
  private List<Tag> tagList;
}
