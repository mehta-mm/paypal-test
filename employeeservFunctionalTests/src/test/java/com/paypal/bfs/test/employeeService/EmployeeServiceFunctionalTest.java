package com.paypal.bfs.test.employeeService;

import com.paypal.bfs.test.employeeserv.model.Address;
import com.paypal.bfs.test.employeeserv.model.Employee;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;
import com.paypal.bfs.test.employeeserv.service.impl.EmployeeServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class EmployeeServiceFunctionalTest {

    private final EmployeeService employeeResourceImpl = new EmployeeServiceImpl();
    @Mock
    private EmployeeRepository employeeRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createEmployeePositiveTest() {
        ResponseEntity<String> response = employeeResourceImpl.createEmployee(mockEmployee());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals("Employee Created Successfully", response.getBody());
    }

    @Test
    public void createEmployeeNegativeTest() {
        Employee employee = mockEmployee();
        employee.setAddress(null);
        ResponseEntity<String> response = employeeResourceImpl.createEmployee(mockEmployee());
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertEquals("Employee Not Created", response.getBody());
    }

    @Test
    public void getEmployeeWhenEmployeeDoesNotExist() {
        ResponseEntity<Employee> response = employeeResourceImpl.getEmployeeById(1);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNull(response.getBody());
    }

    @Test
    public void getEmployeeWhenEmployeeExists() {
        ResponseEntity<Employee> response = employeeResourceImpl.getEmployeeById(1);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(mockEmployee(), response.getBody());
    }

    private Employee mockEmployee() {
        Address address = new Address();
        address.setLine1("Sector 51");
        address.setCity("Gurgaon");
        address.setZipCode("122003");
        address.setState("Haryana");
        address.setCountry("India");
        Employee employee = new Employee();
        employee.setFirstName("Mohit");
        employee.setLastName("Mehta");
        employee.setDateOfBirth("19960428");
        employee.setAddress(address);
        return employee;
    }
}
