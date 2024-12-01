package com.cleanChoice.cleanChoice.domain.home.openAi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OpenAiRequestDto {

    private String model;

    private List<OpenAiMessageDto> messages;

    private double temperature;

    @Data
    @AllArgsConstructor
    public static class OpenAiMessageDto {
        private String role; // "system" or "user", "assistant"
        private String content;
    }

}
