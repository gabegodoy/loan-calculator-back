package com.dev.LoanCalculator.loancalculator;
import org.springframework.stereotype.Service;

@Service
public class LoanService {
    public LoanResponse calculateLoan(LoanRequest request) {
        return new LoanResponse();
    }
}
