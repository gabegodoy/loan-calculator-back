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
public class LoanResponse {
    private LocalDate date;
    @Builder.Default
    private BigDecimal loanAmount = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal outstandingBalance = BigDecimal.ZERO;
    private String consolidated;
    @Builder.Default
    private BigDecimal total = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal amortization = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal provision = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal accumulated = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal paid = BigDecimal.ZERO;

}
