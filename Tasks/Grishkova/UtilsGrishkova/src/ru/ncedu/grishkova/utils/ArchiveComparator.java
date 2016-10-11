package ru.ncedu.grishkova.utils;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * The class compares contents of two given zip archives and prints information
 * about changes in the second file relative to the first.
 * Finds out deleted, new created, updated (when files have the same names
 * but different sizes) and possible renamed files (when files have the same sizes
 * but different names)
 */
public class ArchiveComparator {
    public static void main(String[] args) throws IOException {
        ArchiveComparator archiveComparator = new ArchiveComparator();
        String archive1 = args.length>=2 ? args[0] : null;
        String archive2 = args.length>=2 ? args[1] : null;
        archiveComparator.startCompare(archive1, archive2);
        archiveComparator.printResults();
   }

    /**
     * The main method of class. Compares two archives with given names.
     * If one of argument id null, runs {@link #chooseFile(String)}
     * which uses graphic interface for files choosing.
     * Puts information about comparing in the class fields.
     * @param archiveName1 name of the first file
     * @param archiveName2 name of the second file
     */
    public void startCompare(String archiveName1, String archiveName2)  {
        try {
            if (archiveName1 == null || archiveName2 == null) {
                System.out.println("One of arguments is null. Choose zips in window");
                zipFile1 = new ZipFile(chooseFile("Choose file 1"));
                zipFile2 = new ZipFile(chooseFile("Choose file 2"));
            }  else {
                zipFile1 = new ZipFile(archiveName1);
                zipFile2 = new ZipFile(archiveName2);
            }
        } catch (IOException e) {
            System.out.println("Can't open archives");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        assert zipFile1 != null;
        for (Enumeration e1 = zipFile1.entries(); e1!=null && e1.hasMoreElements();) {
            //indicates is this entry of first archive contains in the second
            boolean file1IsFound = false;
            ZipEntry entry1 = (ZipEntry) e1.nextElement();
            String name1 = entry1.getName();
            assert zipFile2 != null;
            for (Enumeration e2 = zipFile2.entries(); e2!=null && e2.hasMoreElements();) {
                ZipEntry entry2 = (ZipEntry) e2.nextElement();
                String name2 = entry2.getName();
                long size1 = entry1.getSize();
                long size2 = entry2.getSize();
                if (name1.equals(name2)) {
                    if (size1 == size2) {
                        if (entry1.getCrc() == entry2.getCrc()) {//files are equals
                            common.add(name2);
                            file1IsFound = true;
                        } else { // file1 was updated

                            updated.add(name1);
                            common.add(name2);
                            file1IsFound = true;
                        }
                    } else { // file1 was updated
                        updated.add(name1);
                        common.add(name2);
                        file1IsFound = true;
                    }
                } else {
                    if (size1 == size2) { //file was renamed
                        renamed1.add(name1);
                        renamed2.add(name2);
                        common.add(name2);
                        file1IsFound = true;
                    }
                }
            }
            if (!file1IsFound) { //file was deleted
                deleted.add(name1);
            }
        }
        findNewFiles (zipFile2);
    }

    /**
     * Shows the frame which allows to choose archive in the file system.
     * @param message title of frame
     * @return File object, opened in the frame
     * @throws Exception if user did not choose a file
     */
    private File chooseFile(String message) throws Exception {
        JFileChooser fileOpen = new JFileChooser(new File(".").getPath());
        fileOpen.setDialogTitle(message);
        int ret = fileOpen.showOpenDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            return fileOpen.getSelectedFile();
        } else {
            throw new Exception("File not chosen");
        }
    }

    /**
     * Looks for the files in the second archive, which does not contains
     * in the first archive and puts them in {@link #newFile} list
     * @param zipFile2 the second zip archive
     */
    private  void findNewFiles(ZipFile zipFile2) {
        for (Enumeration e2 = zipFile2.entries(); e2!=null && e2.hasMoreElements();) {
            ZipEntry entry2 = (ZipEntry) e2.nextElement();
            String name2 = entry2.getName();
            if (!common.contains(name2)) {
                newFile.add(name2);
            }
        }
    }

    /**
     * Prints aggregated information about comparing archives in specified format
     */
    public void printResults() {
        System.out.printf("%20s%20s\n", zipFile1.getName()+"|",zipFile2.getName());
        System.out.println("-------------------+--------------------");
        for (String s : deleted) {
            System.out.printf("%19s| \n", "- "+s);
        }
        for (String s : newFile) {
            System.out.printf("%20s%20s\n", "|","+ "+s);
        }
        for (String s : updated) {
            System.out.printf("%20s%20s\n", "* "+s+"|","* "+s);
        }
        for (int i = 0; i < renamed1.size(); i++) {
            System.out.printf("%20s%20s\n", "? "+renamed1.get(i)+"|", "? "+renamed2.get(i));
        }

    }

    /**
     * List of files, contained both in the first and the second archives
     */
    private List<String> common = new ArrayList<>();
    /**
     * List of files, which names are equal but sizes are different in two archives
     */
    private List<String> updated = new ArrayList<>();
    /**
     * List of files, contained in the first archive but missing in the second one
     */
    private List<String> deleted = new ArrayList<>();
    /**
     * List of files, contained in the second archive but unlisted in the first one
     */
    private List<String> newFile = new ArrayList<>();
    /**
     * List of files, which sizes are equal but names are different.
     * Purpose this files was renamed. Names collected from the first archive
     */
    private List<String> renamed1 = new ArrayList<>();
    /**
     * List of files, which sizes are equal but names are different.
     * Purpose this files was renamed. Names collected from the second archive
     */
    private List<String> renamed2 = new ArrayList<>();
    private ZipFile zipFile1 = null;
    private ZipFile zipFile2 = null;

}
