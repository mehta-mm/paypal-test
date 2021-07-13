package com.paypal.bfs.test.employeeserv.api.impl;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.model.Employee;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

    @Autowired
    private EmployeeService employeeService;

    @Override
    public ResponseEntity<Employee> employeeGetById(String id) {
        return employeeService.getEmployeeById(Integer.parseInt(id));
    }

    @Override
    public ResponseEntity<String> createEmployee(Employee employee) {
        return employeeService.createEmployee(employee);
    }
}
