package com.paypal.bfs.test.employeeService;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.impl.EmployeeResourceImpl;
import com.paypal.bfs.test.employeeserv.model.Address;
import com.paypal.bfs.test.employeeserv.model.Employee;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

public class EmployeeResourceFunctionalTest {

    private final EmployeeResource employeeResourceImpl = new EmployeeResourceImpl();
    @Mock
    private EmployeeService employeeService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void employeeGetByIdPositiveTest() {
        Employee employee = mockEmployee();
        Mockito.when(employeeService.getEmployeeById(Mockito.anyInt())).thenReturn(new ResponseEntity<>(employee, HttpStatus.OK));
        ReflectionTestUtils.setField(employeeResourceImpl, "employeeService", employeeService);

        ResponseEntity<Employee> employeeResponseEntity = employeeResourceImpl.employeeGetById("1");

        Assert.assertNotNull(employeeResponseEntity);
        Assert.assertEquals(HttpStatus.OK, employeeResponseEntity.getStatusCode());
        Assert.assertEquals(employee, employeeResponseEntity.getBody());
    }

    @Test
    public void employeeGetByIdNegativeIdNotPresent() {

        Mockito.when(employeeService.getEmployeeById(Mockito.anyInt())).thenReturn(new ResponseEntity<>(null, HttpStatus.OK));
        ReflectionTestUtils.setField(employeeResourceImpl, "employeeService", employeeService);

        ResponseEntity<Employee> employeeResponseEntity = employeeResourceImpl.employeeGetById("1");

        Assert.assertNotNull(employeeResponseEntity);
        Assert.assertEquals(HttpStatus.OK, employeeResponseEntity.getStatusCode());
        Assert.assertNull(employeeResponseEntity.getBody());
    }

    @Test
    public void createEmployeePositiveTest() {

        Mockito.when(employeeService.createEmployee(Mockito.any(Employee.class))).thenReturn(new ResponseEntity<>("Employee Created Successfully", HttpStatus.OK));
        ReflectionTestUtils.setField(employeeResourceImpl, "employeeService", employeeService);

        ResponseEntity<String> employeeResponseEntity = employeeResourceImpl.createEmployee(mockEmployee());

        Assert.assertNotNull(employeeResponseEntity);
        Assert.assertEquals(HttpStatus.OK, employeeResponseEntity.getStatusCode());
        Assert.assertEquals("Employee Created Successfully", employeeResponseEntity.getBody());
    }

    @Test
    public void createEmployeeNegativeTest() {

        Mockito.when(employeeService.createEmployee(Mockito.any(Employee.class))).thenReturn(new ResponseEntity<>("Employee was not created.", HttpStatus.BAD_REQUEST));
        ReflectionTestUtils.setField(employeeResourceImpl, "employeeService", employeeService);

        ResponseEntity<String> employeeResponseEntity = employeeResourceImpl.createEmployee(mockEmployee());

        Assert.assertEquals(HttpStatus.BAD_REQUEST, employeeResponseEntity.getStatusCode());
        Assert.assertEquals("Employee was not created.", employeeResponseEntity.getBody());
    }

    @Test
    public void createEmployeeNegativeTestWithBadRequest() {
        Employee employee = mockEmployee();
        employee.getAddress().setState(null);
        Mockito.when(employeeService.createEmployee(Mockito.any())).thenReturn(new ResponseEntity<>("Employee was not created.", HttpStatus.BAD_REQUEST));
        ReflectionTestUtils.setField(employeeResourceImpl, "employeeService", employeeService);

        ResponseEntity<String> employeeResponseEntity = employeeResourceImpl.createEmployee(employee);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, employeeResponseEntity.getStatusCode());
        Assert.assertEquals("Employee was not created.", employeeResponseEntity.getBody());
    }

    private Employee mockEmployee() {
        Address address = new Address();
        address.setLine1("Sector");
        address.setLine1("51");
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
