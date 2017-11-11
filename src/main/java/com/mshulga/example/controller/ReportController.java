package com.mshulga.example.controller;

import com.mshulga.example.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/statistics/daily-reports")
public class ReportController {

    @Autowired
    private ReportService service;

    @GetMapping("")
    public ResponseEntity<Map> getOrderItem() {
        Map aggregatedOrders = service.getOrderAmountByDay();
        return new ResponseEntity<>(aggregatedOrders, HttpStatus.OK);
    }


}
