package com.example.administrator.safehome;

/**
 * Created by Administrator on 2017/12/12 0012.
 */

public class User {
    private static int id;
    private static String userName;
    private static String email;
    private static int phoneNum;
    private static String emergencyNum;
    private static String password;
    private static String address;
    User(){
        userName = " ";
        phoneNum = 0;
        address = " ";
    }

    public static int getId(){return id;}
    public static String getUserName(){
        return userName;
    }
    public static String getEmail(){
        return email;
    }
    public static String getPassword(){
        return password;
    }
    public static String getAddress(){
        return address;
    }
    public static int getPhoneNum(){
        return phoneNum;
    }
    public static String getEmergencyNum(){
        return emergencyNum;
    }
    public static void setId(int i){id = i;}
    public static void setUserName(String s){
        userName = s;
    }
    public static void  setEmail(String s){
        email = s;
    }
    public static void setPhoneNum(int i){
        phoneNum = i;
    }
    public static void setEmergencyNum(String i){
        emergencyNum = i;
    }
    public static void setPassword(String s){
        password = s;
    }
    public static void setAddress(String s){
        address = s;
    }
}
