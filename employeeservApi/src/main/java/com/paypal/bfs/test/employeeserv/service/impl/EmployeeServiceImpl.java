package com.paypal.bfs.test.employeeserv.service.impl;

import com.paypal.bfs.test.employeeserv.model.Employee;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;
import org.springframework.http.ResponseEntity;

public class EmployeeServiceImpl implements EmployeeService {
    @Override
    public ResponseEntity<String> createEmployee(Employee employee) {
        return null;
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(Integer id) {
        return null;
    }
}
