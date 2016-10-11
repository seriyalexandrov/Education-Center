package com.javarush.test.NetCraker.Utils.Archiver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by ������� on 05.11.2015.
 */
public class Archiver
{
    private static ZipOutputStream zos;
    private static String rootDir;

    /**
     * Requests that all files and directories denoted by their pathname
     * in list be packed to an archive.
     *
     * @param archiveName The pathname of the archive
     * @param list contains pathnames of required to be packed files
     *
     * @throws IOException if the archive could not be created
     */
    public static void zipCompress(String archiveName, List<String> list) throws IOException
    {
        boolean success = true;

        FileOutputStream fos = new FileOutputStream(archiveName);
        zos = new ZipOutputStream(fos);
        try {
            for (String fileName : list)
            {
                File file = new File(fileName);
                String shortName = file.getName();
                rootDir = fileName.substring(0, fileName.length() - shortName.length() - 1);
                if (file.isDirectory())
                    compressForDir(fileName);
                else
                    compressForFile(fileName);
            }
        } catch(IOException e) {
            System.out.println(e);
            success = false;
        }
        zos.close();
        fos.close();

        if(!success) {
            boolean isDeleted = new File(archiveName).delete();
            if(isDeleted) {
                System.out.println(String.format("Temp archive \"%s\" was deleted.", archiveName));
                System.out.println("Compression wasn't completed!");
                throw new IOException();
            } else {
                System.out.println("File is used by another process and can't be deleted!");
                System.out.println("Compression wasn't completed!");
                throw new IOException();
            }
        } else {
            System.out.println(String.format("Compressed successfully to \"%s\"", archiveName));
        }
    }

    /**
     * Requests that all files and directories denoted by their pathname
     * in list be packed to an archive and comments it.
     *
     * @param archiveName The pathname of the archive
     * @param list contains pathnames of required to be packed files
     *
     * @throws IOException if the archive could not be created
     */
    public static void zipCommentCompress(String archiveName, String comment, List<String> list) throws IOException
    {
        boolean success = true;

        FileOutputStream fos = new FileOutputStream(archiveName);
        zos = new ZipOutputStream(fos);
        zos.setComment(comment);
        try {
            for (String fileName : list)
            {
                File file = new File(fileName);
                String shortName = file.getName();
                rootDir = fileName.substring(0, fileName.length() - shortName.length() - 1);
                if (file.isDirectory())
                    compressForDir(fileName);
                else
                    compressForFile(fileName);
            }
        } catch(IOException e) {
            System.out.println(e);
            success = false;
        }
        zos.close();
        fos.close();

        if(!success) {
            boolean isDeleted = new File(archiveName).delete();
            if(isDeleted) {
                System.out.println(String.format("Temp archive \"%s\" was deleted.", archiveName));
                System.out.println("Compression wasn't completed!");
                throw new IOException();
            } else {
                System.out.println("File is used by another process and can't be deleted!");
                System.out.println("Compression wasn't completed!");
                throw new IOException();
            }
        } else {
            System.out.println(String.format("Compressed successfully to \"%s\", comment was added.", archiveName));
        }
    }

    private static void compressForDir(String dirName) throws IOException
    {
        for(String fileName: new File(dirName).list()) {
            File file = new File(dirName + File.separator + fileName);
            if(file.isDirectory())
                compressForDir(dirName + File.separator + fileName);
            else
                compressForFile(dirName + File.separator + fileName);
        }
    }

    private static void compressForFile(String fileName) throws IOException
    {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);

        String resultName = fileName.substring(rootDir.length() + 1);
        ZipEntry zipEntry = new ZipEntry(resultName);
        zos.putNextEntry(zipEntry);

        byte[] buffer = new byte[1024];
        int count;
        while((count = fis.read(buffer)) >= 0){
            zos.write(buffer, 0, count);
        }

        zos.closeEntry();
        fis.close();
    }

    /**
     * Requests that contents of an archive be extracted to output directory.
     *
     * @param archiveName The pathname of the archive
     * @param outputDir The pathname of output directory
     *
     * @throws IOException if the archive could not be extracted to output directory
     */
    public static void zipDecompress(String archiveName, String outputDir) throws IOException
    {
        try(ZipInputStream zis = new ZipInputStream(new FileInputStream(archiveName))) {
            File folder = new File(outputDir);
            folder.mkdir();

            byte[] buffer = new byte[1024];
            ZipEntry zipEntry;
            while((zipEntry = zis.getNextEntry()) != null) {
                String fileName = zipEntry.getName();
                File file = new File(outputDir + File.separator + fileName);
                file.getParentFile().mkdirs();
                FileOutputStream fos = new FileOutputStream(file);

                int count;
                while((count = zis.read()) >= 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
            }

            System.out.println(String.format("Decompressed successfully from \"%s\" to \"%s\"", archiveName, outputDir));
        }
        catch (IOException e)
        {
            System.out.println("Decompression wasn't completed!");
            throw e;
        }
    }

    /**
     * Requests that all files and directories denoted by their pathname
     * in list be added to an archive. Saves comment.
     *
     * @param archiveName The pathname of the archive
     * @param list contains pathnames of required to be added files
     *
     * @throws IOException if files could not be added to the archive
     */
    public static void zipAdd(String archiveName, List<String> list) throws IOException
    {
        try {
            String rootDir = new File(archiveName).getParent();
            File tempDir = new File(rootDir + File.separator + "temp$Dir");
            zipDecompress(archiveName, tempDir.getAbsolutePath());
            List<String> resultList = new ArrayList<>();
            for(String fileName: tempDir.list()) {
                resultList.add(rootDir + File.separator + fileName);
            }
            resultList.addAll(list);
            String tempArchiveName = rootDir + File.separator + "temp$Archive.zip";
            zipCommentCompress(tempArchiveName, readComment(archiveName), resultList);
            boolean isDeletedOldArch = new File(archiveName).delete();
            if(!isDeletedOldArch) {
                System.out.println("Old archive can't be deleted now!");
                new File(tempArchiveName).delete();
                throw new IOException();
            }
            new File(tempArchiveName).renameTo(new File(archiveName));
            System.out.println(String.format("Added successfully to \"%s\"", archiveName));
        } catch (IOException e)
        {
            System.out.println("Addition wasn't completed!");
            throw e;
        } finally {
            deleteDirectory(new File(rootDir + File.separator + "temp$Dir"));
        }
    }

    private static boolean deleteDirectory(File dir) {
        if(! dir.exists() || !dir.isDirectory())    {
            return false;
        }
        String[] files = dir.list();
        for(String file : files) {
            File f = new File(dir, file);
            if (f.isDirectory()) {
                deleteDirectory(f);
            } else {
                f.delete();
            }
        }
        return dir.delete();
    }

    /**
     * Adds a comment to an archive.
     *
     * @param archiveName The pathname of the archive
     * @param comment's text
     *
     * @throws IOException if comment couldn't be added to the archive
     */
    public static void addComment(String archiveName,String comment) throws IOException
    {
        try {
            String rootDir = new File(archiveName).getParent();
            File tempDir = new File(rootDir + File.separator + "temp$Dir");
            zipDecompress(archiveName, tempDir.getAbsolutePath());
            List<String> resultList = new ArrayList<>();
            for(String fileName: tempDir.list()) {
                resultList.add(rootDir + File.separator + fileName);
            }
            String tempArchiveName = rootDir + File.separator + "temp$Archive.zip";
            zipCommentCompress(tempArchiveName, comment, resultList);
            boolean isDeletedOldArch = new File(archiveName).delete();
            if(!isDeletedOldArch) {
                System.out.println("Old archive can't be deleted now!");
                new File(tempArchiveName).delete();
                throw new IOException();
            }
            new File(tempArchiveName).renameTo(new File(archiveName));
            System.out.println(String.format("Comment was added successfully to \"%s\"", archiveName));
        } catch (IOException e) {
            System.out.println(String.format("Error occurred, comment couldn't be added to \"%s\"", archiveName));
            throw e;
        } finally {
            deleteDirectory(new File(rootDir + File.separator + "temp$Dir"));
        }
    }

    /**
     * Reads and returns archive's comment.
     *
     * @param archiveName The pathname of the archive
     *
     * @return the archive's comment
     *
     * @throws IOException if archive's comment couldn't be read
     */
    public static String readComment(String archiveName) throws IOException
    {
        try {
            ZipFile zipFile = new ZipFile(archiveName);
            String result = zipFile.getComment();
            zipFile.close();
            return result;
        } catch (IOException e) {
            System.out.println(String.format("Error occurred, comment couldn't be read from \"%s\"", archiveName));
            throw e;
        }
    }
}
