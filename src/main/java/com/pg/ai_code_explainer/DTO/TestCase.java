package com.pg.ai_code_explainer.DTO;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestCase {

    private String input;

    private String output;

    private String explanation;

}