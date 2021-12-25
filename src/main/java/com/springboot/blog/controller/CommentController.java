package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable long postId,@RequestBody @Valid CommentDto comment){
        CommentDto commentDto=commentService.createComment(postId,comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDto);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDto>> GetCommentsByPostId(@PathVariable long postId){
        List<CommentDto> commentDto=commentService.findCommentsByPostId(postId);
        return ResponseEntity.status(HttpStatus.OK).body(commentDto);
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> GetCommentByPostId(@PathVariable long postId,@PathVariable long commentId){
        CommentDto commentDto=commentService.findByCommentId(postId,commentId);
        return ResponseEntity.status(HttpStatus.OK).body(commentDto);
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateCommentByPostId
            (@PathVariable long postId,@PathVariable long commentId,@RequestBody CommentDto comment){
        comment.setId(commentId);
        createComment(postId,comment);
        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentByPostId
            (@PathVariable long postId,@PathVariable long commentId){
        commentService.deletePostById(postId,commentId);
        return new ResponseEntity<>("Comment "+  commentId +"Deleted Successfully",HttpStatus.ACCEPTED);
    }
}
