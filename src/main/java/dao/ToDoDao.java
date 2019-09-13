package dao;
import models.ToDo;
import java.util.List;

public interface ToDoDao {

    // LIST
    List<ToDo> all();

    // CREATE
    void add(ToDo todo);

    // READ
    ToDo find(int id);

    // UPDATE
     void edit(ToDo todo, String content);

    // DELETE
     void delete(int id);
     void clearAll();
}
