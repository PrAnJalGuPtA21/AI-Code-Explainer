package com.pg.ai_code_explainer.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExplainResponse {
    private String detectedLanguage;
    private String summary;
    private String timeComplexity;
    private String spaceComplexity;
    private String dryRun;
    private String optimization;

    private List<String> patternsUsed;

    private Integer confidence;

    private String dsaPattern;

    private String difficulty;
    private List<TestCase> testCases;
    private List<LineExplanation> lineExplanation;
    private Integer codeScore;

    private String readability;

    private String maintainability;

    private String interviewFeedback;

    private java.util.List<CodeIssue> issues;
}
