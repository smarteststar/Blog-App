package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.mapper.CommentMapper;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    if (!comments.isEmpty()) {
      return commentMapper.commentToDtos(comments);
        }
        throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comments does not belong to post");
    }

  @Override
  public CommentDto findByCommentId(long postId, long commentId) {
    Comment comment = commentRepository.findByPostIdAndId(postId, commentId);
    if (comment != null) {
      return commentMapper.commentToDto(comment);
    }
      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }


    @Override
    public void deletePostById(long postId, Long commentId) {
        Comment comment= commentRepository.findByPostIdAndId(postId, commentId);
        if(comment!=null){
            commentRepository.deleteById(comment.getId());
        }
    }

    @Override
    public CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest){
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return commentMapper.commentToDto(updatedComment);
    }


}
