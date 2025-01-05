package com.amitnehra.repo;

import com.amitnehra.models.Post;

import java.util.List;

public interface PostRepo {
    List<Post> fetchPosts(String id);
}
