package com.javarush.test.NetCraker.Utils.Archiver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Аркадий on 05.11.2015.
 */
public class MainArchiver
{
    /**
     *
     * @param args contains an identifier of the command at args[0] :
     *             "-c" for compression, "-d" for decompression, "-a" for addition;
     *             contains an archive's abstract pathname at args[1].
     *
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {
        //args = new String[] {"-c", "c:/javaidea/newArchive.zip", "c:/javaidea/dir1", "c:/javaidea/res.txt", "c:/javaidea/newDir"};
        //args = new String[] {"-cc", "c:/javaidea/newArchive.zip", "Compress comment", "c:/javaidea/dir1", "c:/javaidea/res.txt", "c:/javaidea/newDir"};
        //args = new String[] {"-d", "c:/javaidea/newArchive.zip", "c:/javaidea/resultFolder"};
        //args = new String[] {"-a", "c:/javaidea/newArchive.zip", "c:/javaidea/result.txt", "c:/javaidea/try.txt", "c:/javaidea/lalka"};
        //args = new String[] {"-ac", "c:/javaidea/newArchive.zip", "Test comment"};
        //args = new String[] {"-rc", "c:/javaidea/newArchive.zip"};

        try {
            switch (args[0]) {
                case "-c":
                    List<String> list1 = new ArrayList<>();
                    list1.addAll(Arrays.asList(args).subList(2, args.length));
                    Archiver.zipCompress(args[1], list1);
                    break;
                case "-cc":
                    List<String> list3 = new ArrayList<>();
                    list3.addAll(Arrays.asList(args).subList(3, args.length));
                    Archiver.zipCommentCompress(args[1], args[2], list3);
                    break;
                case "-d":
                    Archiver.zipDecompress(args[1], args[2]);
                    break;
                case "-a":
                    List<String> list2 = new ArrayList<>();
                    list2.addAll(Arrays.asList(args).subList(2, args.length));
                    Archiver.zipAdd(args[1], list2);
                    break;
                case "-ac":
                    Archiver.addComment(args[1], args[2]);
                    break;
                case "-rc":
                    String s = Archiver.readComment(args[1]);
                    System.out.println(String.format("Archive's comment is: \"%s\"", s));
                    break;
                default:
                    System.out.println("Incorrect command!\n" +
                            "You should enter one of allowed commands as the first argument:\n" +
                            "\"-c\" - compression, \"-d\" - decompression, \"-a\" - addition, " +
                            "\"-cc\" - compression with comment, \"-ac\" - add comment, \"-rc\" - read comment.");
            }
        } catch(IndexOutOfBoundsException ie) {
            System.out.println("Incorrect arguments!");
        }
        catch(Exception e) {
            System.out.println("Look for incorrect arguments or some programs, that can use your arguments' files!");
        }
    }
}
