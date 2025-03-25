package com.world.web.utility;

import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VavrUtility {

    public static final Logger logger = LoggerFactory.getLogger(VavrUtility.class);
    public static void main(String[] args) {
        //Try.of() -> Returns success or Failure results
        Try<Integer> result = Try.of(() -> VavrUtility.methodThrowsException(50, 10));
        //Handling success
        result.onSuccess(response -> {
          logger.info("Computation Result :: {}", response);
        });
        //Handling failures
        result.onFailure(exp -> {
           logger.error("Exception message :: {}", exp.getMessage());
        });

        //Try.run() -> Which returns void or may throw exceptions
        Try.run(() -> VavrUtility.badComputationMethod(50, 10))
                .onSuccess(response -> {
                    logger.info("Method execution completed Successfully");
                }).onFailure(throwable -> {
           logger.error("Exception message :: {}", throwable.getMessage());
        });
    }

    /**
     * @param number1 as input parameter
     * @param number2 as input parameter
     * @return int result
     * <a href="https://www.baeldung.com/vavr">
     */
    public static int methodThrowsException(int number1, int number2) {
        if(number2 <= 0) {
           throw new IllegalArgumentException("Number must be greater than Zero");
        }
        return number1 / number2;
    }

    /**
     * @param dividend as input parameter
     * @param divisor as input parameter
     */
    public static void badComputationMethod(int dividend, int divisor) {
        int result = dividend / divisor;
        logger.info("Computation result :: {}",result);
    }
}
