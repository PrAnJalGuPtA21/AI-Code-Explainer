package com.pg.ai_code_explainer.Client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AIClient {

    private final WebClient webClient;

    private final ObjectMapper mapper =
            new ObjectMapper();

    @Value("${gemini.url}")
    private String url;

    @Value("${gemini.api.key}")
    private String apiKey;

    public JsonNode explain(
            String prompt
    ) {

        Map<String, Object> body =
                Map.of(
                        "contents",
                        List.of(
                                Map.of(
                                        "parts",
                                        List.of(
                                                Map.of(
                                                        "text",
                                                        prompt
                                                )
                                        )
                                )
                        )
                );

        String response =
                webClient
                        .post()
                        .uri(
                                url +
                                        "?key=" +
                                        apiKey
                        )
                        .bodyValue(body)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

        try {

            JsonNode root =
                    mapper.readTree(
                            response
                    );

            String aiText =
                    root
                            .path("candidates")
                            .get(0)
                            .path("content")
                            .path("parts")
                            .get(0)
                            .path("text")
                            .asText();

            return mapper.readTree(
                    aiText
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "AI parsing failed",
                    e
            );

        }

    }

}