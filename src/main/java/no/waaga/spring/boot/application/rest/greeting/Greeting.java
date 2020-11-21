package no.waaga.spring.boot.application.rest.greeting;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record Greeting(Long id, String content) {

}
