package com.amitnehra.controller;


import com.amitnehra.models.Account;
import com.amitnehra.models.Comments;
import com.amitnehra.models.Like;
import com.amitnehra.models.Post;
import com.amitnehra.service.CommentService;
import com.amitnehra.service.LikeService;
import com.amitnehra.service.PostService;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;
import java.util.TreeSet;

@Controller
public class ProfileController {
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private LikeService likeService;


    @RequestMapping("/profile/{id}")
    @Transactional
    public String returnProfile(@PathVariable String id, HttpSession session, Model model){
        List<Post> posts = postService.fetchPosts(id);
        for(Post post: posts){
            if(post != null){
                TreeSet<Comments> comments = commentService.getCommentsFor(post.getId());
                TreeSet<Like> likes = likeService.getLikesFor(post.getId());
                post.setComments(comments);
                post.setLikes(likes);
            }
        }
        model.addAttribute("posts", posts);
        return "profilePage";
    }
}
