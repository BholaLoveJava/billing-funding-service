package com.world.web.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.world.web.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class JsonUtility {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtility.class);
    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * <a href="https://www.baeldung.com/jackson-object-mapper-tutorial">...</a>
     * @param args as input parameter
     */
    public static void main(String[] args) {
        JsonUtility jsonUtility = new JsonUtility();
        /* Convert Object to JSON String */
        System.out.println(jsonUtility.convertObjectToString());

        /* Convert Object to Byte Array*/
        System.out.println(Arrays.toString(jsonUtility.convertObjectToByteArray()));

        /* Convert List of JSON String to List Employee Object */
       String employeesListJsonString =  "[{\"id\":1111,\"name\":\"Bhola Kumar\",\"status\":\"Active\",\"department\":\"IT\",\"designation\":\"Software Engineer\"}," +
               "{\"id\":2222,\"name\":\"Rahul Kumar\",\"status\":\"Active\",\"department\":\"Sales\",\"designation\":\"Sales Lead\"}," +
               "{\"id\":3333,\"name\":\"Kamlesh Kumar\",\"status\":\"Inactive\",\"department\":\"Finance\",\"designation\":\"Finance Lead\"}," +
               "{\"id\":4444,\"name\":\"Saroj Kumar\",\"status\":\"Active\",\"department\":\"Human Resource\",\"designation\":\"Senior Recruiter\"}]";
       System.out.println(jsonUtility.convertJsonStringToObject(employeesListJsonString));
    }

    /**
     * getEmployeesData method returns Employees Data
     * @return List<Employee>
     */
    public List<Employee> getEmployeesData() {
        return List.of(new Employee(1111, "Bhola Kumar", "Active", "IT", "Software Engineer"),
                new Employee(2222, "Rahul Kumar", "Active", "Sales", "Sales Lead"),
                new Employee(3333, "Kamlesh Kumar", "Inactive", "Finance", "Finance Lead"),
                new Employee(4444, "Saroj Kumar", "Active", "Human Resource", "Senior Recruiter"));
    }

    /**
     * convertObjectToString method converts List of Employee Object to JSON String
     * @return String
     */
    public  String convertObjectToString(){
        List<Employee> employeesList = getEmployeesData();
        String jsonString = null;
        try {
            jsonString  = objectMapper.writeValueAsString(employeesList);
        } catch (JsonProcessingException exp) {
            logger.error("Exception caught : {}", exp.getMessage(), exp);
        }
        return jsonString;
    }

    /**
     * convertObjectToByteArray method converts the List of Employee Object to byte[]
     * @return byte[]
     */
    public byte[] convertObjectToByteArray() {
        List<Employee> employeeList = getEmployeesData();
        byte[] byteArrayResult = null;
        try {
         byteArrayResult = objectMapper.writeValueAsBytes(employeeList);
        }catch(JsonProcessingException exp){
            logger.error("Exception caught : {}", exp.getMessage(), exp);
        }
        return byteArrayResult;
    }

    /**
     * convertJsonStringToObject method converts JSON String to List<Employee>
     * @param employeesListJsonString as input parameter
     * @return List<Employee>
     */
    public List<Employee> convertJsonStringToObject(String employeesListJsonString){
        List<Employee> employeeListObject = null;
        try {
          employeeListObject =   objectMapper.readValue(employeesListJsonString, new TypeReference<List<Employee>>() {});
        }catch(JsonProcessingException exp){
            logger.error("Exception caught : {}", exp.getMessage(), exp);
        }
        return employeeListObject;
    }
}
