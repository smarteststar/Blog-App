package com.springboot.blog.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDto {
    private long id;
    @NotEmpty(message = "Name should not be empty")
    private String name;
    @Email @NotEmpty(message = "Email should not be Empty")
    private String email;
    @NotEmpty @Size(min=10 ,message = "Body should not be empty")
    private String body;
}
