package com.dev.LoanCalculator.loancalculator;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanRequest {

    private Date endDate;
    private Date firstPaymentDate;
    private Date initialDate;
    private double interestRate;
    private double loanAmount;

}
