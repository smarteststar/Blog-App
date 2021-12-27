package com.springboot.blog.controller;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.SignUpDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController @RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication= authenticationManager.authenticate
                 (new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User Authenticated Successfully !", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
        // check for validating userName exists or not
         if(userRepository.existsByUsername(signUpDto.getUsername())){
             return new ResponseEntity<>("UserName Already Taken!",HttpStatus.BAD_REQUEST);
         }
         //check for validating email exists or not
          if(userRepository.existsByEmail(signUpDto.getEmail())){
              return new ResponseEntity<>("MailID Already Exists!",HttpStatus.BAD_REQUEST);
          }
        User user=new User();
          user.setName(signUpDto.getName());
          user.setUsername(signUpDto.getUsername());
          user.setEmail(signUpDto.getEmail());
          user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles=roleRepository.findByName("ROLE_ADMIN").get();
          user.setRoles(Collections.singleton(roles));
          userRepository.save(user);

        return new ResponseEntity<>("User Registered Successfully!",HttpStatus.OK);
    }
}
