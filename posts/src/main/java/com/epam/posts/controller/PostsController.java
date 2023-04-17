package com.epam.posts.controller;

import java.time.Instant;

import com.epam.posts.dto.CreatePostRequest;
import com.epam.posts.dto.PostDto;
import com.epam.posts.dto.UserDto;
import com.epam.posts.entity.PostEntity;
import com.epam.posts.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Log4j2
@RestController
@RequiredArgsConstructor
public class PostsController {

    private final PostsRepository repository;

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${api.users.url}")
    private String usersUrl;

    @GetMapping
    public String greetings() {
        return "Hello, k8s!";
    }

    @GetMapping("/posts/{id}")
    public PostDto getPostById(@PathVariable Long id) {
        return repository.findById(id).map(entity -> buildPostDto(entity))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/posts")
    public PostDto createPost(@RequestBody CreatePostRequest request) {
        var entity = new PostEntity();
        entity.setAuthorId(request.getAuthorId());
        entity.setText(request.getText());
        entity.setPostedAt(Instant.now());
        changeNumberOfPortsNumber(entity.getAuthorId(), 1);
        repository.save(entity);
        return buildPostDto(entity);
    }

    @PutMapping("/posts/{id}")
    public PostDto updatePost(@PathVariable Long id, @RequestBody CreatePostRequest request) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        entity.setText(request.getText());
        entity = repository.save(entity);
        return buildPostDto(entity);
    }

    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable Long id) {
        var entity = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        changeNumberOfPortsNumber(entity.getAuthorId(), -1);
        repository.delete(entity);
    }

    private synchronized void changeNumberOfPortsNumber(Long userId, int change) {
        try {
            var res = restTemplate.getForEntity(usersUrl + "/" + userId, UserDto.class).getBody();
            if (res != null) {
                res.setAmountOfPosts(res.getAmountOfPosts() + change);
                restTemplate.put(usersUrl + "/" + userId, res);
            }
        } catch (RestClientException e) {
            log.warn("Unable to update user with it [{}] amount of posts", userId, e);
        }
    }

    private PostDto buildPostDto(PostEntity entity) {
        return PostDto.builder()
                .postedAt(entity.getPostedAt())
                .id(entity.getId())
                .authorId(entity.getAuthorId())
                .text(entity.getText())
                .build();
    }
}
