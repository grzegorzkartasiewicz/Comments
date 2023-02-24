package com.kartasiewicz.comments.Post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
class CommentsConnector {
    private static final Logger logger = LoggerFactory.getLogger(CommentsConnector.class);

    private final WebClient webClient;

    CommentsConnector(WebClient webClient) {
        this.webClient = webClient;
    }

    Flux<Comment> getAllComments(int postId) {
        String uri = "/posts/" + postId + "/comments/";
        logger.info("Retrieving data from external server. Uri: {}", uri);
        return webClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Comment.class);
    }

    Flux<Comment> getComment(int postId, int commentId) {
        String uri = "/posts/" + postId + "/comments/";
        logger.info("Retrieving data from external server. Uri: {}", uri);
        return webClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Comment.class)
                .filter(comment -> comment.getId() == commentId);
    }
}
