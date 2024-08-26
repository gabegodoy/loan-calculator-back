package com.dev.LoanCalculator.loancalculator;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanRequest {

    private LocalDate endDate;
    private LocalDate firstPaymentDate;
    private LocalDate initialDate;
    private BigDecimal interestRate;
    private BigDecimal loanAmount;

}
