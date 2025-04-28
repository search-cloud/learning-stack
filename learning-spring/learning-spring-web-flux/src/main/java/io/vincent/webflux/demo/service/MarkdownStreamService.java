package io.vincent.webflux.demo.service;

import io.vincent.webflux.demo.model.ConversationContext;
import io.vincent.webflux.demo.util.MarkdownUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;

@Service
public class MarkdownStreamService {

    public Flux<String> generateMarkdownStream(ConversationContext context) {
        Mono<Flux<String>> model1Stream = callLargeModel(context, "第一个大模型请求");

        Mono<List<List<String>>> tableData2 = callTableApi("接口2").subscribeOn(Schedulers.boundedElastic());
        Mono<List<List<String>>> tableData3 = callTableApi("接口3").subscribeOn(Schedulers.boundedElastic());

        Mono<Flux<String>> model4Stream = callLargeModel(context, "第二个大模型请求");

        return Flux.concat(
                Flux.just(MarkdownUtils.formatTitle("🧠 大模型回答1")),
                safeFlux(model1Stream.flatMapMany(this::finalizeStream), "大模型1"),

                Flux.just(MarkdownUtils.formatTitle("📊 表格接口2数据")),
                safeMono(tableData2, "接口2"),

                Flux.just(MarkdownUtils.formatTitle("📊 表格接口3数据")),
                safeMono(tableData3, "接口3"),

                Flux.just(MarkdownUtils.formatTitle("🧠 大模型回答2")),
                safeFlux(model4Stream.flatMapMany(this::finalizeStream), "大模型4")
        );
    }

    private Mono<Flux<String>> callLargeModel(ConversationContext context, String prompt) {
        // 模拟流式返回大模型输出
        return Mono.fromSupplier(() -> {
            Flux<String> dataFlux = Flux.just(
                    "## " + prompt + "\n",
                    "这是第1段内容\n",
                    "这是第2段内容\n"
            ).delayElements(Duration.ofSeconds(2));

            Flux<String> heartbeatFlux = Flux.interval(Duration.ofSeconds(10))
                    .map(i -> "<!-- heartbeat -->\n");

            return Flux.merge(dataFlux, heartbeatFlux);
        });
    }

    private Mono<List<List<String>>> callTableApi(String name) {
        return Mono.fromSupplier(() -> List.of(
                List.of("字段A", "字段B"),
                List.of(name + "-值1", "值1B"),
                List.of(name + "-值2", "值2B")
        )).delayElement(Duration.ofSeconds(3));
    }

    private Flux<String> safeFlux(Flux<String> flux, String apiName) {
        return flux
                .timeout(Duration.ofMinutes(2))
                .onErrorResume(e -> Flux.just(MarkdownUtils.formatError(apiName, e), MarkdownUtils.formatFinish()));
    }

    private Flux<String> safeMono(Mono<List<List<String>>> mono, String apiName) {
        return mono
                .map(MarkdownUtils::formatTable)
                .onErrorResume(e -> Mono.just(MarkdownUtils.formatError(apiName, e)))
                .flux();
    }

    private Flux<String> finalizeStream(Flux<String> content) {
        return Flux.concat(
                content,
                Flux.just(MarkdownUtils.formatFinish())
        );
    }
}