package com.springboot.blog.repository;

import com.springboot.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

  Optional<User> findByUsernameOrEmail(String username, String email);

  @Query("select u from User u where u.email = :email")
  Optional<User> findByEmail(@Param("email") String email);

  Optional<User> findByUsername(String username);

  @Query("select (count(u) > 0) from User u where u.name = :name")
  boolean existsByName(@Param("name") String name);

  boolean existsByUsername(String username);


}
