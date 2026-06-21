package com.pg.ai_code_explainer.DTO;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeIssue {

    private String severity;

    private String issue;

    private String suggestion;

}