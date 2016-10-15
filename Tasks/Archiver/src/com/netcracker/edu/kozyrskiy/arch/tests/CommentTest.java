package com.netcracker.edu.kozyrskiy.arch.tests;


import com.netcracker.edu.kozyrskiy.arch.Archive;
import com.netcracker.edu.kozyrskiy.arch.ZipArchive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The arguments are:
 *                  args[0]: name of zip archive
 */


public class CommentTest {
    public static void main(String[] args) {
        Archive arc = new ZipArchive();

        //reading the comment from archives
        //prints null if no comment set
        System.out.println("Comment from the archive without initial comment:  " + arc.readCommentFromArchive(args[0] + ".zip"));
        System.out.println("Comment from the archive with initial comment:  " + arc.readCommentFromArchive(args[0] + "_with_comment.zip"));

        //setting a comment to archives
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the comment you would like to set to the archive without initial comment: ");
        try{
            arc.setCommentToArchive(args[0] + ".zip", reader.readLine());
            System.out.println("New comment: " + arc.readCommentFromArchive(args[0] + ".zip"));
        } catch (IOException e){
            System.out.println("Something wrong with reader: " + e.toString());
        }

        System.out.println("Enter the comment you would like to set to the archive with initial comment: ");
        try{
            arc.setCommentToArchive(args[0] + "_with_comment.zip", reader.readLine());
            System.out.println("New comment: " + arc.readCommentFromArchive(args[0] + "_with_comment.zip"));
        } catch (IOException e){
            System.out.println("Something wrong with reader: " + e.toString());
        }
    }
}
