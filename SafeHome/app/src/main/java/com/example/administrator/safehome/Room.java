package com.example.administrator.safehome;

/**
 * Created by Administrator on 2017/12/25 0025.
 */

public class Room {
    String name;
    String temprature;
    boolean doorState;
    boolean windowState;

    Room(){
        name = "room";
        temprature = "41";
        doorState = false;
        windowState = false;
    }
    void setTemprature(String s){
        temprature = s;
    }
    void setName(String s){name = s;}
    void setDoorState(boolean b){
        doorState = b;
    }
    void setWindowState(boolean b){
        windowState = b;
    }
    String getName(){return name;}
    String getTemprature(){return temprature;}
    Boolean getDoorState(){return doorState;}
    Boolean getWindowState(){return windowState;}
}
