package com.amitnehra.service;

import com.amitnehra.models.Post;
import com.amitnehra.repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{
    @Autowired
    private PostRepo postRepo;

    @Override
    public List<Post> fetchPosts(String id) {
        return postRepo.fetchPosts(id);
    }
}
