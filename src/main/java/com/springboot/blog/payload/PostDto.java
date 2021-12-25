package com.springboot.blog.payload;

import com.springboot.blog.entity.Comment;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDto {
    private Long id;
    @NotEmpty @Size(min = 3,max=20 ,message = "Post Title should have Minimum 3 and Maximum 20 characters")
    private String title;
    @NotEmpty @Size(min = 10, message = "Post Description should have Minimum 10 characters")
    private String description;
    @NotEmpty
    private String content;

    private Set<Comment> comments;

}
