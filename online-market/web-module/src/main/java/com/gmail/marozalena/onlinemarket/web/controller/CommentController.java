package com.gmail.marozalena.onlinemarket.web.controller;

import com.gmail.marozalena.onlinemarket.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("articles/{id}/comments/{idCom}/delete")
    public String deleteComment(@PathVariable Long id,
                                @PathVariable Long idCom) {
        commentService.deleteComment(idCom);
        return "redirect:/articles/" + id;
    }
}
