package com.cricketx.FileManager;

import com.badlogic.gdx.Gdx;

import java.io.File;
import java.io.IOException;

public class UserData extends FileManager {

    class Data{
        private String name;
        private int highScore;
        File file;
        public Data(){
            name = "";
            highScore =0;
        }
        public int getHighScore(){
            return highScore;
        }
        public void setHighScore(int score){
            highScore = score;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
    Data[] data;
    String[] names;
    static int numOfUsers;
    static int userid;
    File file;
    public UserData(){
        data = new Data[10];
        names = new String[10];
        numOfUsers=0;
        file = new File(String.valueOf(Gdx.files.internal("users.txt")));
        userid=0;
        extractData();
    }

    private void extractData(){
        setFile(file);
        boolean isReadable = setToRead();
        if (isReadable){
            while (true){

                String line = readLine();
                if(line.equals("null")){
                    System.out.println("File(users.txt) Read Succesfully");
                    break;
                }
                System.out.println("User data found: "+line);
                if (line.startsWith("Users")){
                    numOfUsers = Integer.parseInt(line.substring(8));
                    System.out.println("Number of users : "+numOfUsers);
                }
                if (line.startsWith("<User>")){
                    String userdata = line.substring(line.indexOf('>')+1,line.lastIndexOf('<'));
                    data[userid] = new Data();
                    data[userid].setName(userdata.substring(0,userdata.lastIndexOf(':')));
                    data[userid].setHighScore(Integer.parseInt(userdata.substring(userdata.lastIndexOf(':')+1)));
                    userid++;
                }
            }
        }
        else {
            System.out.println("Users.txt not found, cant load user data");
        }
        closeReader();
        closeFile();
    }

    public void setData() throws IOException {
        //file = new File(String.valueOf(Gdx.files.internal("users.txt")));//Ask sir
        setFile(file);
        clearFile();
        boolean isWritable = setToWrite(true);
        if(isWritable){
            writeLine("Users = "+numOfUsers);
            for (int i = 0; i < numOfUsers; i++) {
                writeLine("<User>"+data[i].getName()+":"+data[i].getHighScore()+"</User>");
            }
            System.out.println("User Data Saved");
        }
        else {
            System.out.println("Cant Write into Users.txt");
        }
        closeWriter();
        closeFile();
    }

    public boolean addUser(String name){
        if(numOfUsers==10){
            return false;
        }
        numOfUsers++;
        data[numOfUsers-1] = new Data();
        data[numOfUsers-1].setName(name);

        return true;
    }

    public boolean deleteUser(String name){
        for (int i = 0; i < numOfUsers; i++) {
            if(data[i].getName().equals(name)){
                for (int j = i; j <numOfUsers-1 ; j++) {
                    data[j].setName(data[j+1].getName());
                    data[i].setHighScore(data[j+1].getHighScore());
                }
                numOfUsers--;
                return true;
            }
        }
        return false;
    }



    public int getUserScore(String name){
        for (int i = 0; i < numOfUsers; i++) {
            if(data[i].getName().equals(name)){
                return data[i].getHighScore();
            }
        }
        return -1;
    }
    public void setUserScore(String name,int score){
        for (int i = 0; i < numOfUsers; i++) {
            if(data[i].getName().equals(name)){
                data[i].setHighScore(score);
            }
        }
    }

    public String[] getNamesList(){
        names = new String[numOfUsers];
        for (int i = 0; i < numOfUsers; i++) {
            names[i] = data[i].getName();
        }
        return names;
    }


}
