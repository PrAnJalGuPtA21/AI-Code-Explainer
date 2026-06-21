package com.pg.ai_code_explainer.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExplainRequest {

    @NotNull
    private String code;

    private String language;
}
