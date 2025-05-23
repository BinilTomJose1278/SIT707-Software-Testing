package web.service;

public class MathQuestionService {

    /**
     * Calculate Q1 result: Addition
     */
    public static double q1Addition(String number1, String number2) throws IllegalArgumentException {
        try {
            double num1 = Double.parseDouble(number1);
            double num2 = Double.parseDouble(number2);
            return num1 + num2;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input for addition.");
        }
    }

    /**
     * Calculate Q2 result: Subtraction
     */
    public static double q2Subtraction(String number1, String number2) throws IllegalArgumentException {
        try {
            double num1 = Double.parseDouble(number1);
            double num2 = Double.parseDouble(number2);
            return num1 - num2;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input for subtraction.");
        }
    }

    /**
     * Calculate Q3 result: Multiplication
     */
    public static double q3Multiplication(String number1, String number2) throws IllegalArgumentException {
        try {
            double num1 = Double.parseDouble(number1);
            double num2 = Double.parseDouble(number2);
            return num1 * num2;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input for multiplication.");
        }
    }
}
