package com.patika.bloghubservice.model;

import com.patika.bloghubservice.model.enums.StatusType;
import com.patika.bloghubservice.model.enums.UserType;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Setter
@Builder
@Table(name = "bloghub_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    private String bio;

    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    @OneToMany(mappedBy = "id")
    private Set<SocialMedia> socialMediaList;

    @OneToMany(mappedBy = "user")
    private List<Blog> blogList;

    @OneToMany(mappedBy = "id")
    private List<BlogTag> followedTagList = new ArrayList<>();

    @ElementCollection // Still use @ElementCollection for the map
    @CollectionTable(name = "blog_post_like_counts", joinColumns = @JoinColumn(name = "blog_post_id"))
    @MapKeyJoinColumn(name = "id") // Key now references Blog entity
    @Column(name = "like_count")
    private Map<Blog, Integer> blogTitleLikeCountMap;

    public User(String email, String password) {
        this.userType = UserType.STANDARD;
        this.statusType = StatusType.WAITING_APPROVAL;
        this.email = email;
        this.password = password;
        this.blogList = new ArrayList<>();
        this. blogTitleLikeCountMap = new HashMap<>();
    }
}
