package com.kartasiewicz.comments.Comment;

import com.kartasiewicz.comments.exceptions.IllegalParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    private final CommentService commentService;

    CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("posts/{postId}")
    public ResponseEntity<Flux<Comment>> getComments(@PathVariable int postId,
                                                     @RequestParam(required = false) Integer commentId) {
        logger.info("Get comments for given postId: {} and commentId: {}", postId, commentId);

        validatePostId(postId);
        validateCommentId(commentId);

        Flux<Comment> comments = commentService.getComments(postId, commentId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(comments);
    }

    private void validatePostId(int postId) {
        String errorMessage = "postId can not be lower then 1 and higher then 100.";

        if (postId < 1 || postId > 100) {
            logger.warn(errorMessage);
            throw new IllegalParameterException(errorMessage);
        }
    }

    private void validateCommentId(Integer commentId) {
        String errorMessage = "commentId can not be lower then 1 and higher then 500.";

        if (commentId == null) {
            return;
        }

        if (commentId < 1 || commentId > 500) {
            logger.warn(errorMessage);
            throw new IllegalParameterException(errorMessage);
        }
    }
}
