package com.xavierbouclet.kotlinreactive;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public record JavaHandler(KotlinService service) {

    public Mono<ServerResponse> aJavaBean(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.stringToMono("Hello Confoo!"), String.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
