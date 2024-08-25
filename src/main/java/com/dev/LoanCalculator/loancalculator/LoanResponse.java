package com.dev.LoanCalculator.loancalculator;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanResponse {

    private Date date;
    private BigDecimal loanAmount;
    private BigDecimal outstandingBalance;
    private String consolidated;
    private BigDecimal total;
    private BigDecimal amortization;
    private BigDecimal balance;
    private BigDecimal provision;
    private BigDecimal accumulated;
    private BigDecimal paid;

}
