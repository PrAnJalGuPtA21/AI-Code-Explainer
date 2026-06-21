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
                        Return ONLY valid JSON.
                        
                        {
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