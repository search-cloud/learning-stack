package io.vincent.webflux.demo.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
public class FakeModelStreamService {

    public Flux<String> generateTypingEffect(String text, Duration delay) {
        return Flux.fromStream(text.codePoints()
                .mapToObj(cp -> new String(Character.toChars(cp))))
                .delayElements(delay); // 每个字间隔 30ms
    }

    public Flux<String> simulateResponse(String userInput) {
        String reply = "你好！根据你的问题「" + userInput + "」，我为你推荐以下内容：\n1. 去云南玩，可以选择大理丽江。\n2. 国庆节注意防晒和人流高峰。";
        return generateTypingEffect(reply, Duration.ofMillis(30));
    }
}