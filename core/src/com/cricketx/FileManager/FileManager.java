package com.cricketx.FileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileManager{
    static Scanner sc = null;
    static FileWriter fw = null;
    static File file;

    protected static void setFile(File f){
        file = f;
    }
    protected static void closeFile(){
        file= null;
    }


    protected static boolean setToRead(){
        try {
            sc=  new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    protected static String readLine(){
        if(sc==null){
            return "null";
        }
        if(sc.hasNextLine()){
            return sc.nextLine();
        }
        return "null";
    }
    protected static void closeReader(){
        sc.close();
        sc = null;
    }

    protected static boolean setToWrite(boolean append) throws IOException {
        if(file==null){
            return false;
        }
        fw = new FileWriter(file,append);
        return true;
    }
    protected static boolean clearFile() throws IOException {
        if(file!=null && fw==null){
            fw = new FileWriter(file);
            fw.write("");

            return true;
        }
        return false;
    }
    protected static void writeLine(String line) throws IOException {
        fw.write(line);
        fw.write("\n");

    }
    protected static void closeWriter() throws IOException {
        fw.close();
        fw = null;
    }
}
