package com.cleanChoice.cleanChoice.domain.openAi.dto;

import lombok.Data;

import java.util.List;

@Data
public class OpenAiResponseDto {
    private List<ChoiceResponseDto> choices;

    @Data
    public static class ChoiceResponseDto {
        private MessageResponseDto message;

        @Data
        public static class MessageResponseDto {
            private String role;

            private String content;
        }
    }
}
