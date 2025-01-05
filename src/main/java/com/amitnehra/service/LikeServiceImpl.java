package com.amitnehra.service;


import com.amitnehra.models.Like;
import com.amitnehra.repo.LikeRepo;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.TreeSet;

@Service
public class LikeServiceImpl implements LikeService{
    @Autowired
    private LikeRepo likeRepo;
    @Override
    public TreeSet<Like> getLikesFor(Long id) {
        return new TreeSet<>(likeRepo.getLikesFor(id));
    }
}
