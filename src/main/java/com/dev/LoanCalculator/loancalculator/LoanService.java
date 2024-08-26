package com.dev.LoanCalculator.loancalculator;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import static java.lang.Math.exp;

@Service
public class LoanService {
    public List<LoanResponse> calculateLoan (LoanRequest request) {
        LocalDate initialDate = request.getInitialDate ();
        LocalDate firstPaymentDate = request.getFirstPaymentDate ();
        LocalDate endDate = request.getEndDate ();
        BigDecimal loanAmount = request.getLoanAmount ();
        BigDecimal interestRate = request.getInterestRate ().divide (BigDecimal.valueOf (100), 4, RoundingMode.FLOOR);

        int totalInstallments = calculateTotalInstallments (firstPaymentDate, endDate); // OK RETURNING AMOUNT OF INSTALMENTS
        int daysBase = 360;

        List<LoanResponse> installments = new ArrayList<> ();

        LoanResponse first = LoanResponse.builder ()
                .date (request.getInitialDate ())
                .loanAmount (request.getLoanAmount ())
                .outstandingBalance (request.getLoanAmount ())
                .balance (request.getLoanAmount ())
                .build ();
        installments.add (first);

        LoanResponse last = first;
        LocalDate nextPaymentDate = request.getFirstPaymentDate ();

        for (int i = 0; i < totalInstallments; i++) {

            if (last.getDate ().getMonthValue () != nextPaymentDate.getMonthValue ()) {
                BigDecimal provision = calculateProvision (last, getEndOfMonth (last.getDate ()), interestRate, daysBase);
                BigDecimal paid = BigDecimal.ZERO;
                BigDecimal accumulated = provision;
                BigDecimal outstandingBal = last.getBalance ().add (accumulated);

                LoanResponse interestRow = LoanResponse.builder ()
                        .date (getEndOfMonth (last.getDate ()))
                        .outstandingBalance (outstandingBal)
                        .balance (last.getBalance ())
                        .provision (provision)
                        .accumulated (accumulated)
                        .build ();

                installments.add (interestRow);
                last = interestRow;
            }

            BigDecimal provision = calculateProvision (last, nextPaymentDate, interestRate, daysBase);
            BigDecimal paid = last.getAccumulated ().add (provision);
            BigDecimal accumulated = calculateAccumulated (last, provision, paid);
            BigDecimal amortization = calculateAmortization(totalInstallments, loanAmount);
            BigDecimal balance = last.getBalance ().subtract (amortization);
            BigDecimal outstandingBal = balance.add (accumulated);

            LoanResponse installmentRow = LoanResponse.builder ()
                    .date (nextPaymentDate)
                    .outstandingBalance (outstandingBal)
                    .consolidated (i+1 + "/" + totalInstallments)
                    .total (amortization.add(paid))
                    .amortization (amortization)
                    .balance (balance)
                    .provision (provision)
                    .accumulated (accumulated)
                    .paid (paid)
                    .build ();

            installments.add (installmentRow);
            last = installmentRow;
            nextPaymentDate = nextPaymentDate.plusMonths (1);
        }

        return installments;

    }

    private BigDecimal calculateAmortization (int totalInstallments, BigDecimal loanAmount) {
        return loanAmount.divide (BigDecimal.valueOf (totalInstallments), 4, RoundingMode.FLOOR);
    }

    private BigDecimal calculateProvision (LoanResponse last, LocalDate current, BigDecimal interestRate, int daysBase) {

        BigDecimal daysBetween = BigDecimal.valueOf (ChronoUnit.DAYS.between (last.getDate (), current));

        BigDecimal exponent = daysBetween.divide(BigDecimal.valueOf(daysBase), 4 ,RoundingMode.FLOOR);
        BigDecimal pow = BigDecimal.valueOf(Math.pow(interestRate.add(BigDecimal.ONE).doubleValue(), exponent.doubleValue())).subtract(BigDecimal.ONE);


        BigDecimal balancePlusAccumulated = last.getBalance ().add (last.getAccumulated ());

        return pow.multiply (balancePlusAccumulated).setScale (4, RoundingMode.FLOOR);

    }

    private BigDecimal calculateAccumulated (LoanResponse last, BigDecimal provision, BigDecimal paid) {
        return last.getAccumulated ().add (provision).subtract (paid);
    }

    private LocalDate getEndOfMonth (LocalDate date) {
        return date.withDayOfMonth (date.lengthOfMonth ());
    }

    private int calculateTotalInstallments (LocalDate firstPaymentDate, LocalDate endDate) {
        return (int) ChronoUnit.MONTHS.between (firstPaymentDate.withDayOfMonth (1), endDate.withDayOfMonth (1)) + 1;
    }

}
