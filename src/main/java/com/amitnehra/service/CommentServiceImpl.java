package com.amitnehra.service;

import com.amitnehra.models.Comments;
import com.amitnehra.repo.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.TreeSet;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepo commentRepo;

    @Override
    public TreeSet<Comments> getCommentsFor(Long id) {
        return new TreeSet<>(commentRepo.getCommentsFor(id));
    }
}
