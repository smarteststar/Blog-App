package com.springboot.blog.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpDto {
    private String name;
    private String username;
    private String email;
    private String password;
}
