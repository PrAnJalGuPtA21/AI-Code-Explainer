package com.pg.ai_code_explainer.DTO;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LineExplanation {

    private Integer line;

    private String explanation;

}