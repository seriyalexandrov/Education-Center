package ru.ncedu.grishkova.cc;

/**
 * Implementation of {@link CcState}. Realised the state, when it is expected entering valid
 * first argument by user.
 */
public class CcStateWaitFirstArg extends CcState {
    @Override
    public void processInput(String input, ConsoleCalculator cc) {
        try {
            cc.setFirstArgument(Double.parseDouble(input));
            cc.setCurrentState(new CcStateWaitOp());
        } catch (Exception e) {
            System.out.println("Can't parse input string to double number. Try again");
            cc.setCurrentState(new CcStateWaitFirstArg());
        }

    }
    @Override
    public void printHelp() {
        System.out.println("Enter the first argument");
    }
}
