package com.pg.ai_code_explainer.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExplainResponse {
    private String summary ;
    private String timeComplexity;
    private String spaceComplexity;
    private String dryRun;
    private String optimization;
}
