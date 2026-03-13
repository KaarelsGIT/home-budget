package com.home.home_budget.controller;

import com.home.home_budget.dto.BudgetSummaryDTO;
import com.home.home_budget.service.SummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/summary")
@RequiredArgsConstructor
public class SummaryController {

    private final SummaryService summaryService;

    @GetMapping
    public ResponseEntity<BudgetSummaryDTO> getSummary(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month
    ) {
        return ResponseEntity.ok(summaryService.getBudgetSummary(userId, year, month));
    }
}
