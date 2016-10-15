package ru.ncedu.grishkova.cc.operators;

import ru.ncedu.grishkova.cc.Operator;

public class Multiplication extends Operator {
    @Override
    public double calculate(double leftOperand, double rightOperand) {
        return leftOperand*rightOperand;
    }
}
