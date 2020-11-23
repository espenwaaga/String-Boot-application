package no.waaga.spring.boot.server.rest.greeting;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Greeting(@JsonProperty("id") Long id, @JsonProperty("content") String content) {

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [id: " + id + ", content: " + content + "]";
    }
}
