package com.mshulga.example.model;

import java.math.BigDecimal;

public class DailyReportResult {
    private String date;
    private BigDecimal sum;

    public DailyReportResult(String date, BigDecimal sum) {
        this.date = date;
        this.sum = sum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }
}
