package io.vincent.webflux.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class OllamaService {
    
    private final WebClient webClient;

    public OllamaService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<String> generateText(String model, String prompt) {
        log.info("Generating text using model: {}", model);
        
        return webClient.post()
                        .uri("/api/generate")
                        .body(BodyInserters.fromValue(GenerateRequest.builder().model(model).prompt(prompt).build()))
                        .retrieve()
                        .bodyToFlux(String.class)
                        .onErrorMap(e -> new RuntimeException("Failed to generate text", e));
    }
}