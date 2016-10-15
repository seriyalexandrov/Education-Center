package ru.ncedu.grishkova.cc;

/**
 * Implementation of {@link CcState}. Realised the state, when it is expected entering valid
 * operator by user.
 */
public class CcStateWaitOp extends CcState {
    @Override
    public void processInput(String input, ConsoleCalculator cc) {
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
            System.out.println("Wrong operator. Try again");
            cc.setCurrentState(new CcStateWaitFirstArg());
        }
    }

    @Override
    public void printHelp() {
        System.out.println("Enter operator");
    }

}
