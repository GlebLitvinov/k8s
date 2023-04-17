package com.epam.posts.dto;

import lombok.Data;

@Data
public class CreatePostRequest {
    private Long authorId;
    private String text;
}
