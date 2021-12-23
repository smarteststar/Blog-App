package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.mapper.CommentMapper;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;



@Service @Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final CommentMapper commentMapper;

    @Override
    public CommentDto createComment(long postId, CommentDto comment) {
        Comment toComment = commentMapper.dtoToComment(comment);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        toComment.setPost(post);
        return commentMapper.commentToDto(commentRepository.save(toComment)) ;
    }

    @Override
    public List<CommentDto> findCommentsByPostId(long postId) {
        List<Comment> comments=commentRepository.findByPostId(postId);
        return commentMapper.commentToDtos(comments);
    }

    @Override
    public CommentDto findByCommentId(long postId,long commentId) {
        Comment comment= commentRepository.findByPostIdAndId(postId, commentId);
        log.info("comment is {}" +comment.toString());
        return commentMapper.commentToDto(comment);
    }

    @Override
    public void deletePostById(long postId, Long commentId) {
        Comment comment= commentRepository.findByPostIdAndId(postId, commentId);
        if(comment!=null){
            commentRepository.deleteById(comment.getId());
        }
    }


}
