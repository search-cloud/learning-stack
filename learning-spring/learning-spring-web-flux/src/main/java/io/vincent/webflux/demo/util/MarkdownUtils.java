package io.vincent.webflux.demo.util;

import java.util.List;
import java.util.stream.Collectors;

public class MarkdownUtils {

    public static String formatTable(List<List<String>> table) {
        if (table == null || table.isEmpty()) return "_没有数据_\n";
        StringBuilder sb = new StringBuilder();
        List<String> header = table.get(0);
        sb.append("| ").append(String.join(" | ", header)).append(" |\n");
        sb.append("| ").append(header.stream().map(h -> "---").collect(Collectors.joining(" | "))).append(" |\n");

        for (int i = 1; i < table.size(); i++) {
            List<String> row = table.get(i);
            sb.append("| ").append(String.join(" | ", row)).append(" |\n");
        }
        return sb.toString();
    }

    public static String formatError(String title, Throwable e) {
        return "\n| Error |\n| --- |\n| " + title + " 失败: " + e.getMessage() + " |\n\n";
    }

    public static String formatTitle(String title) {
        String time = java.time.LocalTime.now().withNano(0).toString();
        return "\n### [" + time + "] " + title + "\n\n";
    }

    public static String formatFinish() {
        return "\n---\n✅ 本次任务结束\n\n";
    }
}