package com.example.project.comment.controller;

import com.example.project.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController  {

    private final CommentService commentService;
}
