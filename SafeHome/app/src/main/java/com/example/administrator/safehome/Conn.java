package com.example.administrator.safehome;

import java.sql.DriverManager;
import java.sql.*;
import java.util.ArrayList;

public class Conn {
    Connection con;
    public Connection getCon(){

        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("数据库驱动加载成功！");

        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        try{
            con= DriverManager.getConnection("jdbc:mysql://120.79.45.134:3306/login_data","admin","admin");
            //多次重试及支持中文字符 url="jdbc:mysql://120.79.45.134:3306/login_data?user=admin&password=admin&useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false"
            //单次连接（不一定支持中文）url="jdbc:mysql://120.79.45.134:3306/login_data","admin","admin"
            System.out.println("数据库连接成功");
            //服务器信息：HOST Name:iZu3458wohe4usZ
            //IP:120.79.45.134  PORT:3306
        }catch(SQLException e){
            e.printStackTrace();
        }
        return con;
    }
    public void getLoginInfo(ArrayList<String> email, ArrayList<String> password,ArrayList<Integer> id,
                             ArrayList<String> name,ArrayList<String> address,ArrayList<Integer> phone,ArrayList<String> emyNum){
        String getEmail = "select count from cot_pd";
        String getPass = "select passwd from cot_pd";
        String getId = "select id from cot_pd";
        String getName = "select user_name from cot_pd";
        String getPhone = "select phone from cot_pd";
        String getAddress = "select address from cot_pd";
        String getEmyNum = "select emergency from cot_pd";
        try {
            Statement statement = (Statement) con.createStatement();
            Statement statement1 = (Statement) con.createStatement();
            Statement statement2 = (Statement) con.createStatement();
            Statement statement3 = (Statement) con.createStatement();
            Statement statement4 = (Statement) con.createStatement();
            Statement statement5 = (Statement) con.createStatement();
            Statement statement6 = (Statement) con.createStatement();
            ResultSet mEmy = statement6.executeQuery(getEmyNum);
            ResultSet mEmail = statement.executeQuery(getEmail);
            ResultSet mPassword = statement1.executeQuery(getPass);
            ResultSet mId = statement2.executeQuery(getId);
            ResultSet mName = statement3.executeQuery(getName);
            ResultSet mPhone = statement4.executeQuery(getPhone);
            ResultSet mAddress = statement5.executeQuery(getAddress);
            while (mEmail.next()){
                email.add(mEmail.getString("count"));
            }
            while (mPassword.next()){
                password.add(mPassword.getString("passwd"));
            }
            while (mId.next()){
                id.add(mId.getInt("id"));
            }
            while (mName.next()){
                name.add(mName.getString("user_name"));
            }
            while (mPhone.next()){
                phone.add(mPhone.getInt("phone"));
            }
            while (mAddress.next()){
                address.add(mAddress.getString("address"));
            }
            while (mEmy.next()){
                emyNum.add(mEmy.getString("emergency"));
            }
            /*con.close();
            statement.close();
            mEmail.close();
            mPassword.close();*/
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void postNewPass(){
        System.out.print(User.getPassword()+"\n\n\n");
        String setNewPass = "update cot_pd set passwd = '"+User.getPassword()+"' where count = '"+User.getEmail()+"'";
        try {
            Statement statement = (Statement) con.createStatement();
            statement.executeUpdate(setNewPass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void postNewInfo(){
        String setNewName = "update cot_pd set user_name = '"+User.getUserName()+"' where id = '"+User.getId()+"'";
        String setNewEmail = "update cot_pd set count = '"+User.getEmail()+"' where id = '"+User.getId()+"'";
        String setNewPhone = "update cot_pd set phone = '"+User.getPhoneNum()+"' where id = '"+User.getId()+"'";
        String setNewAddress = "update cot_pd set address = '"+User.getAddress()+"' where id = '"+User.getId()+"'";
        String setNewEmy = "update cot_pd set emergency = '"+User.getEmergencyNum()+"' where id = '"+User.getId()+"'";

        try {
            Statement statement = (Statement) con.createStatement();
            Statement statement1 = (Statement) con.createStatement();
            Statement statement2 = (Statement) con.createStatement();
            Statement statement3 = (Statement) con.createStatement();
            Statement statement4 = (Statement) con.createStatement();

            statement.executeUpdate(setNewName);
            statement1.executeUpdate(setNewEmail);
            statement2.executeUpdate(setNewPhone);
            statement3.executeUpdate(setNewAddress);
            statement4.executeUpdate(setNewEmy);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void getTmp(ArrayList<String> s1, ArrayList<String> s2){
        String getTmp = "select sensor1 from tmpsensor";
        String getTmp2 = "select sensor2 from tmpsensor";
        try {
            Statement statement = (Statement) con.createStatement();
            Statement statement2 = (Statement) con.createStatement();
            ResultSet mTmp = statement.executeQuery(getTmp);
            ResultSet mTmp2 = statement2.executeQuery(getTmp2);
            while (mTmp.next()) {
                s1.add(mTmp.getString("sensor1")) ;
                System.out.println("\t\t"+mTmp);
            }
            while (mTmp2.next()) {
                s2.add(mTmp2.getString("sensor2"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void getDW(ArrayList<String> door,ArrayList<String> window){
        String getwin1 = "select window1 from door_window where count = '"+User.getEmail()+"'";
        String getwin2 = "select window2 from door_window where count = '"+User.getEmail()+"'";
        String getwin3 = "select window3 from door_window where count = '"+User.getEmail()+"'";
        String getwin4 = "select window4 from door_window where count = '"+User.getEmail()+"'";
        String getdoor1 = "select door1 from door_window where count = '"+User.getEmail()+"'";
        String getdoor2 = "select door2 from door_window where count = '"+User.getEmail()+"'";
        String getdoor3 = "select door3 from door_window where count = '"+User.getEmail()+"'";
        String getdoor4 = "select door4 from door_window where count = '"+User.getEmail()+"'";
        try {
            Statement statement = (Statement) con.createStatement();
            Statement statement2 = (Statement) con.createStatement();
            Statement statement3 = (Statement) con.createStatement();
            Statement statement4 = (Statement) con.createStatement();
            Statement statement5 = (Statement) con.createStatement();
            Statement statement6 = (Statement) con.createStatement();
            Statement statement7 = (Statement) con.createStatement();
            Statement statement8 = (Statement) con.createStatement();
            ResultSet w1 = statement.executeQuery(getwin1);
            ResultSet w2 = statement2.executeQuery(getwin2);
            ResultSet w3 = statement3.executeQuery(getwin3);
            ResultSet w4 = statement4.executeQuery(getwin4);
            ResultSet d1 = statement5.executeQuery(getdoor1);
            ResultSet d2 = statement6.executeQuery(getdoor2);
            ResultSet d3 = statement7.executeQuery(getdoor3);
            ResultSet d4 = statement8.executeQuery(getdoor4);
            while (w1.next()){window.set(0,w1.getString("window1"));}
            while (w2.next()){window.set(1,w2.getString("window2"));}
            while (w3.next()){window.set(2,w3.getString("window3"));}
            while (w4.next()){window.set(3,w4.getString("window4"));}
            while (d1.next()){door.set(0,d1.getString("door1"));}
            while (d2.next()){door.set(1,d2.getString("door2"));}
            while (d3.next()){door.set(2,d3.getString("door3"));}
            while (d4.next()){door.set(3,d4.getString("door4"));}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void postState(){
        String flag;
        for(int i=1;i<=4;i++){
            if(BedroomActivity.rooms.get(i-1).getDoorState()==true)
                flag = "opened";
            else
                flag = "closed";
            String setDoor = "update door_window set door"+i+" = '"+flag+"'";
            try {
                Statement statement = (Statement) con.createStatement();
                statement.executeUpdate(setDoor);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for(int i=1;i<=4;i++){
            if(BedroomActivity.rooms.get(i-1).getWindowState()==true)
                flag = "opened";
            else
                flag = "closed";
            String setWin = "update door_window set window"+i+" = '"+flag+"'";
            try {
                Statement statement = (Statement) con.createStatement();
                statement.executeUpdate(setWin);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void getEmail(ArrayList<String> email){

        String getEmail = "select count from cot_pd";
        try {
            Statement statement = (Statement) con.createStatement();
            ResultSet mEmail = statement.executeQuery(getEmail);
            while (mEmail.next()){
                email.add(mEmail.getString("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            System.out.print("null String");
        }
    }
    public void getQues(ArrayList<String> questions){
        for(int i=1;i<=3;i++){
            String getQus = "select question"+i+" from ques where count = '"+User.getEmail()+"'";
            try {
                Statement statement = (Statement) con.createStatement();
                ResultSet mQues = statement.executeQuery(getQus);
                while (mQues.next()){
                    questions.add(mQues.getString("question"+i));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void getAns(ArrayList<String> answers){
        for(int i=1;i<=3;i++){
            String getAns = "select answer"+i+" from ques where count = '"+User.getEmail()+"'";
            try {
                Statement statement = (Statement) con.createStatement();
                ResultSet mAns = statement.executeQuery(getAns);
                while (mAns.next()){
                    answers.add(mAns.getString("answer"+i));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
/*    public static void main(String[] args){
        Conn c=new Conn();
        c.getCon();
    }*/
}