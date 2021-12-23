package com.springboot.blog.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="posts" ,uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="title" ,nullable = false)
    private String title;
    @Column(name="description" ,nullable = false)
    private String description;
    @Column(name="content" ,nullable = false)
    private String content;
    //Cascade type All and orphanRemoval when we Add/Delete in parent child will also get reflected

    @OneToMany(mappedBy = "post" ,cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (!Objects.equals(id, post.id)) return false;
        if (!Objects.equals(title, post.title)) return false;
        if (!Objects.equals(description, post.description)) return false;
        if (!Objects.equals(content, post.content)) return false;
        return Objects.equals(comments, post.comments);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        return result;
    }
}
