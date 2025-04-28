package io.vincent.webflux.demo.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
public class MarkdownStreamControllerV3 {

    @GetMapping(value = "/v2/api/markdownStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getMarkdownStream() {
        // 第1个接口 - 大模型流
        Mono<Flux<String>> model1Stream = callLargeModel("第一个大模型请求");

        // 第2、3个接口 - 二维表格数据（用多线程）
        Mono<List<List<String>>> tableData2 = callTableApi("接口2").subscribeOn(Schedulers.boundedElastic());
        Mono<List<List<String>>> tableData3 = callTableApi("接口3").subscribeOn(Schedulers.boundedElastic());

        // 第4个接口 - 大模型流
        Mono<Flux<String>> model4Stream = callLargeModel("第二个大模型请求");

        return Flux.concat(
                model1Stream.flatMapMany(flux -> safeFlux(flux, "大模型1")),
                safeMono(tableData2, "接口2"),
                safeMono(tableData3, "接口3"),
                model4Stream.flatMapMany(flux -> safeFlux(flux, "大模型4"))
        );
    }

    @GetMapping(value = "/v3/api/markdownStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getMarkdownStreamV3() {
        // 各个任务
        Mono<Flux<String>> model1Stream = callLargeModel("第一个大模型请求");
        Mono<List<List<String>>> tableData2 = callTableApi("接口2").subscribeOn(Schedulers.boundedElastic());
        Mono<List<List<String>>> tableData3 = callTableApi("接口3").subscribeOn(Schedulers.boundedElastic());
        Mono<Flux<String>> model4Stream = callLargeModel("第二个大模型请求");

        return Flux.concat(
                Flux.just(genTitle("🧠 大模型回答1")),
                model1Stream.flatMapMany(flux -> safeFlux(flux, "大模型1")),

                Flux.just(genTitle("📊 接口2表格数据")),
                safeMono(tableData2, "接口2"),

                Flux.just(genTitle("📊 接口3表格数据")),
                safeMono(tableData3, "接口3"),

                Flux.just(genTitle("🧠 大模型回答2")),
                model4Stream.flatMapMany(flux -> safeFlux(flux, "大模型4"))
        );
    }

    // 生成小标题（加上时间戳）
    private String genTitle(String title) {
        String time = java.time.LocalTime.now().withNano(0).toString();
        return "\n### [" + time + "] " + title + "\n\n";
    }

    // ===== 大模型接口，返回 Flux<String>（逐块数据流） =====
    private Mono<Flux<String>> callLargeModel(String prompt) {
        // 假设大模型是流式返回Markdown文本
        return Mono.fromSupplier(() -> Flux.just(
                "## " + prompt + " 回答开始\n",
                "这是第1段内容。\n",
                "这是第2段内容。\n"
        ).delayElements(java.time.Duration.ofMillis(500))); // 模拟流
    }

    // ===== 取二维表格接口，返回 List<List<String>> =====
    private Mono<List<List<String>>> callTableApi(String name) {
        return Mono.fromSupplier(() -> List.of(
                List.of("字段A", "字段B"),
                List.of(name + "-值1", "值1B"),
                List.of(name + "-值2", "值2B")
        )).delayElement(java.time.Duration.ofSeconds(2)); // 模拟慢查询
    }

    // ====== 保护大模型流，异常处理转成Markdown ======
    private Flux<String> safeFlux(Flux<String> flux, String apiName) {
        return flux.onErrorResume(e -> Flux.just(formatErrorMarkdown(apiName, e)));
    }

    // ====== 保护表格接口，异常处理转成Markdown ======
    private Flux<String> safeMono(Mono<List<List<String>>> mono, String apiName) {
        return mono
                .map(this::convertTableToMarkdown)
                .onErrorResume(e -> Mono.just(formatErrorMarkdown(apiName, e)))
                .flux();
    }

    // ===== 转换二维表格成Markdown表格 =====
    private String convertTableToMarkdown(List<List<String>> data) {
        if (data == null || data.isEmpty()) {
            return "_没有数据_\n";
        }
        StringBuilder sb = new StringBuilder();
        List<String> headers = data.get(0);
        sb.append("| ").append(String.join(" | ", headers)).append(" |\n");
        sb.append("| ").append(headers.stream().map(h -> "---").collect(Collectors.joining(" | "))).append(" |\n");

        for (int i = 1; i < data.size(); i++) {
            List<String> row = data.get(i);
            sb.append("| ").append(String.join(" | ", row)).append(" |\n");
        }
        sb.append("\n"); // 末尾加换行
        return sb.toString();
    }

    // ===== 异常信息Markdown格式化 =====
    private String formatErrorMarkdown(String apiName, Throwable e) {
        return "| Error |\n| --- |\n| " + apiName + " 失败: " + e.getMessage() + " |\n\n";
    }
}