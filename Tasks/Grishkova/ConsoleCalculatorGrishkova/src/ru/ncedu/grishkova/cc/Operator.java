package ru.ncedu.grishkova.cc;

/**
 * The class for any binary or unary mathematical function
 */
public abstract class Operator {
    /**
     * Performs calculating with given arguments. If function is unary, method must use only
     * leftOperand for definition,
     * @param leftOperand first argument
     * @param rightOperand second argument
     * @return the result of calculating.
     */
    public abstract double calculate(double leftOperand, double rightOperand) ;

    /**
     * Shows is function binary or unary
     */
    protected boolean binary = true;

    public boolean isBinary() {
        return binary;
    }
}
