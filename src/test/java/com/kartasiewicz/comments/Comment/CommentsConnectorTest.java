package com.kartasiewicz.comments.Comment;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Before;
import org.junit.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest()
@ExtendWith(SpringExtension.class)
public class CommentsConnectorTest {
    private MockWebServer mockWebServer;
    private CommentsConnector commentsConnector;

    @Before
    public void setupMockWebServer() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        commentsConnector = new CommentsConnector(WebClient.create(mockWebServer.url("/").url().toString()));
    }

    @Test
    public void getAllCommentsShouldReturnAllCommentsForGivenPostIdIfPresentTest() throws JsonProcessingException {
        Flux<Comment> expectedResponse = TestUtil.prepareTwoComments();
        mockWebServer.enqueue(new MockResponse().setResponseCode(HttpStatus.OK.value()).setBody(new ObjectMapper().writeValueAsString(expectedResponse.collectList().block())).addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        Flux<Comment> actualResponse = commentsConnector.getAllComments(TestUtil.PRESENT_POST_ID);

        assertEquals(expectedResponse.collectList().block(), actualResponse.collectList().block());
    }

    @Test
    public void getAllCommentsShouldThrowExceptionForInvalidResponseTest() throws JsonProcessingException {
        Flux<Comment> expectedResponse = TestUtil.prepareTwoComments();
        mockWebServer.enqueue(new MockResponse().setResponseCode(HttpStatus.BAD_REQUEST.value()).setBody(new ObjectMapper().writeValueAsString(expectedResponse.collectList().block())).addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        Flux<Comment> actualResponse = commentsConnector.getAllComments(TestUtil.PRESENT_POST_ID);

        assertThrows(WebClientResponseException.class, () -> actualResponse.collectList().block());
    }

    @Test
    public void getCommentShouldReturnOneCommentForGivenPostIdIfPresentAndCommentIdIfPresentTest() throws JsonProcessingException {
        Flux<Comment> expectedResponse = TestUtil.prepareOneComment();
        mockWebServer.enqueue(new MockResponse().setResponseCode(HttpStatus.OK.value()).setBody(new ObjectMapper().writeValueAsString(expectedResponse.collectList().block())).addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        Flux<Comment> actualResponse = commentsConnector.getComment(TestUtil.PRESENT_POST_ID, TestUtil.PRESENT_COMMENT_ID_ONE);

        assertEquals(expectedResponse.collectList().block(), actualResponse.collectList().block());
    }

    @Test
    public void getCommentShouldThrowExceptionForInvalidResponseTest() throws JsonProcessingException {
        Flux<Comment> expectedResponse = TestUtil.prepareOneComment();
        mockWebServer.enqueue(new MockResponse().setResponseCode(HttpStatus.BAD_REQUEST.value()).setBody(new ObjectMapper().writeValueAsString(expectedResponse.collectList().block())).addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        Flux<Comment> actualResponse = commentsConnector.getComment(TestUtil.PRESENT_POST_ID, TestUtil.PRESENT_COMMENT_ID_ONE);

        assertThrows(WebClientResponseException.class, () -> actualResponse.collectList().block());
    }
}
