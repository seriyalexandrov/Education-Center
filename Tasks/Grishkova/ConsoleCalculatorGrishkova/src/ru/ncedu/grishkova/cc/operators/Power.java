package ru.ncedu.grishkova.cc.operators;

import ru.ncedu.grishkova.cc.Operator;

public class Power extends Operator {
    @Override
    public double calculate(double leftOperand, double rightOperand) {
        return Math.pow(leftOperand, rightOperand);
    }
}
