package ru.ncedu.grishkova.cc;

/**
 * Implementation of {@link CcState}. Realised the state, when it is expected entering valid
 * second argument by user.
 */
public class CcStateWaitSecondArg extends CcState {
    @Override
    public void processInput(String input, ConsoleCalculator cc) {
        try {
            cc.setSecondArgument(Double.parseDouble(input));
            if (cc.doCalc()) {
                cc.setCurrentState(new CcStateWaitOpOrFirstArg());
                cc.copyResToFirstArg();
            } else {
                cc.setCurrentState(new CcStateWaitFirstArg());
            }
        } catch (Exception e) {
            System.out.println("Can't parse input string to double number. Try again");
            cc.setCurrentState(new CcStateWaitFirstArg());
        }
    }

    @Override
    public void printHelp() {
        System.out.println("Enter the second argument");
    }
}
