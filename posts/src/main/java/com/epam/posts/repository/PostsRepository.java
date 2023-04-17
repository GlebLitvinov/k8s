package com.epam.posts.repository;

import com.epam.posts.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<PostEntity, Long> {

}
