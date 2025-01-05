package com.amitnehra.repo;

import com.amitnehra.models.Comments;

import java.util.List;

public interface CommentRepo {
    List<Comments> getCommentsFor(Long id);
}
