package ru.ncedu.grishkova.cc.operators;

import ru.ncedu.grishkova.cc.Operator;



public class Factorial extends Operator {

    public Factorial() {
        binary = false;
    }
    @Override
    public double calculate(double leftOperand, double rightOperand) {
        double ret = 1.0;
        for (int i = 1; i <= leftOperand; ++i) {
            ret *= i;
        }
        return ret;
    }
}
