package com.kartasiewicz.comments.Post;

import com.kartasiewicz.comments.exceptions.IllegalParameterException;
import org.junit.Test;


import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;

import static com.kartasiewicz.comments.Post.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CommentControllerTest {

    @Mock
    private CommentService commentService;
    @InjectMocks
    private CommentController commentController;

    @Test
    public void getCommentsShouldReturnAllCommentsForGivenPostIdIfCommentIdIsNullTest() {
        Flux<Comment> expectedResponse = TestUtil.prepareTwoComments();

        when(commentService.getComments(anyInt(), isNull())).thenReturn(expectedResponse);

        ResponseEntity<Flux<Comment>> response = commentController.getComments(PRESENT_POST_ID, null);

        Flux<Comment> responseBody = response.getBody();

        assertSame(expectedResponse, responseBody);
    }

    @Test
    public void getCommentsShouldReturnOneCommentForGivenPostIdIfCommentIdIsPassedTest() {
        Flux<Comment> expectedResponse = TestUtil.prepareOneComment();

        when(commentService.getComments(anyInt(), anyInt())).thenReturn(expectedResponse);

        ResponseEntity<Flux<Comment>> response = commentController.getComments(PRESENT_POST_ID, PRESENT_COMMENT_ID_ONE);

        Flux<Comment> responseBody = response.getBody();

        assertSame(expectedResponse, responseBody);
    }

    @Test
    public void getCommentsShouldReturnThrowExceptionForPostIdOutOfRangeTest() {
        assertThrows(IllegalParameterException.class, () -> commentController.getComments(POST_ID_INVALID_LOWER_BOUNDARY, null), ERROR_MESSAGE_POST_ID);
        assertThrows(IllegalParameterException.class, () -> commentController.getComments(POST_ID_INVALID_UPPER_BOUNDARY, null), ERROR_MESSAGE_POST_ID);
    }

    @Test
    public void getCommentsShouldReturnThrowExceptionForCommentIdOutOfRangeTest() {
        assertThrows(IllegalParameterException.class, () -> commentController.getComments(PRESENT_POST_ID, COMMENT_ID_INVALID_LOWER_BOUNDARY), ERROR_MESSAGE_COMMENT_ID);
        assertThrows(IllegalParameterException.class, () -> commentController.getComments(PRESENT_POST_ID, COMMENT_ID_INVALID_UPPER_BOUNDARY), ERROR_MESSAGE_COMMENT_ID);
    }
}
