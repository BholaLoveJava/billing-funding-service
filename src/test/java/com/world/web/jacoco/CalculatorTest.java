package com.world.web.jacoco;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CalculatorTest {

  private static Calculator calculator;

  @BeforeAll
  static void setUp() {
    calculator = new Calculator();
  }

  @Test
  void addSimple() {
    double result = calculator.calculate(1, 1, '+');
    Assertions.assertEquals(2, result);
  }

  @Test
  void multiplySimple() {
    double result = calculator.calculate(1, 1, '*');
    Assertions.assertEquals(1, result);
  }

  @Test
  void subtractSimple() {
    double result = calculator.calculate(1, 1, '-');
    Assertions.assertEquals(0, result);
  }

  @Test
  void divideSimple() {
    double result = calculator.calculate(1, 1, ':');
    Assertions.assertEquals(1, result);
  }

  @Test
  void illegalArgument() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> calculator.calculate(1, 1, '/'));
  }

  @Test
  void divideByZero() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> calculator.calculate(1, 0, ':'));
  }
}
