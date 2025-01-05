package com.amitnehra.service;

import com.amitnehra.models.Comments;

import java.util.TreeSet;

public interface CommentService {
    TreeSet<Comments> getCommentsFor(Long id);
}
