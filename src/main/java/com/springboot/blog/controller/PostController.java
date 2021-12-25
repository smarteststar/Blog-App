package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody @Valid PostDto postDto){

        PostDto post=postService.createPost(postDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
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
   public ResponseEntity<?> findPostById(@PathVariable Long id) {
       Optional<PostDto> postDto= Optional.ofNullable(postService.findPostById(id));
    if (postDto.isPresent()) {
      return ResponseEntity.ok(postDto);
       }
       return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePostById(@PathVariable Long id, @RequestBody @Valid PostDto postDto) {
        postDto.setId(id);
        postService.createPost(postDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(postDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable Long id) {
        postService.deletePostById(id);
        return new ResponseEntity<>("Post with  Id "+ id +"  Deleted Successfully",HttpStatus.ACCEPTED);
    }

}
