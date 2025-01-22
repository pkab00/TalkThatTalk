package com.talk;

import java.sql.*;
import java.util.ArrayList;

/*
 * DBManager
 * 
 * Класс для взаимодействия с базой данных приложения.
 * Осуществляет добавление, удаление и изменение записей.
 * 
 * TODO: Установить пароль на БД для предотвращения доступа к ней извне.
 */

public class DBManager {
    private Connection connection;
    private Statement statement;

    public static enum ORDER_BY{
        ID,
        RUS,
        KOR,
        TRANSCRIPTION
    }

    DBManager(){
        try{
            this.connection = DriverManager.getConnection("jdbc:sqlite:talk\\src\\main\\resources\\base.db");
            this.statement = this.connection.createStatement();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    // возвращает ДБ в виде двумерного списка
    public ResultSet getBase(ORDER_BY order){
        ResultSet set = null;
        try{
            String sql = "select * from main order by " + order.toString();
            set = statement.executeQuery(sql);
            
        } catch(SQLException e){
            e.printStackTrace();
        }
        return set;
    }

    public ResultSet getRusAndKor(){
        ResultSet set = null;
        try{
            String sql = "select rus, kor from main";
            set = statement.executeQuery(sql);
            
        } catch(SQLException e){
            e.printStackTrace();
        }
        return set;
    }

    public void addNewRecord(ArrayList<String> data){
        try{
            String sql = "insert into main(rus, kor, transcription) values(?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            for(int i = 0; i < data.size(); i++){
                statement.setString(i+1, data.get(i));
            }
            statement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }


    public void editRecord(ArrayList<Object> data){
        try{
            String sql = "replace into main(id, rus, kor, transcription) values(?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            for(int i = 0; i < data.size(); i++){
                statement.setObject(i+1, data.get(i));
            }
            statement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteRecord(int id){
        try{
            String sql = "delete from main where id = '"+id+"';";
            statement.executeUpdate(sql);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
