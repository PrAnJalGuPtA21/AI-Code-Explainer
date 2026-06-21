package com.pg.ai_code_explainer.Services;

import com.fasterxml.jackson.databind.JsonNode;

import com.pg.ai_code_explainer.Client.AIClient;
import com.pg.ai_code_explainer.DTO.*;

import org.springframework.stereotype.Service;

@Service
public class ExplainServiceImpl
        implements ExplainService {

    private final AIClient aiClient;

    public ExplainServiceImpl(
            AIClient aiClient
    ) {
        this.aiClient =
                aiClient;
    }

    @Override
    public ExplainResponse explain(
            ExplainRequest request
    ) {

        String prompt =
                """
                        You are an expert software engineer.
                        
                        Analyze the code.
                        
                        Rules:
                        - Detect language automatically
                        - Explain code
                        - Return ONLY JSON
                        - No markdown
                        - No extra text
                        
                        {
                         "detectedLanguage":"",
                         "summary":"",
                         "timeComplexity":"",
                         "spaceComplexity":"",
                         "dryRun":"",
                         "optimization":""
                        }
                        
                        Code:
                        %s
                        """
                        .formatted(
                                request.getCode()
                        );

        JsonNode result =
                aiClient
                        .explain(
                                prompt
                        );

        return ExplainResponse
                .builder()
                .detectedLanguage(
                        result
                                .path("detectedLanguage")
                                .asText()
                )
                .summary(
                        result
                                .path("summary")
                                .asText()
                )
                .timeComplexity(
                        result
                                .path("timeComplexity")
                                .asText()
                )
                .spaceComplexity(
                        result
                                .path("spaceComplexity")
                                .asText()
                )
                .dryRun(
                        result
                                .path("dryRun")
                                .asText()
                )
                .optimization(
                        result
                                .path("optimization")
                                .asText()
                )
                .build();

    }

}