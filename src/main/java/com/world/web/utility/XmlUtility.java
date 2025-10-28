package com.world.web.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.world.web.model.Employee;

public class XmlUtility {

    private static final XmlMapper xmlMapper = new XmlMapper();

    public static void main(String[] args) {
        Employee employee =
                new Employee(123, "Bhola Kumar", "Active", "Finance", "Head of Finance");

        convertJavaObjectXml(employee);

        convertXmlStringToJavaObject();
    }

    /**
     *
     * @param employee as input parameter
     * Marshalling -> Java object to xml conversion
     */
    public static void convertJavaObjectXml(Employee employee) {
        try {
            String xmlString = xmlMapper.writeValueAsString(employee);
            System.out.println(xmlString);
        }catch(JsonProcessingException exp) {
            System.out.println("Exception message :: "+exp.getMessage());
        }
    }


    /**
     * convertXmlStringToJavaObject method converts xml String to java object
     * UnMarshalling -> xml to java object conversion
     */
    public static void convertXmlStringToJavaObject() {
        String xmlString = """
                <Employee>
                   <id>123</id>
                   <name>Bhola Kumar</name>
                   <status>Active</status>
                   <department>Finance</department>
                   <designation>Head of Finance</designation>
                </Employee>
                """;
        try {
           Employee employeeObj =  xmlMapper.readValue(xmlString, Employee.class);
           System.out.println(employeeObj);
        } catch(JsonProcessingException exp) {
            System.out.println("Exception message :: "+exp.getMessage());
        }
    }
}
