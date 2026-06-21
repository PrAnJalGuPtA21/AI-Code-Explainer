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
                        You are a senior software engineer and interview reviewer.
                        
                        TASK:
                        Analyze the provided source code and return ONLY VALID JSON.
                        
                        STRICT RULES:
                        1. Output MUST be valid JSON.
                        2. Do NOT wrap JSON in markdown.
                        3. Do NOT add comments.
                        4. Every field MUST exist.
                        5. Use empty string "" if unknown.
                        6. Use [] for missing arrays.
                        7. Keep explanations concise but useful.
                        8. Never invent unavailable behavior.
                        9. Infer language automatically.
                        10. Base complexity on actual implementation.
                        
                        ANALYSIS ORDER:
                        1. Detect language
                        2. Understand functionality
                        3. Identify DSA/design patterns
                        4. Compute complexity
                        5. Perform dry run
                        6. Suggest optimizations
                        7. Generate tests
                        8. Explain lines
                        9. Review code quality
                        
                        OUTPUT:
                        
                        {
                         "detectedLanguage":"",
                         "summary":"",
                         "timeComplexity":"",
                         "spaceComplexity":"",
                         "dryRun":"",
                         "optimization":"",
                        
                         "patternsUsed":[],
                        
                         "confidence":0,
                        
                         "dsaPattern":"",
                        
                         "difficulty":"",
                        
                         "lineExplanation":[
                           {
                             "line":0,
                             "explanation":""
                           }
                         ],
                        
                         "testCases":[
                           {
                             "input":"",
                             "output":"",
                             "explanation":""
                           }
                         ],
                        
                         "codeScore":0,
                        
                         "readability":"",
                        
                         "maintainability":"",
                        
                         "interviewFeedback":"",
                        
                         "issues":[
                           {
                             "severity":"",
                             "issue":"",
                             "suggestion":""
                           }
                         ]
                        }
                        
                        FIELD RULES:
                        
                        summary:
                        - Explain intent
                        - Mention major logic
                        
                        timeComplexity:
                        - Big O only
                        - Mention dominant operation
                        
                        spaceComplexity:
                        - Include auxiliary memory only
                        
                        dryRun:
                        - Use a small realistic example
                        
                        optimization:
                        - Suggest only meaningful improvements
                        
                        patternsUsed:
                        - algorithm
                        - design pattern
                        - coding approach
                        
                        dsaPattern:
                        Choose one:
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
                        None
                        
                        difficulty:
                        Easy
                        Medium
                        Hard
                        
                        lineExplanation:
                        - line numbers start from 1
                        - skip blanks
                        - concise
                        
                        testCases:
                        - exactly 3
                        - include normal case
                        - include edge case
                        - include boundary case
                        
                        codeScore:
                        0–100
                        
                        readability:
                        Poor
                        Average
                        Good
                        Excellent
                        
                        maintainability:
                        Low
                        Medium
                        High
                        
                        confidence:
                        0–100
                        
                        issues:
                        severity:
                        LOW
                        MEDIUM
                        HIGH
                        
                        CODE:
                        
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
                                        >() {
                                }
                        )
                )
                .lineExplanation(
                        mapper.convertValue(
                                result.path(
                                        "lineExplanation"
                                ),
                                new TypeReference<
                                        java.util.List<LineExplanation>
                                        >() {
                                }
                        )
                )
                .codeScore(
                        result
                                .path(
                                        "codeScore"
                                )
                                .asInt()
                )

                .readability(
                        result
                                .path(
                                        "readability"
                                )
                                .asText()
                )

                .maintainability(
                        result
                                .path(
                                        "maintainability"
                                )
                                .asText()
                )

                .interviewFeedback(
                        result
                                .path(
                                        "interviewFeedback"
                                )
                                .asText()
                )
                .issues(
                        mapper.convertValue(
                                result.path(
                                        "issues"
                                ),
                                new TypeReference<
                                        java.util.List<CodeIssue>
                                        >() {
                                }
                        )
                )
                .build();

    }

}