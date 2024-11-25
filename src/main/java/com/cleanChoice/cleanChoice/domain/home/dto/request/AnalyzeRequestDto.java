package com.cleanChoice.cleanChoice.domain.home.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyzeRequestDto {

    private String url;

    private String name;

    private String brandName;

}
