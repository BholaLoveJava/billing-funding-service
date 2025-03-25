package com.world.web.gateway;

import com.world.web.model.Employee;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

@MessagingGateway
public interface EmployeeGateway {

    /*Spring Integration Service Activator Components */
    /**
     * GET getEmployeeName method example
     * @param name as input parameter
     * @return String as result
     */
    @Gateway(requestChannel = "request-emp-name-channel")
    public String getEmployeeName(String name);

    /**
     * POST hireEmployee method Example
     * @param employee as input parameter
     * @return Message<Employee>
     */
    @Gateway(requestChannel = "request-hire-emp-channel")
    public Message<Employee> hireEmployee(Employee employee);

    /*Spring Integration Transformer Component */
    /**
     *
     * @param status as input parameter
     * @return String as result
     */
    @Gateway(requestChannel = "emp-status-input-channel")
    public String processEmployeeStatus(String status);

    /*Spring Integration Splitter Components Example*/

    /**
     *
     * @param managers as input parameter
     * @return String
     */
    @Gateway(requestChannel = "emp-managers-channel")
    public String getManagersList(String managers);

    /*Spring Integration Filter Components Example */
    /**
     *
     * @param designation as input parameter
     * @return String
     */
    @Gateway(requestChannel = "emp-developer-channel")
    public String isEmployeeDesignationDeveloper(String designation);

    /*Spring Integration Router Components Example */

    /**
     *
     * @param employeeInputRequest as input parameter
     * @return String
     */
    @Gateway(requestChannel = "emp-department-channel")
    public String getEmployeeDepartment(Employee employeeInputRequest);
}
