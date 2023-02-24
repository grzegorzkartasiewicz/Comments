package com.kartasiewicz.comments.Comment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Objects;

class Comment {
    private final int postId;
    private final int id;
    @NotNull
    private final String name;
    @NotNull
    @Email
    private final String email;
    @NotNull
    private final String body;

    @JsonCreator
    public Comment(@JsonProperty("postId") int postId,
                   @JsonProperty("id") int id,
                   @JsonProperty("name") String name,
                   @JsonProperty("email") String email,
                   @JsonProperty("body") String body) {
        this.postId = postId;
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public int getPostId() {
        return postId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return postId == comment.postId && id == comment.id && name.equals(comment.name) && email.equals(comment.email) && body.equals(comment.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, id, name, email, body);
    }
}
