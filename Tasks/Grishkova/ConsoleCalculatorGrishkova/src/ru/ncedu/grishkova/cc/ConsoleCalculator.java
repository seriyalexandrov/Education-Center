package ru.ncedu.grishkova.cc;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The program implements the pattern State. This class is context class, which contains
 * current state of calculator ({@link CcState}) and receives user input.
 * Stores arguments for calculating ({@link #firstArgument}, {@link #secondArgument}
 * and {@link #currentOperator}) and list of available operators.
 * This class gives input in current implementation of CcState, until user enters "exit"
 *
 * Calculator can use different operations and functions
 * which are implementations of {@link Operator}. Anyone can add any wished binary or unary
 * function by adding new class, extended Operator and overriding its methods and parameters.
 * ConsoleCalculator class loads available classes by reading its names from file,
 * located in {@link #operatorsPath}.
 *
 * CcState can make an inquiry to do calculation with current arguments by using
 * {@link Operator#calculate(double, double)}
 */
public class ConsoleCalculator {
    /**
     * Starts work of calculator. Loads operators by using {@link #loadOperators()}.
     * If it is not successful, stops own working.
     * Gets user's input until he enters "exit"
     * and runs {@link CcState#processInput(String, ConsoleCalculator)} to process input
     * in current state.
     */
    public void startUserDialog() {
        printWelcome();
        if (!loadOperators()) {
            return;
        }
        String input = "";
        try  {
            currentState.printHelp();
            while (! (input = bufferedReader.readLine()).equals("exit") ) {
                currentState.processInput(input, this);
                currentState.printHelp();
            }
        } catch (IOException e) {
            System.out.println("IOException");
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("I made for you " + countOfOperations + " successful operations. " +
                "Bye-bye.");
    }

    /**
     * Prints welcome and help
     */
    public void printWelcome() {
        System.out.println("Welcome to Console Calculator");
        System.out.println("Enter numbers and operators each in new line");
        System.out.println("To exit enter \"exit\"");
    }

    /**
     * Loads operators from file {@link #operatorsPath} and puts instances of each loaded class
     * in {@link #mapOperators}.
     * Implementations of {@link Operator} must be in "operators" package and file must contains
     * information in  right format.
     * If load is not successful, offers user to repeat attempt or exit.
     * @return true if and only if operators loaded successfully
     */
    public boolean loadOperators() {
        while (true) {
            try (BufferedReader fileReader = new BufferedReader(new FileReader(operatorsPath))) {
                String s;
                while ((s = fileReader.readLine()) != null) {
                    String[] setting = s.split("\\s+");
                    mapOperators.put(setting[0], (Operator) Class.forName(setting[1]).newInstance());
                }
                System.out.print("Operators loaded successfully. Available operators are: ");
                for (String key : mapOperators.keySet()) {
                    System.out.print(key + " ");
                }
                System.out.println("");
                System.out.println();
                return true;
            } catch (FileNotFoundException e) {
                System.out.println("File with operators not found. ");
            } catch (IOException e) {
                System.out.println("IOException in file reading ");
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                System.out.println("Exception in class loading " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Exception:" + e.getMessage());
            }
            //only in case of exception
            System.out.println("To exit enter \"exit\".");
            System.out.println("To try again edit file '" + operatorsPath + "' and press " +
                            "enter");
            String answer="";
            try {
                answer = bufferedReader.readLine();
                if (answer.equals("exit")) {
                    return false;
                }
            } catch (IOException e1) {
                System.out.println("IOException " + e1);
                e1.printStackTrace();
            }
        }
    }

    /**
     * Checks is input valid operator using {@link #mapOperators}
     * Sets {@link #currentOperator} if input is valid
     * @param input input string, entered by user
     * @return true if and only if input is valid operator
     */
    public boolean setIfValidOperator(String input) {
        if (mapOperators.containsKey(input)) {
            currentOperator = mapOperators.get(input);
            return true;
        }
        return false;
    }

    /**
     * Performs calculating with {@link #firstArgument} and {@link #secondArgument} by
     * invocation {@link Operator#calculate(double, double)} using implementation of Operator
     * stored in {@link #currentOperator}.
     * Checks {@link #result} of calculating for correct result.
     * Catches any exceptions that {@link Operator#calculate(double, double)} could throw in wrong
     * cases of arguments
     * @return true if and only if calculating was successful and {@link #result} is correct
     * (not NaN, not Infinite)
     */
    public boolean doCalc() {
        try {
            result = currentOperator.calculate(firstArgument, secondArgument);
            if (Double.isInfinite(result) ) {
                throw new ArithmeticException("Result is Infinite");
            }
            if (Double.isNaN(result)) {
                throw new ArithmeticException("Result is NaN");
            }
            System.out.println(result);
            countOfOperations++;
        } catch (Exception e) {
            System.out.println("Wrong input:" + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Checks is {@link #currentOperator} binary or unary
     * @return true if operator is binary, false if operator is unary
     */
    public boolean operatorIsBinary() {
        return currentOperator.isBinary();
    }

    /**
     * Sets {@link #firstArgument} in value {@link #result}.
     * Invoked in cases we need to use previous result as first argument of new calculating
     */
    public void copyResToFirstArg() {
        firstArgument = result;
    }

    /**
     * Sets new instance of {@link CcState} in {@link #currentState}
     * @param currentState
     */
    public void setCurrentState(CcState currentState) {
        this.currentState = currentState;
    }

    public void setFirstArgument(double firstArgument) {
        this.firstArgument = firstArgument;
    }

    public void setSecondArgument(double secondArgument) {
        this.secondArgument = secondArgument;
    }

    /**
     * Stores the path to list of operator classes (implementations of {@link Operator})
     * File must contains names of operator each in new line in next format: "'operator_symbols'
     * + ' ' + 'name_of_class'"
     */
    private static String operatorsPath = new File("").getAbsolutePath() + "\\operators.txt";
    /**
     * Stores 'operator_symbols' as key and instance of Operator implementation as value
     */
    private static Map<String, Operator> mapOperators = new HashMap<>();
    /**
     * Stores current implementation of {@link CcState}
     */
    private CcState currentState = new CcStateWaitFirstArg();
    private double secondArgument = 0.0;
    private double firstArgument = 0.0;
    private Operator currentOperator;
    private double result = 0.0;
    /**
     * Count of successful operations
     */
    private int countOfOperations = 0;
    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
}
