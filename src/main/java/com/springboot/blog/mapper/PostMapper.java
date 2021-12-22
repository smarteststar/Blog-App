package com.springboot.blog.mapper;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import org.mapstruct.Mapper;


import java.util.List;


@Mapper(
        componentModel = "spring"
)
public interface PostMapper {

    PostDto postToDto (Post post);

    Post dtoToPost(PostDto postDto);

    List<PostDto> toPostDTOs(List<Post> posts);

}
