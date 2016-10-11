package ru.ncedu.grishkova.utils;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * The class is intended for archiving and unpacking one or more files and directories.
 * Works with "Zip" archives by using {@link java.util.zip}
 * Creates a new .zip file or updates existing file.
 * Supports adding and updating comments to archive.
 */

public class Archiver {
    public static void main(String[] args)  {
        Archiver archiver = new Archiver();
        archiver.printHelp();
        archiver.startArch(args);
        try {
            archiver.changeComment("12.zip");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The main method of class. Processes program arguments and decides
     * which task is needed to do (add files to new archive, add files to existing archive,
     * unpack archive in the work directory or in specified directory)
     * Invokes the methods for working with comments
     * @param args an array of program arguments. Zero argument must be the key
     *             "A" - archive, or "U" - unpack, first argument must be archive name
     *             (existing or not) ending with ".zip", next arguments are
     *             file names for archiving or destination folder for unpacking
     */
    public void startArch(String[] args) {
        try {

            String zipName = args[1];
            if (args.length > 2 && zipName.endsWith(".zip") && args[0].equals("A")) {//if the first file is .zip and if we have at least one file to add in zip
                System.out.println("\"Archive\" task is recognized.");
                File zipFile = new File(zipName);
                ArrayList<File> files = new ArrayList<>(); // files to add in zip
                for (int i = 2; i < args.length; i++) {
                    files.add(new File(args[i]));
                }
                ZipOutputStream zipOutputStream;
                if (zipFile.exists()) {
                    System.out.println("Such zip already exists");
                    readComment(zipName);
                    zipOutputStream = copyZipToZos(zipFile);
                } else {
                    zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
                }
                addComment();
                zipOutputStream.setComment(comment);
                addDirToArchive(zipOutputStream, files);
                zipOutputStream.close();
            } else if (args.length == 3 && zipName.endsWith(".zip") && args[0].equals("U")) {
                System.out.println("\"Unpack in the directory\" task is recognized.");
                readComment(zipName);
                changeComment(zipName);
                unpackZip(zipName, args[2]);
            } else if (args.length == 2 && zipName.endsWith(".zip") && args[0].equals("U")) { //if we use a simple unpacking
                System.out.println("\"Unpack\" task is recognized.");
                readComment(zipName);
                changeComment(zipName);
                unpackZip(zipName, new File(".").getAbsolutePath());
            } else {
                System.out.println("Wrong arguments.");
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Works with ZIP comments. Reads comment from archive if user enters "Y"
     * @param zipName name of zip file
     * @throws IOException
     */
    public void readComment(String zipName) throws IOException {
        System.out.println("Do you want to read zip comment? (Y/N)");
        if (bufferedReader.readLine().equals("Y")) {
            ZipFile zipFile = new ZipFile(zipName);
            comment = zipFile.getComment();
            System.out.println("Zip comment is \""+ comment + "\".");
            zipFile.close();
        }
    }

    /**
     * Works with ZIP comments. Sets entered string as a {@link #comment}
     * @throws IOException
     */
    private void addComment() throws IOException{
        System.out.println("Do you want to add/change a zip comment? (Y/N)");
        if (bufferedReader.readLine().equals("Y")) {
            System.out.println("Enter comment:");
            comment = bufferedReader.readLine();
        }
    }
    /**
     * Works with ZIP comments. If user enters "Y" changes comment in archive on
     * following entered comment.
     * @param zipName name of zip file
     * @throws IOException
     */
    public void changeComment(String zipName) throws IOException {
        System.out.println("Do you want to change zip comment? (Y/N)");
        if (bufferedReader.readLine().equals("Y")) {
            System.out.println("Enter comment:");
            comment = bufferedReader.readLine();
            ZipOutputStream zipOutputStream = copyZipToZos(new File(zipName));
            zipOutputStream.setComment(comment);
            zipOutputStream.close();

        }
    }

    /**
     * Adds given files in ZipOutputStream. Invoked recursively if one of files is directory
     * @param zos ZipOutputStream for writing
     * @param files list if files for adding
     */
    private static void addDirToArchive(ZipOutputStream zos, ArrayList<File> files) {
        for (File file : files) {
            // if the file is directory, use recursion
            if (file.isDirectory() && file.listFiles() != null) {
                addDirToArchive(zos, new ArrayList<>(Arrays.asList(file.listFiles())));
                continue;
            }
            try {
                byte[] buffer = new byte[1024];
                FileInputStream fis = new FileInputStream(file);
                zos.putNextEntry(new ZipEntry(file.getPath()));
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
                zos.closeEntry();
                fis.close();
            } catch (IOException ioe) {
                System.out.println("IOException :" + ioe);
            }
        }
    }

    /**
     * Unpacks zip file with given name in destination directory
     * @param zipName name of zip file
     * @param destinationDirectory name of directory for unpacking
     */
    private static void unpackZip(String zipName, String destinationDirectory) {
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(new File(zipName));
        } catch (IOException e) {
            System.out.println("Can't open/found zip");
            e.printStackTrace();
        }
        File folder = new File(destinationDirectory);
        if(!folder.exists()){
            folder.mkdir();
        }
        for (Enumeration e = (zipFile != null ? zipFile.entries() : null); e!=null && e.hasMoreElements();) {
            ZipEntry entry = (ZipEntry) e.nextElement();
            File file = new File(destinationDirectory + File.separator + entry.getName());
            if (entry.isDirectory()) {
                File newDir = new File(file.getAbsolutePath());
                if (!newDir.exists()) {
                    newDir.mkdirs();
                }
            } else {
                try {
                    InputStream is = zipFile.getInputStream(entry);
                    BufferedInputStream  zis = new BufferedInputStream  (is);
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                    FileOutputStream fOutput = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int count;
                    while ((count = zis.read(buffer)) > 0) {
                        // write 'count' bytes to the file output stream
                        fOutput.write(buffer, 0, count);
                    }
                    fOutput.close();
                    //zis.closeEntry();
                    zis.close();
                    is.close();

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * Prints instructions for use
     */

    public void printHelp() {
        System.out.println("Example to archive files: ");
        System.out.println("\tA archive_name.zip folder1 file1.* file2.* folder2 file3.*");
        System.out.println("All files must be located in work directory");
        System.out.println("Current work directory is " + new File(".").getAbsolutePath());
        System.out.println("Example  to unpack zip:");
        System.out.println("\tU archive_name.zip");
        System.out.println("or");
        System.out.println("\tU archive_name.zip destination_folder");
        System.out.println("To choose archive/unpack add \"A\"/\"U\" before file names.");
        System.out.println("To work with zip-comments use program dialog tips\n");
    }

    /**
     * Copies zip entries from given zip file to new ZipOutputStream
     * @param source source file for copy
     * @return an instance of ZipOutputStream which contains entries of source file
     * @throws IOException
     */
    private ZipOutputStream copyZipToZos(File source) throws IOException {
        ZipOutputStream out = null;
        File tmpZip = File.createTempFile(source.getName(), null);
        tmpZip.delete();
        if(!source.renameTo(tmpZip)) {
            throw new IOException("Could not make temp file (" + source.getName() + ")");
        }
        byte[] buffer = new byte[1024];
        ZipInputStream zin = new ZipInputStream(new FileInputStream(tmpZip));
        out = new ZipOutputStream(new FileOutputStream(source));

        for(ZipEntry ze = zin.getNextEntry(); ze != null; ze = zin.getNextEntry()) {
            out.putNextEntry(ze);
            for(int read = zin.read(buffer); read > -1; read = zin.read(buffer)) {
                out.write(buffer, 0, read);
            }
            out.closeEntry();
        }
        tmpZip.delete();
        zin.close();
        return out;
    }

    private String comment = "";
    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
}
