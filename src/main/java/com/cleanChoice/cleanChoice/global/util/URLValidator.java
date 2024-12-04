package com.cleanChoice.cleanChoice.global.util;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class URLValidator {

    // 유효하면 true, 유효하지 않으면 false return

    public static Mono<Boolean> validateUrl(String url) {
        WebClient webClient = WebClient.create();
        return webClient.head()
                .uri(url)
                .exchangeToMono(clientResponse -> {
                    HttpStatusCode statusCode = clientResponse.statusCode();
                    return Mono.just(statusCode.is2xxSuccessful());
                }).onErrorResume(e -> Mono.just(false));
    }

    public static Mono<Boolean> validateImageUrl(String imageUrl) {
        WebClient webClient = WebClient.create();
        return webClient.head()
                .uri(imageUrl)
                .exchangeToMono(clientResponse -> {
                    HttpStatusCode statusCode = clientResponse.statusCode();
                    String contentType = clientResponse.headers().contentType().map(MimeType::toString).orElse("");

                    return Mono.just(statusCode.is2xxSuccessful() && contentType.startsWith("image/"));
                }).onErrorResume(e -> Mono.just(false));
    }

}
