package io.vincent.webflux.demo.api;

import io.vincent.webflux.demo.GenerateRequest;
import io.vincent.webflux.demo.OllamaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/ollama")
public class OllamaController {
    
    @Autowired
    private OllamaService ollamaService;
    
    @PostMapping(value = "/generate", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> generateText(@RequestBody GenerateRequest request) {
        return ollamaService.generateText(request.getModel(), request.getPrompt())
//                            .map(ResponseEntity::ok)
                            .onErrorResume(e ->
//                                    Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                            .body(e.getMessage()))
                                    // 返回错误信息而不是抛出异常，保持流的完整性
                                Flux.just(String.format("{\"error\": {\"message\": \"%s\"}}", e.getMessage()))
                            );
    }
}