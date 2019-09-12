import models.ToDo;
import org.sql2o.*;
import java.util.List;

public class DAO {

    private static Sql2o sql2o;

    static {
        sql2o = new Sql2o("jdbc:mysql://localhost:5432/toDoList");
    }

    public static void main(String[] args){
        List tmpList = getAllToDos();
        System.out.println(tmpList);
    }

    public static List<ToDo> getAllToDos(){
        String sql =
                "SELECT id, content, labelId " +
                        "FROM todo";

        try(Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(ToDo.class);
        }
    }



}
