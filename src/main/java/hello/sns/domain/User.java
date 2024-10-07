package hello.sns.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Objects;

@Getter @Setter
@Table(name = "account")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String loginId;

    @Column(unique = true)
    private String name;

    @Column(nullable = false)
    private String password;

    private String profileImageUrl;

    private String bio;

//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Post> posts = new ArrayList<>();
//
//    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Follow> followers = new ArrayList<>();
//
//    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Follow> following = new ArrayList<>();
}
