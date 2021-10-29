package com.cricketx.FileManager;

import com.badlogic.gdx.Gdx;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Settings extends FileManager{
    public boolean isMusic;
    public boolean isSound;
    public float musicVol;
    public float soundVol;
    File file;
    public Settings() throws IOException {
        isMusic = true;
        isSound = true;
        musicVol = 0.5f;
        soundVol = 0.5f;
        file = new File(String.valueOf(Gdx.files.internal("settings.txt")));
        extractData();


    }

    private void extractData(){

        setFile(file);
        boolean isReadable = setToRead();
        if(isReadable){
            while (true){
                String line = readLine();
                if(Objects.equals(line, "null")){
                    break;
                }
                System.out.println("Data found: "+line);
                if(line.substring(0,9).equals("<isMusic>")){
                    int start = line.indexOf('>');
                    int end = line.lastIndexOf('<');
                    String sub  =line.substring(start+1,end);
                    isMusic = sub.equals("true");
                }
                if(line.substring(0,9).equals("<isSound>")){
                    int start = line.indexOf('>');
                    int end = line.lastIndexOf('<');
                    String sub  =line.substring(start+1,end);
                    isSound = sub.equals("true");
                }
            }
        }
        else {
            System.out.println("File not found");
        }
        closeReader();
        closeFile();
    }

    public void setData() throws IOException {
        setFile(file);
        clearFile();
        boolean isWritable = setToWrite(true);

        if(isWritable){
            writeLine("<isMusic>"+isMusic+"</isMusic>");
            writeLine("<isSound>"+isSound+"</isSound>");
            writeLine("<musicVol>"+musicVol+"</musicVol>");
            writeLine("<soundVol>"+soundVol+"</soundVol>");
            System.out.println("Wrote Data to settings file");
        }
        closeWriter();
        closeFile();
    }
}
