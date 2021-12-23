package com.springboot.blog.mapper;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.payload.CommentDto;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(
        componentModel = "spring"
)
public interface CommentMapper {

    CommentDto commentToDto(Comment comment);

    Comment dtoToComment(CommentDto commentDto);

    List<CommentDto> commentToDtos(List<Comment> comments);

}
