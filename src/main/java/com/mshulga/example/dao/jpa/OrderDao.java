package com.mshulga.example.dao.jpa;

import com.mshulga.example.model.DailyReportResult;
import com.mshulga.example.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
public interface OrderDao extends CrudRepository<Order, Long> {


    @Query("SELECT new com.mshulga.example.model.DailyReportResult(to_char(o.orderDate, 'YYYY-MM-DD'), SUM(o.totalBill)) " +
            "FROM Order o GROUP BY to_char(o.orderDate, 'YYYY-MM-DD')")
    List<DailyReportResult> getOrderAmountByDay();

}
