package ru.ncedu.grishkova.cc.operators;

import ru.ncedu.grishkova.cc.Operator;

public class Sqrt extends Operator {
    public Sqrt() {
        binary = false;
    }

    @Override
    public double calculate(double leftOperand, double rightOperand) {
        return Math.sqrt(leftOperand);
    }
}
