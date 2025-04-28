package io.vincent.webflux.demo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GenerateRequest {
    private String model;
    private String prompt;
}