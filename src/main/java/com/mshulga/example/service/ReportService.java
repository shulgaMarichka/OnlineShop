package com.mshulga.example.service;

import com.mshulga.example.dao.impl.OrderDao;
import com.mshulga.example.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private OrderDao orderDao;

    public Map<String, BigDecimal> getOrderAmountByDay() {
        List<Order> orders = orderDao.getAll();
        Map<String, BigDecimal> result = new HashMap<>();

        orders.stream().forEach(order -> {
            String date = formatter.format(order.getOrderDate());

            BigDecimal prevAmount = result.get(date);
            result.put(date, (null != prevAmount) ?
                    prevAmount.add(order.getTotalBill()) :
                new BigDecimal(0));
        });

        return result;
    }

}
