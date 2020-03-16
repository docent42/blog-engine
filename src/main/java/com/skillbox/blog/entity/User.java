package com.skillbox.blog.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "blog_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private int id;

    @Column(nullable = false)
    private String code;

    @Column(name = "e_mail", nullable = false)
    private String email;

    @Column(nullable = false)
    private byte isModerator;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(length = 65535, columnDefinition = "Text")
    private String photo;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime regTime;

}
