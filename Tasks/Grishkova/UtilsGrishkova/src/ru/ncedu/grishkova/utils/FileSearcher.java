package ru.ncedu.grishkova.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class intended for searching files which names match specified regular expression
 * or contain defined part of name. Searching performs in the work directory.
 * Information about found files includes name, path, directory or file ,
 * and some columns involved by keys in program arguments
 */

public class FileSearcher {
    public static void main(String[] args) {
        FileSearcher fileSearcher = new FileSearcher();
        fileSearcher.printHelp();
        fileSearcher.startSearch(args);
    }

    /**
     * The main method of program. Processes input arguments and searches necessary files
     * @param args array of strings. arg[0] must be regular expression or part of name.
     *             Next arguments can be keys "S" for size information
     *             or "D" for modification date
     */
    public void startSearch(String[] args) {
        if (args.length > 0) {
            pattern = args[0];
        } else {
            System.out.println("No arguments in command line");
            return;
        }
        File rootDir = new File(".");
        if (rootDir.listFiles() != null) {
            setArgFlags(args);
            printHeadline();
            searchInDir(new ArrayList<>(Arrays.asList(rootDir.listFiles())));
        } else {
            System.out.println("Work directory is empty");
        }
    }

    /**
     * Sets flags in {@link #dateFlag} or/and {@link #sizeFlag} if args contains
     * keys "D" or/and "S" accordingly
     * @param args input arguments
     */
    private void setArgFlags(String[] args) {
        if (args.length == 2) {
            switch (args[1]) {
                case "S":
                    sizeFlag = true;
                    break;
                case "D":
                    dateFlag = true;
                    break;
                default:
                    System.out.println("Wrong flag");
                    break;
            }
        } else if (args.length == 3) {
            if (args[1].equals("S") || args[2].equals("S") ) {
                sizeFlag = true;
            }
            if (args[1].equals("D") || args[2].equals("D")) {
                dateFlag = true;
            }
            if (!(sizeFlag&&dateFlag)) {
                System.out.println("Some flags are wrong");
            }
        }
    }

    /**
     * Searches files in given list that match {@link #pattern}. If list contains directory,
     * method invokes yourself recursively with contents of directory as an parameter
     * @param files list of files for searching
     */
    private  void searchInDir(ArrayList<File> files) {
        for (File file : files) {
            // if the file is directory, use recursion
            if (file.isDirectory() && file.listFiles() != null) {
                searchInDir(new ArrayList<>(Arrays.asList(file.listFiles())));
            }
            String fileName = file.getName();
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(fileName);
            if (fileName.contains(pattern) || m.matches()) {
                printFileInfo(file);
            }
        }
    }
    /**
     * Prints headline accordingly value of flags
     */
    private void printHeadline() {
        if (sizeFlag && dateFlag) {
            System.out.printf ("%10s\t%20s\t%15s\t%20s\t%s\n\n", "Type", "Name", "Size, B", "Date modified", "Path");
        } else if (sizeFlag) {
            System.out.printf ("%10s\t%20s\t%15s\t%s\n\n", "Type", "Name", "Size, B", "Path");
        } else if (dateFlag) {
            System.out.printf ("%10s\t%20s\t%20s\t%s\n\n", "Type", "Name", "Date modified", "Path");
        } else {
            System.out.printf ("%10s\t%20s\t%s\n\n", "Type", "Name", "Path");
        }
    }

    /**
     * Prints information about file accordingly value of flags
     * @param file
     */
    private void printFileInfo(File file) {
        String isDir = file.isDirectory() ? "Directory" : "File";
        if (sizeFlag && dateFlag) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
            System.out.printf ("%10s\t%20s\t%15s\t%20s\t%s\n", isDir, file.getName(), file.length
                    (), sdf.format(file.lastModified()), file.getPath());
        } else if (sizeFlag) {
            System.out.printf ("%10s\t%20s\t%15s\t%s\n", isDir, file.getName(), file.length
                    (), file.getPath());
        } else if (dateFlag) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
            System.out.printf ("%10s\t%20s\t%20s\t%s\n", isDir, file.getName(),
                    sdf.format(file.lastModified()), file.getPath());
        } else {
            System.out.printf ("%10s\t%20s\t%s\n", isDir, file.getName(), file.getPath());
        }
    }

    /**
     * Prints user help
     */
    public void printHelp() {
        System.out.println("You can search files or directories in work directory by pattern or " +
                "part of name");
        System.out.println("First argument is regex pattern or part of name");
        System.out.println("Second and third arguments in any order give extra info about files");
        System.out.println("S flag provided size of file");
        System.out.println("D flag provided date of last modification");
        System.out.println();
    }

    /**
     * Can be part of name or regular expression
     */
    private String pattern;
    /**
     * Is true if there was flag "S"
     */
    private boolean sizeFlag;
    /**
     * Is true if there was flag "D"
     */
    private boolean dateFlag;
}
