package com.amitnehra.service;

import com.amitnehra.models.Like;

import java.util.TreeSet;

public interface LikeService {
    TreeSet<Like> getLikesFor(Long id);
}
