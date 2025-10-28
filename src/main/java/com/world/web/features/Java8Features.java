package com.world.web.features;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

record Address(String details, String street, String pinCode, String city, String state) {
}

record User(String name, String mobileNo, String email, Address address) {
}

class ValidationFailedException extends Exception {

    public ValidationFailedException() {
        super();
    }
    public ValidationFailedException(String message){
        super(message);
    }
}
public class Java8Features {

    public static void main(String[] args) {
        optionalFeatures();
    }

    /**
     *
     */
    public static void optionalFeatures() {
        Optional<String> optional = Optional.empty();
        optional.ifPresent(System.out::println);

        String str = "value";
        Optional<String> optionalData = Optional.of(str);
        optionalData.ifPresent(System.out::println);

        Optional<String> optionalS = Optional.ofNullable(getData());
        optionalS.ifPresent(System.out::println);

        List<String> listOpt = Optional.of(getList()).orElseGet(ArrayList::new);
        System.out.println("List data :: " + listOpt);

        /*
         * we used the map() method to convert results of calling the getAddress() to the Optional<Address> and
         * getStreet() to Optional<String>.
         * If any of these methods returned null, the map() method would return an empty Optional.
         */
        Optional<User> user = Optional.of(getUserDetails());
        String street = user.map(User::address)
                            .map(Address::street)
                            .orElse("not specified");
        System.out.println("User Street name :: "+street);

        //Another use case of Optional is changing NPE with another exception.
        String value = null;
        String result = "";
        try {
            result = value.toUpperCase();
            System.out.println("Result data :: "+result);
        } catch (NullPointerException exp) {
            System.out.println("Exception message :: " + exp.getMessage());
        }

        try {
            String valueData = null;
            Optional<String> valueOpt = Optional.ofNullable(value);
            String resultData = valueOpt.orElseThrow(() -> new ValidationFailedException("String is null")).toUpperCase();
            System.out.println("Result data :: "+resultData);
        } catch (ValidationFailedException exp) {
            System.out.println("Exception message :: " + exp.getMessage());
        }
    }

    public static String getData() {
        return null;
    }

    public static List<String> getList() {
        return List.of("Bhola", "Rohit", "suman", "sonam");
    }

    /**
     * @return User
     */
    public static User getUserDetails() {
        Address address =
                new Address("Manyata Tech Park", "Rachenahalli, Thanisandra Main Road", "560077", "Bengaluru", "Karnataka");
        return new User("Bhola Kumar", "8287910207", "bhola.kumar1990@gmail.com", address);
    }
}

/*
 * https://www.baeldung.com/java-8-new-features
 * Interface Default and Static Methods
 * Before Java 8, interfaces could have only public abstract methods. It was not possible to add new functionality to the existing
 * interface without forcing all implementing classes to create an implementation of the new methods,
 * nor was it possible to create interface methods with an implementation.
 * Starting with Java 8, interfaces can have static and default methods that, despite being declared in an interface, have a defined behavior.
 * Default Methods
 * Default methods are declared using the new default keyword. These are accessible through the instance of the implementing class and can be overridden.
 * Static Methods
 * It can’t be overridden by an implementing class.
 *
 * Method References
 * Method reference can be used as a shorter and more readable alternative for a lambda expression that
 * only calls an existing method.
 * There are four variants of method references.
 *  1.Reference to a Static Method -> ContainingClass::methodName
 *    eg. boolean isReal = list.stream().anyMatch(User::isRealUser);
 *  2.Reference to an Instance Method -> containingInstance::methodName
 *    eg. User user = new User();
          boolean isLegalName = list.stream().anyMatch(user::isLegalName);
 *  3.Reference to an Instance Method of an Object of a Particular Type -> ContainingType::methodName
 *   eg. list.stream().filter(String::isEmpty).count();
 *  4.Reference to a Constructor -> ClassName::new
 *   eg. Stream<User> stream = list.stream().map(User::new)
 */