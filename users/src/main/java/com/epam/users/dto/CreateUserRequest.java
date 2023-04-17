package com.epam.users.dto;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String username;
    private Integer amountOfPosts;
}
