package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.mapper.PostMapper;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    public PostResponse findAllPosts(int pageNo, int pageSize,String sortBy,String sortDir) {
        //sorting Based Upon Asc and Desc
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
       // create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts=postRepository.findAll(pageable);
        // get content of page object
        List<Post> postList=posts.getContent();

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postMapper.toPostDTOs(postList));
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    public PostDto findPostById(Long id) {

        return postMapper.postToDto(postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id)));
    }

    public PostDto createPost(PostDto postDto) {
        Post post=postMapper.dtoToPost(postDto);
        return  postMapper.postToDto(postRepository.save(post));
    }

    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }
}
