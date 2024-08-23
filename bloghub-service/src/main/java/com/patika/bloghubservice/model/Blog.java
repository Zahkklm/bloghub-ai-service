package com.patika.bloghubservice.model;

import com.patika.bloghubservice.model.enums.BlogStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String text;

    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private BlogStatus blogStatus;

    private Long likeCount;

    private String imageUrl;

    @OneToMany(mappedBy = "id")
    private List<BlogComment> blogCommentList = new ArrayList<>();

    public Blog(String title, String text, User user) {
        this.title = title;
        this.text = text;
        this.user = user;
        this.createdDate = LocalDateTime.now();
        this.blogStatus = BlogStatus.DRAFT;
        this.likeCount = 0L;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", createdDate=" + createdDate +
                ", user=" + user +
                ", blogStatus=" + blogStatus +
                '}';
    }
}
