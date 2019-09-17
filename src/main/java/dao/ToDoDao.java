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
    List<ToDo> filterByLabel(int labelId);

    // UPDATE
     void edit(int id, String content, int labelId);

    // DELETE
     void delete(int id);
     void clearAll();
}
