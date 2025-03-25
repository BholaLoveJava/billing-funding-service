package com.world.web.serviceImpl;

import com.world.web.constants.EmployeeConstants;
import com.world.web.model.Employee;
import com.world.web.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.*;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    /**
     * GET getEmployeeName method example
     *
     * @param name as input parameter
     *             The request goes form EmployeeGateway(request channel) to EmployeeService(input channel)
     *             using this @ServiceActivator annotation
     *             ServiceActivator is the endpoint type for connecting, any spring managed object to an input channel.
     *             So, that it may play the role of the Service
     */
    @ServiceActivator(inputChannel = "request-emp-name-channel")
    @Override
    public void getEmployeeName(Message<String> name) {
        logger.info("Request received for request-emp-name-channel :: {}", name.getPayload());
        MessageChannel replyChannel = (MessageChannel) name.getHeaders().getReplyChannel();
        assert replyChannel != null;
        replyChannel.send(name);
    }

    /**
     * POST hireEmployee method example
     * This method process the input request received on inputChannel and writes the response to outputChannel
     * for further processing
     *
     * @param employee as input parameter
     * @return Message<Employee>
     */
    @ServiceActivator(inputChannel = "request-hire-emp-channel", outputChannel = "process-emp-channel")
    @Override
    public Message<Employee> hireEmployee(Message<Employee> employee) {
        logger.info("Request received for request-hire-emp-channel :: {}", employee.getPayload());
        return employee;
    }

    /**
     * processEmployee method process the request received on inputChannel and post the response to outputChannel
     *
     * @param employee as input parameter
     * @return Message<Employee>
     */
    @ServiceActivator(inputChannel = "process-emp-channel", outputChannel = "get-emp-status-channel")
    public Message<Employee> processEmployee(Message<Employee> employee) {
        logger.info("Request received for process-emp-channel :: {}", employee.getPayload());
        employee.getPayload().setStatus(EmployeeConstants.PERMANENT_ROLE);
        logger.info("Employee payload status updated :: {}", employee.getPayload().getStatus());
        return employee;
    }

    /**
     * This getEmployeeStatus method process the input request, received on inputChannel, and send the response as
     * message using MessageChannel
     *
     * @param employee as input parameter
     */
    @ServiceActivator(inputChannel = "get-emp-status-channel")
    public void getEmployeeStatus(Message<Employee> employee) {
        logger.info("Request received for get-emp-status-channel :: {}", employee.getPayload());
        MessageChannel replyChannel = (MessageChannel) employee.getHeaders().getReplyChannel();
        assert replyChannel != null;
        replyChannel.send(employee);
    }

    /*Spring Integration Transformer Components*/

    /**
     * @param status as input parameter
     * @return Message<String>
     * Transformer takes a message from a channel and creates a new message containing converted payload or
     * message structure
     */
    @Transformer(inputChannel = "emp-status-input-channel", outputChannel = "emp-status-output-channel")
    @Override
    public Message<String> processEmployeeStatus(Message<String> status) {
        logger.info("Request received for emp-status-input-channel :: {}", status.getPayload());
        String statusData = status.getPayload();
        Message<String> statusInUpperCase;
        statusInUpperCase = MessageBuilder.withPayload(statusData.toUpperCase())
                .copyHeaders(status.getHeaders())
                .build();
        logger.info("Request payload transformed :: {} for emp-status-input-channel", status.getPayload());
        return statusInUpperCase;
    }

    /*common emp-status-output-channel using ServiceActivator */

    /**
     * @param status as input parameter
     */
    @ServiceActivator(inputChannel = "emp-status-output-channel")
    public void getEmployeeUpdatedStatus(Message<String> status) {
        logger.info("Request received for emp-status-output-channel :: {}", status.getPayload());
        MessageChannel replyChannel = (MessageChannel) status.getHeaders().getReplyChannel();
        assert replyChannel != null;
        replyChannel.send(status);
    }

    /*Spring Integration Splitter Components */
    @Splitter(inputChannel = "emp-managers-channel", outputChannel = "emp-agg-managers-channel")
    @Override
    public List<Message<String>> splitMessages(Message<?> message) {
        logger.info("Request received for emp-managers-channel :: {}", message.getPayload());
        List<Message<String>> messages = new ArrayList<>();
        String[] splittedMessage = message.getPayload().toString().split(",");
        Arrays.stream(splittedMessage).forEach(data -> {
            Message<String> messageData =
                    MessageBuilder.withPayload(data)
                            .copyHeaders(message.getHeaders()).build();
            messages.add(messageData);
        });
        return messages;
    }

    /*Spring Integration Aggregator Components */

    /**
     * @param messages as input parameter
     * @return Message<String>
     */
    @Aggregator(inputChannel = "emp-agg-managers-channel", outputChannel = "emp-output-channel")
    public Message<String> aggregatorMessage(List<Message<String>> messages) {
        logger.info("Request received for emp-agg-managers-channel :: {}", messages);
        Message<String> messageData;
        StringJoiner joiner = new StringJoiner("&", "[", "]");
        messages.forEach(data -> {
            String managersName = data.getPayload();
            joiner.add(managersName);
        });
        //Now prepare the response data
        messageData = MessageBuilder.withPayload(joiner.toString()).build();
        return messageData;
    }

    /*Spring Integration Filter Components Example */

    /**
     * @param designation as input parameter
     * @return boolean
     */
    @Filter(inputChannel = "emp-developer-channel", outputChannel = "emp-output-channel", discardChannel = "emp-discard-channel")
    @Override
    public boolean isEmployeeDesignationDeveloper(Message<?> designation) {
        logger.info("Request received for emp-developer-channel :: {}", designation.getPayload());
        String employeeDesignation = designation.getPayload().toString();
        return employeeDesignation.contains(EmployeeConstants.DEVELOPER_ROLE);
    }


    /*Spring Integration Router Components Example */

    /**
     * @param employeeInput as input parameter
     * @return String
     */
    @Router(inputChannel = "emp-department-channel")
    @Override
    public String getEmployeeDepartment(Message<Employee> employeeInput) {
        String employeeDepartment = employeeInput.getPayload().getDepartment();
        String routerChannel = null;
        switch (employeeDepartment) {
            case "Sales":
                routerChannel = "emp-sales-channel";
                break;
            case "Marketing":
                routerChannel = "emp-marketing-channel";
                break;
            case "Finance":
                routerChannel = "emp-finance-channel";
                break;
            case "IT Support":
                routerChannel = "emp-support-channel";
                break;
            default:
                routerChannel = "emp-output-channel";
                break;
        }
        return routerChannel;
    }

    /**
     * @param employeeData as input parameter
     */
    @ServiceActivator(inputChannel = "emp-sales-channel")
    public void getSalesDepartment(Message<Employee> employeeData) {
        Message<String> salesDepartment = MessageBuilder.withPayload("SALES DEPARTMENT").build();
        MessageChannel replyChannel = (MessageChannel) employeeData.getHeaders().getReplyChannel();
        assert replyChannel != null;
        replyChannel.send(salesDepartment);
    }

    /**
     * @param employeeData as input parameter
     */
    @ServiceActivator(inputChannel = "emp-marketing-channel")
    public void getMarketingDepartment(Message<Employee> employeeData) {
        Message<String> marketingDepartment = MessageBuilder.withPayload("MARKETING DEPARTMENT").build();
        MessageChannel replyChannel = (MessageChannel) employeeData.getHeaders().getReplyChannel();
        assert replyChannel != null;
        replyChannel.send(marketingDepartment);
    }

    /**
     * @param employeeData as input parameter
     */
    @ServiceActivator(inputChannel = "emp-finance-channel")
    public void getFinanceDepartment(Message<Employee> employeeData) {
        Message<String> financeDepartment = MessageBuilder.withPayload("FINANCE DEPARTMENT").build();
        MessageChannel replyChannel = (MessageChannel) employeeData.getHeaders().getReplyChannel();
        assert replyChannel != null;
        replyChannel.send(financeDepartment);
    }

    /**
     * @param employeeData as input parameter
     */
    @ServiceActivator(inputChannel = "emp-support-channel")
    public void getITSupportDepartment(Message<Employee> employeeData) {
        Message<String> itSupportDepartment = MessageBuilder.withPayload("IT SUPPORT DEPARTMENT").build();
        MessageChannel replyChannel = (MessageChannel) employeeData.getHeaders().getReplyChannel();
        assert replyChannel != null;
        replyChannel.send(itSupportDepartment);
    }


    /**
     * Common output channel for Employees services
     *
     * @param data as input parameter
     */
    @ServiceActivator(inputChannel = "emp-output-channel")
    public void processEmployeeData(Message<String> data) {
        logger.info("Request received for emp-output-channel :: {}", data.getPayload());
        MessageChannel replyChannel = (MessageChannel) data.getHeaders().getReplyChannel();
        assert replyChannel != null;
        replyChannel.send(data);
    }

    /**
     * common discard channel for Employees services
     *
     * @param data as input parameter
     */
    @ServiceActivator(inputChannel = "emp-discard-channel")
    public void processDiscardEmployeeData(Message<String> data) {
        logger.info("Request received for emp-discard-channel :: {}", data.getPayload());
        MessageChannel replyChannel = (MessageChannel) data.getHeaders().getReplyChannel();
        assert replyChannel != null;
        replyChannel.send(data);
    }

    public void errorChannel(){

    }
}
