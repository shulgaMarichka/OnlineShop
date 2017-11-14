package com.mshulga.example.controller;

import com.mshulga.example.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping("/statistics/daily-reports")
public class ReportController {

    private static final Logger LOG = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private ReportService service;

    @GetMapping("")
    public ResponseEntity<Map> getOrderItem() {
        Map<String, BigDecimal> aggregatedOrders = service.getOrderAmountByDay();
        LOG.info("Daily reports result contains {} aggregate items.", aggregatedOrders.size());
        return new ResponseEntity<>(aggregatedOrders, HttpStatus.OK);
    }


}
