package com.world.web.controller;

import com.world.web.model.Employee;
import com.world.web.gateway.EmployeeGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/integration/v1")
public class EmployeeController {

    @Autowired
    private EmployeeGateway employeeGateway;


    /*Examples of Spring Integration Service Activator Components */
    /**
     * GET getEmployeeName method
     * @param name as input parameter
     * @return String
     */
    @GetMapping(value = "/employee/{name}")
    public String getEmployeeName(@PathVariable("name") String name) {
        return employeeGateway.getEmployeeName(name);
    }

    /**
     * POST hireEmployee method
     * @param employee object as input parameter
     * @return Employee object as a response
     */
    @PostMapping(value = "/hireEmployee")
    public Employee hireEmployee(@RequestBody Employee employee) {
        Message<Employee> messageReply = employeeGateway.hireEmployee(employee);
        return messageReply.getPayload();
    }

    /*Examples of Spring Integration Transformer Components */

    /**
     *
     * @param status as input request parameter
     * @return String as Employee Status
     */
    @GetMapping(value = "/processEmployeeStatus/{status}")
    public String processEmployeeStatus(@PathVariable("status") String status){
     return employeeGateway.processEmployeeStatus(status);
    }

    /*Examples of Spring Integration Splitter Components */

    /**
     * SPLITTER getManagersList method example
     * @param managers as input request parameter
     * @return String
     */
    @GetMapping(value="/employeeManagers/{managers}")
    public String getManagersList(@PathVariable("managers") String managers) {
      return employeeGateway.getManagersList(managers);
    }

    /*Examples of Spring Integration Filter Components */
    /**
     *
     * @param designation as input request parameter
     * @return String
     */
    @GetMapping(value = "/employeeDesignation/{designation}")
    public String isEmployeeDesignationDeveloper(@PathVariable("designation") String designation){
      return employeeGateway.isEmployeeDesignationDeveloper(designation);
    }

    /*Examples of Spring Integration Router Components */

    /**
     *
     * @param employeeInputRequest as input request parameter
     * @return String
     */
    @PostMapping(value = "/employeeDepartment")
    public String getEmployeeDepartment(@RequestBody Employee employeeInputRequest){
        return employeeGateway.getEmployeeDepartment(employeeInputRequest);
    }
}
