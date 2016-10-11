package ru.ncedu.grishkova.cc;

/**
 * The program implements the pattern State. This class represents a state of calculator.
 * Replacement of state made by implementations by own, therefore they need to know
 * about each other
 */
public abstract class CcState {
    /**
     * Processes input string using the instance of {@link ConsoleCalculator} and its methods.
     * Makes a decision which implementation of {@link CcState} must be used next
     * and sets it by {@link ConsoleCalculator#setCurrentState(CcState)}
     * @param input string entered by user
     * @param consoleCalculator an instance of {@link ConsoleCalculator}
     */
    public abstract void processInput(String input, ConsoleCalculator consoleCalculator);

    /**
     * Prints an instruction to user which input is expected
     */
    public abstract void printHelp();
}
