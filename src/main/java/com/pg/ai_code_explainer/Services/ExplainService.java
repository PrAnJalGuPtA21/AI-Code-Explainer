package com.pg.ai_code_explainer.Services;

import com.pg.ai_code_explainer.DTO.ExplainRequest;
import com.pg.ai_code_explainer.DTO.ExplainResponse;

public interface ExplainService {

    ExplainResponse explain(
            ExplainRequest request
    );
}
