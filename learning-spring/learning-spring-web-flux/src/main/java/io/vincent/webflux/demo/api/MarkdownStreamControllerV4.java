package io.vincent.webflux.demo.api;

import io.vincent.webflux.demo.model.ConversationContext;
import io.vincent.webflux.demo.service.MarkdownStreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class MarkdownStreamControllerV4 {

    private final MarkdownStreamService streamService;

    @GetMapping(value = "/v4/api/markdownStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamMarkdown() {
        ConversationContext context = new ConversationContext();
        context.addUserMessage("请告诉我天气情况"); // 示例，未来可以传入实际对话
        return streamService.generateMarkdownStream(context);
    }
}