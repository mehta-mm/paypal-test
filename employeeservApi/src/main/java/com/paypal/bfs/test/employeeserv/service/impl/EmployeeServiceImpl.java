package com.paypal.bfs.test.employeeserv.service.impl;

import com.paypal.bfs.test.employeeserv.model.Employee;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static com.paypal.bfs.test.employeeserv.constant.EmployeeServiceConstant.*;

@Component
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    public ResponseEntity<String> createEmployee(Employee employee) {

        String message = validateEmployee(employee);
        if (message.length() > 1)
            return new ResponseEntity<>(EMPLOYEE_NOT_CREATED + message, HttpStatus.BAD_REQUEST);
        try {
            employeeRepository.save(employee);
            logger.info(EMPLOYEE_CREATED);
        } catch (Exception e) {
            logger.error(EMPLOYEE_NOT_CREATED, e.getMessage());
            return new ResponseEntity<>(EMPLOYEE_NOT_CREATED, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(EMPLOYEE_CREATED, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(Integer id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(null, HttpStatus.OK));
    }

    private String validateEmployee(Employee employee) {
        StringBuilder errorMessage = new StringBuilder();
        if (employee.getAddress() == null)
            errorMessage.append(ADDRESS_MISSING);
        if (StringUtils.isEmpty(employee.getAddress().getLine1()))
            errorMessage.append(ADDRESS_LINE_MISSING);
        if (StringUtils.isEmpty(employee.getAddress().getCity()))
            errorMessage.append(ADDRESS_CITY_MISSING);
        if (StringUtils.isEmpty(employee.getAddress().getState()))
            errorMessage.append(ADDRESS_STATE_MISSING);
        if (StringUtils.isEmpty(employee.getAddress().getCountry()))
            errorMessage.append(ADDRESS_COUNTRY_MISSING);
        if (StringUtils.isEmpty(employee.getAddress().getZipCode()))
            errorMessage.append(ADDRESS_ZIP_MISSING);
        return errorMessage.toString();
    }
}
