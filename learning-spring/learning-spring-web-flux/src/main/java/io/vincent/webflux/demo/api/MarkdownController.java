package io.vincent.webflux.demo.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
public class MarkdownController {

    @GetMapping("/api/markdownStream")
    public Flux<String> getMarkdownStream() {
        Mono<List<List<String>>> api1 = callApi1();

        // 这里是并行执行的
        Mono<List<List<String>>> api2 = callApi2().subscribeOn(Schedulers.parallel());
        Mono<List<List<String>>> api3 = callApi3().subscribeOn(Schedulers.parallel());

        return Flux.concat(
                safeCallApi("API1", api1),
                safeCallApi("API2", api2),
                safeCallApi("API3", api3)
        );
    }

    private Mono<String> safeCallApi(String apiName, Mono<List<List<String>>> apiCall) {
        return apiCall
                .map(this::convertToMarkdownTable)
                .onErrorResume(e -> Mono.just(convertErrorToMarkdown(apiName, e)));
    }

    // 模拟API
    private Mono<List<List<String>>> callApi1() {
        return Mono.just(List.of(
                List.of("Name", "Age"),
                List.of("Alice", "30"),
                List.of("Bob", "25")
        )).delayElement(Duration.ofSeconds(1)); // 模拟延迟
    }

    private Mono<List<List<String>>> callApi2() {
        return Mono.just(List.of(
                List.of("Product", "Price"),
                List.of("Apple", "$1"),
                List.of("Banana", "$0.5")
        )).delayElement(Duration.ofSeconds(5)); // 故意慢
    }

    private Mono<List<List<String>>> callApi3() {
        return Mono.just(List.of(
                List.of("City", "Country"),
                List.of("Paris", "France"),
                List.of("Berlin", "Germany")
        )).delayElement(Duration.ofSeconds(2)); // 比API2快
    }

    private String convertToMarkdownTable(List<List<String>> data) {
        if (data == null || data.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        List<String> header = data.get(0);
        sb.append("| ").append(String.join(" | ", header)).append(" |\n");
        sb.append("| ").append(header.stream().map(h -> "---").collect(Collectors.joining(" | "))).append(" |\n");
        for (int i = 1; i < data.size(); i++) {
            List<String> row = data.get(i);
            sb.append("| ").append(String.join(" | ", row)).append(" |\n");
        }
        return sb.toString();
    }

    private String convertErrorToMarkdown(String apiName, Throwable e) {
        return "| Error |\n" +
                "| --- |\n" +
                "| Failed to fetch " + apiName + ": " + e.getMessage() + " |\n";
    }
}