package io.vincent.webflux.demo.api;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class ReactiveController {
    private final ChatClient chatClient;
    private final ChatMemory chatMemory;

    public ReactiveController(ChatClient chatClient, ChatMemory chatMemory) {
        this.chatClient = chatClient;
        this.chatMemory = chatMemory;
    }

    @GetMapping(value = "/stream/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamFlux() {
        return Flux.interval(Duration.ofSeconds(1))
                   .map(seq -> "响应式数据：" + (seq + 1))
                   .take(10); // 限制发送10条
    }

    @GetMapping(value = "/async-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> asyncStream() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        return Flux.create(fluxSink -> {
            List<CompletableFuture<Void>> futures = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                futures.add(CompletableFuture.runAsync(() ->
                        fluxSink.next("分区数据：" + Thread.currentThread().getName()), executor));
            }
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                    .thenRun(fluxSink::complete);
        });
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> streamAgent(@RequestParam String sessionId) {
        return chatClient.prompt()
                .advisors(new MessageChatMemoryAdvisor(chatMemory, sessionId, 5)) // 携带最近5条历史
                .stream().content()
                .map(response -> ServerSentEvent.builder(response).build());
    }

}