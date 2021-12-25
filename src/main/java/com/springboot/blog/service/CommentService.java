package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId,CommentDto comment);

    List<CommentDto> findCommentsByPostId(long postId);

    CommentDto findByCommentId(long postId,long commentId) ;

    void deletePostById(long postId ,Long commentId);

    CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest);
}
