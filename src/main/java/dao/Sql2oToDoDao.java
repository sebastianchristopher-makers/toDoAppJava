package dao;

import models.ToDo;
import org.sql2o.*;

import java.util.Collections;
import java.util.List;

public class Sql2oToDoDao implements ToDoDao { //implementing our interface

    private final Sql2o sql2o;

    public Sql2oToDoDao(Sql2o sql2o){
        this.sql2o = sql2o; //making the sql2o object available everywhere so we can call methods in it
    }

    @Override
    public void add(ToDo todo) {
        String sql = "INSERT INTO todo (content) VALUES (:content)"; //raw sql
        try(Connection con = sql2o.open()){ //try to open a connection
            int id = (int) con.createQuery(sql, true) //make a new variable
                    .bind(todo) //map my argument onto the query so we can use information from it (pass in :content)
                    .executeUpdate() //run it all
                    .getKey(); //int id is now the row number (row “key”) of db
            todo.setId(id); //update object to set id now from database
        } catch (Sql2oException ex) {
            System.out.println("25: " + ex);
        }
    }

    @Override
    public List<ToDo> all() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM todo") //raw sql
                    .executeAndFetch(ToDo.class); //fetch a list
        } catch(Sql2oException ex) {
            System.out.println("35: " + ex);
        }
        return Collections.emptyList();
    }

    @Override
    public ToDo find(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM todo WHERE id = :id")
                    .addParameter("id", id) //key/value pair, key must match above
                    .executeAndFetchFirst(ToDo.class); //fetch an individual item
        }
    }

    @Override
    public void delete(int id){
        try(Connection con = sql2o.open()){
            con.createQuery("DELETE FROM todo WHERE id = :id")
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println("57: " + ex);
        }
    }

    @Override
    public void edit(ToDo todo, String content){
        try(Connection con = sql2o.open()){
            con.createQuery("UPDATE todo SET content = :content WHERE id = :id")
                    .addParameter("id", todo.getId())
                    .addParameter("content", content)
                    .executeUpdate();
            todo.setContent(content);
        } catch (Sql2oException ex) {
            System.out.println("70: " + ex);
        }
    }

    @Override
    public List<ToDo> filterByLabel(int labelId){
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM todo WHERE labelId = :labelId")
                    .addParameter("labelId", labelId)
                    .executeAndFetch(ToDo.class); //fetch a list
        } catch(Sql2oException ex) {
            System.out.println(ex);
        }
        return Collections.emptyList();
    }

    @Override
    public void clearAll(){
        try(Connection con = sql2o.open()){
            con.createQuery("TRUNCATE todo").executeUpdate();
        }
    }
}