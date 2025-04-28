package io.vincent.webflux.demo;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class WebfluxDemoClient {
    public static void main(String[] args) {
        String sessionId = "";
        WebClient.create().get()
//                .uri("http://localhost:8080/stream/{sessionId}", sessionId)
                .uri("http://localhost:8080/stream/flux")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class)
                .subscribe(response -> System.out.println("响应: " + response));
    }
}
