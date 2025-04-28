package io.vincent.webflux.demo.api;

import io.vincent.webflux.demo.AgentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
public class StreamController {
    private final AgentService agentService;

    public StreamController(AgentService agentService) {
        this.agentService = agentService;
    }

    @GetMapping(value = "/stream/{sessionId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamResponse(@PathVariable String sessionId, @RequestParam String prompt) {
        return agentService.generateStream(prompt, sessionId);
    }
}