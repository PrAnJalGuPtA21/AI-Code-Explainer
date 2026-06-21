package com.pg.ai_code_explainer.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ExplainRequest {

    @NotBlank
    private String code;

}
