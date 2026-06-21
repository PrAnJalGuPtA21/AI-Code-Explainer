package com.pg.ai_code_explainer.Services;

import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pg.ai_code_explainer.Client.AIClient;
import com.pg.ai_code_explainer.DTO.*;
import com.fasterxml.jackson.core.type.TypeReference;

import org.springframework.stereotype.Service;

@Service
public class ExplainServiceImpl
        implements ExplainService {

    private final AIClient aiClient;

    private final ObjectMapper mapper =
            new ObjectMapper();

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
                        
                        Analyze this code.
                        
                        Rules:
                        - Detect language automatically
                        - Return ONLY valid JSON
                        - No markdown
                        - Detect DSA pattern
                        - No explanation outside JSON
                        - Estimate difficulty
                        - Generate sample test cases
                        - Explain line by line briefly with visuals
                        
                        Output:
                        
                        {
                         "detectedLanguage":"",
                         "summary":"",
                         "timeComplexity":"",
                         "spaceComplexity":"",
                         "dryRun":"",
                         "optimization":"",
                         "patternsUsed":[],
                         "confidence":0,
                         "lineExplanation":[
                            {
                              "line":0,
                              "explanation":""
                            }
                          ],
                         "dsaPattern":"",
                          "difficulty":"",
                           "testCases":[
                             {
                               "input":"",
                               "output":"",
                               "explanation":""
                             }
                           ]
                        }
                        
                        Rules:
                        - line numbers start from 1
                        - skip blank lines
                        
                        Generate:
                        - 3 realistic test cases
                        - include edge cases
                        
                        patternsUsed:
                        - algorithms
                        - design patterns
                        - coding approaches
                        
                        DSA patterns examples:
                        Sliding Window
                        Two Pointers
                        HashMap
                        Binary Search
                        Greedy
                        DFS
                        BFS
                        DP
                        Recursion
                        Backtracking
                        Graph
                        Tree
                        Stack
                        Queue
                        Prefix Sum
                        Heap
                        Sorting
                        
                        Difficulty:
                        Easy
                        Medium
                        Hard
                        
                        confidence:
                        0–100
                        
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
                .patternsUsed(
                        mapper
                                .convertValue(
                                        result.path(
                                                "patternsUsed"
                                        ),
                                        new com.fasterxml.jackson.core.type.TypeReference<java.util.List<String>>() {
                                        }
                                )
                )
                .confidence(
                        result
                                .path("confidence")
                                .asInt()
                )
                .dsaPattern(
                        result
                                .path("dsaPattern")
                                .asText()
                )
                .difficulty(
                        result
                                .path("difficulty")
                                .asText()
                )
                .testCases(
                        mapper.convertValue(
                                result.path(
                                        "testCases"
                                ),
                                new TypeReference<
                                        java.util.List<TestCase>
                                        >() {}
                        )
                )
                .lineExplanation(
                        mapper.convertValue(
                                result.path(
                                        "lineExplanation"
                                ),
                                new TypeReference<
                                        java.util.List<LineExplanation>
                                        >() {}
                        )
                )
                .build();

    }

}