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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Objects;
import java.util.Optional;

import static com.paypal.bfs.test.employeeserv.constant.EmployeeServiceConstant.*;

public class EmployeeServiceFunctionalTest {

    private final EmployeeService employeeResourceImpl = new EmployeeServiceImpl();
    @Mock
    private EmployeeRepository employeeRepository;
    private Employee employee;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        employee = mockEmployee();
    }

    @Test
    public void createEmployeePositiveTest() {
        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee);
        ReflectionTestUtils.setField(employeeResourceImpl, "employeeRepository", employeeRepository);
        ResponseEntity<String> response = employeeResourceImpl.createEmployee(mockEmployee());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals("Employee Created Successfully", response.getBody());
    }

    @Test
    public void createEmployeeNegativeTestAddressMissing() {
        Employee employee = mockEmployee();
        employee.setAddress(null);
        ResponseEntity<String> response = employeeResourceImpl.createEmployee(employee);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(Objects.requireNonNull(response.getBody()).contains(EMPLOYEE_NOT_CREATED));
        Assert.assertTrue(Objects.requireNonNull(response.getBody()).contains(ADDRESS_MISSING));
    }

    @Test
    public void createEmployeeNegativeTestLineMissing() {
        Employee employee = mockEmployee();
        employee.getAddress().setLine1(null);
        ResponseEntity<String> response = employeeResourceImpl.createEmployee(employee);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(Objects.requireNonNull(response.getBody()).contains(EMPLOYEE_NOT_CREATED));
        Assert.assertTrue(Objects.requireNonNull(response.getBody()).contains(ADDRESS_LINE_MISSING));
    }

    @Test
    public void createEmployeeNegativeTestCityMissing() {
        Employee employee = mockEmployee();
        employee.getAddress().setCity(null);
        ResponseEntity<String> response = employeeResourceImpl.createEmployee(employee);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(Objects.requireNonNull(response.getBody()).contains(EMPLOYEE_NOT_CREATED));
        Assert.assertTrue(Objects.requireNonNull(response.getBody()).contains(ADDRESS_CITY_MISSING));
    }

    @Test
    public void createEmployeeNegativeTestCountryMissing() {
        Employee employee = mockEmployee();
        employee.getAddress().setCountry(null);
        ResponseEntity<String> response = employeeResourceImpl.createEmployee(employee);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(Objects.requireNonNull(response.getBody()).contains(EMPLOYEE_NOT_CREATED));
        Assert.assertTrue(Objects.requireNonNull(response.getBody()).contains(ADDRESS_COUNTRY_MISSING));
    }

    @Test
    public void createEmployeeNegativeTestStateMissing() {
        Employee employee = mockEmployee();
        employee.getAddress().setState(null);
        ResponseEntity<String> response = employeeResourceImpl.createEmployee(employee);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(Objects.requireNonNull(response.getBody()).contains(EMPLOYEE_NOT_CREATED));
        Assert.assertTrue(Objects.requireNonNull(response.getBody()).contains(ADDRESS_STATE_MISSING));
    }

    @Test
    public void createEmployeeNegativeTestZipMissing() {
        Employee employee = mockEmployee();
        employee.getAddress().setZipCode(null);
        ResponseEntity<String> response = employeeResourceImpl.createEmployee(employee);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(Objects.requireNonNull(response.getBody()).contains(EMPLOYEE_NOT_CREATED));
        Assert.assertTrue(Objects.requireNonNull(response.getBody()).contains(ADDRESS_ZIP_MISSING));
    }

    @Test
    public void createEmployeeNegativeTestRepositoryThrowsException() {
        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenThrow(RuntimeException.class);
        ResponseEntity<String> response = employeeResourceImpl.createEmployee(employee);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertEquals(EMPLOYEE_NOT_CREATED, response.getBody());
    }

    @Test
    public void getEmployeeWhenEmployeeDoesNotExist() {
        Mockito.when(employeeRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.empty());
        ReflectionTestUtils.setField(employeeResourceImpl, "employeeRepository", employeeRepository);
        ResponseEntity<Employee> response = employeeResourceImpl.getEmployeeById(1);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNull(response.getBody());
    }

    @Test
    public void getEmployeeWhenEmployeeExists() {
        Mockito.when(employeeRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(employee));
        ReflectionTestUtils.setField(employeeResourceImpl, "employeeRepository", employeeRepository);
        ResponseEntity<Employee> response = employeeResourceImpl.getEmployeeById(1);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(employee, response.getBody());
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
