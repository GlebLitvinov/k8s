package com.epam.posts.dto;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDto {
    private Long id;
    private Long authorId;
    private String text;
    private Instant postedAt;

}
