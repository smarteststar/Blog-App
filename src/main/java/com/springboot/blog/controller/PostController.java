package com.springboot.blog.controller;

import com.springboot.blog.entity.Post;
import com.springboot.blog.mapper.PostMapper;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    public final PostService postService;

    private final PostMapper postMapper;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        postService.createPost(postMapper.dtoToPost(postDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(postDto);
    }

    @GetMapping
    public PostResponse findAllPosts(
            @RequestParam(value="pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value="pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue =  AppConstants.DEFAULT_SORT_DIRECTION,required = false) String sortDir
    ) {
        return  postService.findAllPosts(pageNo,pageSize,sortBy,sortDir);
    }

   @GetMapping("/{id}")
   public ResponseEntity<PostDto> findPostById(@PathVariable Long id) {
       Optional<Post> post = postService.findPostById(id);
       return ResponseEntity.ok(postMapper.postToDto(post.orElse(null)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePostById(@PathVariable Long id, @RequestBody PostDto postDto) {
        Post post = postMapper.dtoToPost(postDto);
        post.setId(id);
        postService.createPost(post);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(postDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable Long id) {
        postService.deletePostById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
