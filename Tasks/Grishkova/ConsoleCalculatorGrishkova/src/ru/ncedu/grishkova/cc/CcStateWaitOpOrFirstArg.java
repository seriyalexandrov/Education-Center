package ru.ncedu.grishkova.cc;

/**
 * Implementation of {@link CcState}. Realised the state, when it is expected entering valid
 * operator or first argument by user.
 */
public class CcStateWaitOpOrFirstArg extends CcState {
    @Override
    public void processInput(String input, ConsoleCalculator cc) {
        try {
            cc.setFirstArgument(Double.parseDouble(input));
            //wait until first argument is valid. Another - welcome to "catch"
            cc.setCurrentState(new CcStateWaitOp());
        } catch (Exception e) {
            if (cc.setIfValidOperator(input)) {
                if (cc.operatorIsBinary()) {
                    cc.setCurrentState(new CcStateWaitSecondArg());
                } else {
                    if (cc.doCalc()) {
                        cc.setCurrentState(new CcStateWaitOpOrFirstArg());
                        cc.copyResToFirstArg();
                    } else {
                        cc.setCurrentState(new CcStateWaitFirstArg());
                    }
                }
            } else {
                System.out.println("Can't parse input string to double number or wrong operator. " +
                        "Try again.");
                cc.setCurrentState(new CcStateWaitFirstArg());
            }
        }
    }

    @Override
    public void printHelp() {
        System.out.println("Enter the first argument or operator (current result will be the " +
                "first agrument)");
    }
}
