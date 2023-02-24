package com.kartasiewicz.comments.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
class CommentService {

    @Autowired
    private final CommentsConnector commentsConnector;

    public CommentService(CommentsConnector commentsConnector) {
        this.commentsConnector = commentsConnector;
    }

    Flux<Comment> getComments(int postId, Integer commentId) {
        return commentId == null
                ? commentsConnector.getAllComments(postId)
                : commentsConnector.getComment(postId, commentId);
    }
}