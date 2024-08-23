package com.patika.bloghubservice.model;

import com.patika.bloghubservice.model.enums.BlogCommentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BlogComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String comment;

    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    private BlogCommentType blogCommentType;

    public BlogComment(User user, String comment) {
        this.user = user;
        this.comment = comment;
        this.createdDate = LocalDateTime.now();
        this.blogCommentType = BlogCommentType.INITIAL;
    }

    @Override
    public String toString() {
        return "BlogComment{" +
                "user=" + user +
                ", comment='" + comment + '\'' +
                ", createdDate=" + createdDate +
                ", blogCommentType=" + blogCommentType +
                '}';
    }
}
