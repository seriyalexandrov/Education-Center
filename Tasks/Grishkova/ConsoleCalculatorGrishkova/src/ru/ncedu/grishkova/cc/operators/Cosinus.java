package ru.ncedu.grishkova.cc.operators;

import ru.ncedu.grishkova.cc.Operator;

public class Cosinus extends Operator {
    public Cosinus() {
        binary = false;
    }

    @Override
    public double calculate(double leftOperand, double rightOperand) {
        return Math.cos(leftOperand);
    }

}
