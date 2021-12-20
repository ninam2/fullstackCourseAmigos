package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DemoApplicationTests {

    Calculator calculator = new Calculator();

    @Test
    void itShouldAddNumbers() {
        int givenNumberOne = 20;
        int givenNumberTwo = 30;
        int expectedResult = 50;

        int result = calculator.add(givenNumberOne, givenNumberTwo);

        assertThat(result).isEqualTo(expectedResult);
    }

    class Calculator {

        int add(int a, int b) {
            return a + b;
        }
    }
}
