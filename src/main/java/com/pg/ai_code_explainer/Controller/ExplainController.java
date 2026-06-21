package com.pg.ai_code_explainer.Controller;

import com.pg.ai_code_explainer.DTO.ExplainRequest;
import com.pg.ai_code_explainer.DTO.ExplainResponse;
import com.pg.ai_code_explainer.Services.ExplainService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ExplainController {

    private final ExplainService explainService;

    @PostMapping("/explain")
    public ExplainResponse explain(
            @Valid
            @RequestBody ExplainRequest request
            ){
        return explainService.explain(request);
    }
}
