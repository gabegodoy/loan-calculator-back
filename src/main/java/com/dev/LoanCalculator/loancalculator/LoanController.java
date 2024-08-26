package com.dev.LoanCalculator.loancalculator;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/loan")
public class LoanController {

    private final LoanService loanService;
    @PostMapping(path = "/calculate", produces = "application/json", consumes = "application/json")
    public List<LoanResponse> calculateLoan(@RequestBody LoanRequest request) {
        List<LoanResponse> loanResponse = loanService.calculateLoan(request);
        return loanResponse;
    }
}
