package com.skillbox.blog.service;

import com.skillbox.blog.dto.request.RequestPost;
import com.skillbox.blog.dto.response.ResponseResults;
import com.skillbox.blog.dto.response.errors.ErrorTitleText;
import com.skillbox.blog.entity.Post;
import com.skillbox.blog.entity.Tag;
import com.skillbox.blog.mapper.RequestPostToPost;
import com.skillbox.blog.repository.PostRepository;
import com.skillbox.blog.repository.TagRepository;
import com.skillbox.blog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = false)
@AllArgsConstructor
public class PostService {

    PostRepository postRepository;
    TagRepository tagRepository;
    UserRepository userRepository;
    RequestPostToPost requestMapper;

    public ResponseResults<ErrorTitleText> createPost(RequestPost post) {

        List<String> tags = Arrays.asList(post.getTags()
                                                 .trim()
                                                 .toLowerCase()
                                                 .split(","));

        List<Tag> existTagList = tagRepository.findAllByNameIn(tags);

        List<String> existTagListNames = existTagList.stream().map(Tag::getName).collect(Collectors.toList());



        List<Tag> newTagList = tags.stream().filter(tag -> !existTagListNames.contains(tag))
                                            .map(Tag::new)
                                            .collect(Collectors.toList());
        newTagList = tagRepository.saveAll(newTagList);

        existTagList.addAll(newTagList);

    Post postToSave = requestMapper.map(post);
    postToSave.setUserId(userRepository.findById(1));
    postToSave.setModeratorId(userRepository.findById(1));
    postToSave.setTagList(existTagList);

    postRepository.save(postToSave);

        return new ResponseResults<>(true,null);
    }
}
