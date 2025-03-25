package com.world.web.service;

import com.world.web.model.Employee;
import org.springframework.messaging.Message;

import java.util.List;

public interface EmployeeService {

    public void getEmployeeName(Message<String> name);

    public Message<Employee> hireEmployee(Message<Employee> employee);

    public Message<String> processEmployeeStatus(Message<String> status);

    public List<Message<String>> splitMessages(Message<?> message);

    public boolean isEmployeeDesignationDeveloper(Message<?> designation);

    public String getEmployeeDepartment(Message<Employee> employeeInput);
}
