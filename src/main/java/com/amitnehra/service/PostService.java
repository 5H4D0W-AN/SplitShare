package com.amitnehra.service;

import com.amitnehra.models.Post;

import java.util.List;

public interface PostService {
    List<Post> fetchPosts(String id);
}
