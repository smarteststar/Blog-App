package com.springboot.blog.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//purpose is only  for testing by generating password in Bcrypt format and storing in DB
public class PasswordGenerator {
  public static void main(String[] args) {
    PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    System.out.println( passwordEncoder.encode("admin"));

  }
}
