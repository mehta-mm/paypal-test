package com.paypal.bfs.test.employeeserv.service;

import com.paypal.bfs.test.employeeserv.model.Employee;
import org.springframework.http.ResponseEntity;

public interface EmployeeService {
    ResponseEntity<String> createEmployee(Employee employee);

    ResponseEntity<Employee> getEmployeeById(Integer id);
}
