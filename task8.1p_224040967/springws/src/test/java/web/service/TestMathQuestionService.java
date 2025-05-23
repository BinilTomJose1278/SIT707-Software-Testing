package web.service;

import org.junit.Assert;
import org.junit.Test;

public class TestMathQuestionService {

    // Valid test for addition
    @Test
    public void testTrueAdd() {
        Assert.assertEquals(3.0, MathQuestionService.q1Addition("1", "2"), 0.001);
    }

    // Invalid input (empty number1) should throw an exception
    @Test(expected = IllegalArgumentException.class)
    public void testAddNumber1Empty() {
        MathQuestionService.q1Addition("", "2");
    }

    // Valid subtraction
    @Test
    public void testTrueSubtract() {
        Assert.assertEquals(2.0, MathQuestionService.q2Subtraction("5", "3"), 0.001);
    }

    // Invalid input (non-number string) should throw exception
    @Test(expected = IllegalArgumentException.class)
    public void testSubtractInvalid() {
        MathQuestionService.q2Subtraction("abc", "3");
    }

    // Valid multiplication
    @Test
    public void testTrueMultiply() {
        Assert.assertEquals(42.0, MathQuestionService.q3Multiplication("6", "7"), 0.001);
    }

    // Invalid input for multiplication
    @Test(expected = IllegalArgumentException.class)
    public void testMultiplyInvalid() {
        MathQuestionService.q3Multiplication("x", "7");
    }
}
