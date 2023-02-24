package com.kartasiewicz.comments.Comment;

import reactor.core.publisher.Flux;

public class TestUtil {

    public static final int PRESENT_POST_ID = 1;
    public static final int NOT_PRESENT_POST_ID = 2;
    public static final int PRESENT_COMMENT_ID_ONE = 1;
    public static final int PRESENT_COMMENT_ID_TWO = 2;
    public static final int NOT_PRESENT_COMMENT_ID = 3;
    public static final int POST_ID_INVALID_LOWER_BOUNDARY = -1;
    public static final int POST_ID_INVALID_UPPER_BOUNDARY = 101;
    public static final int COMMENT_ID_INVALID_LOWER_BOUNDARY = -1;
    public static final int COMMENT_ID_INVALID_UPPER_BOUNDARY = 501;
    public static final String TEST_COMMENT_ONE = "test_comment1";
    public static final String TEST_COMMENT_TWO = "test_comment2";
    public static final String TEST_EMAIL_ONE = "test1@email.com";
    public static final String TEST_EMAIL_TWO = "test2@email.com";
    public static final String TEST_BODY_ONE = "test_body1";
    public static final String TEST_BODY_TWO = "test_body2";
    public static final String ERROR_MESSAGE_POST_ID = "postId can not be lower then 1 and higher then 100.";
    public static final String ERROR_MESSAGE_COMMENT_ID = "commentId can not be lower then 1 and higher then 500.";

    public static Flux<Comment> prepareTwoComments() {
        Comment firstComment = new Comment(PRESENT_POST_ID, PRESENT_COMMENT_ID_ONE, TEST_COMMENT_ONE, TEST_EMAIL_ONE, TEST_BODY_ONE);
        Comment secondComment = new Comment(PRESENT_POST_ID, PRESENT_COMMENT_ID_TWO, TEST_COMMENT_TWO, TEST_EMAIL_TWO, TEST_BODY_TWO);
        return Flux.just(firstComment, secondComment);
    }

    public static Flux<Comment> prepareOneComment() {
        Comment firstComment = new Comment(PRESENT_POST_ID, PRESENT_COMMENT_ID_ONE, TEST_COMMENT_ONE, TEST_EMAIL_ONE, TEST_BODY_ONE);
        return Flux.just(firstComment);
    }

}
