package com.kartasiewicz.comments.Comment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest {

    @Mock
    private CommentsConnector commentsConnector;

    @InjectMocks
    private CommentService commentService;

    @Test
    public void getCommentsShouldReturnAllCommentsForGivenPostIdIfCommentIdIsNullTest() {
        Flux<Comment> expectedResponse = TestUtil.prepareTwoComments();

        when(commentsConnector.getAllComments(anyInt())).thenReturn(expectedResponse);

        Flux<Comment> actualResponse = commentService.getComments(TestUtil.PRESENT_POST_ID, null);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void getCommentsShouldNotReturnAllCommentsForGivenPostIdNotMatchAnyPostTest() {
        Flux<Comment> allComments = TestUtil.prepareTwoComments();
        Flux<Comment> expectedComments = Flux.empty();

        when(commentsConnector.getAllComments(anyInt())).thenReturn(expectedComments);

        Flux<Comment> actualResponse = commentService.getComments(TestUtil.NOT_PRESENT_POST_ID, null);

        assertNotEquals(allComments, actualResponse);
        assertEquals(expectedComments, actualResponse);
    }

    @Test
    public void getCommentsShouldReturnOneCommentForGivenPostIdIfCommentIdIsNotNullTest() {
        Flux<Comment> expectedResponse = TestUtil.prepareOneComment();

        when(commentsConnector.getComment(anyInt(), anyInt())).thenReturn(expectedResponse);

        Flux<Comment> actualResponse = commentService.getComments(TestUtil.PRESENT_POST_ID, TestUtil.PRESENT_COMMENT_ID_ONE);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void getCommentsShouldNotReturnOneCommentForGivenPostIdIfCommentIdNotMatchAnyCommentTest() {
        Flux<Comment> allComments = TestUtil.prepareTwoComments();
        Flux<Comment> expectedComments = Flux.empty();

        when(commentsConnector.getComment(anyInt(), anyInt())).thenReturn(expectedComments);

        Flux<Comment> actualResponse = commentService.getComments(TestUtil.PRESENT_POST_ID, TestUtil.NOT_PRESENT_COMMENT_ID);

        assertNotEquals(allComments, actualResponse);
        assertEquals(expectedComments, actualResponse);
    }
}
