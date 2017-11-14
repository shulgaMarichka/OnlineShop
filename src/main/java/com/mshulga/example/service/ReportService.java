package com.mshulga.example.service;

import com.mshulga.example.dao.jpa.OrderDao;
import com.mshulga.example.model.DailyReportResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private OrderDao orderDao;

    public Map<String, BigDecimal> getOrderAmountByDay() {
        List<DailyReportResult> dailyReport = orderDao.getOrderAmountByDay();
        return dailyReport.stream().collect(Collectors.toMap(DailyReportResult::getDate, DailyReportResult::getSum));
    }

}
