package com.amitnehra.repo;

import com.amitnehra.models.Like;

import java.util.Collection;
import java.util.List;

public interface LikeRepo {
    List<Like> getLikesFor(Long id);
}
