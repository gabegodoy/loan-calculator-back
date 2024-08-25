package com.dev.LoanCalculator.loancalculator;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan")
public class LoanController {
    @PostMapping(path = "/calculate", produces = "application/json", consumes = "application/json")
    public LoanResponse calculateLoan(@RequestBody LoanRequest request) {
        return new LoanService().calculateLoan(request);
    }

    @GetMapping(path = "/test", produces = "application/json")
    public String testEndpoint() {
        return "Test successful";
    }
}
