import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static spark.Spark.get;
import static spark.Spark.post;
import dao.Sql2oToDoDao;
import dao.Sql2oLabelDao;
import models.ToDo;
import models.Label;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import static spark.Spark.*;

public class App {

    public static void main(String[] args) {

//        String connectionString = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        String connectionString = "jdbc:postgresql://localhost:5432/toDoList";
        Sql2o sql2o = new Sql2o(connectionString, "student", "");
        Sql2oToDoDao todoDao = new Sql2oToDoDao(sql2o);
        Sql2oLabelDao labelDao = new Sql2oLabelDao(sql2o);

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
//                List<Category> allCategories = categoryDao.getAll();
//                model.put("categories", allCategories);
            List<ToDo> todos = todoDao.all();
            model.put("todos", todos);
            return new ModelAndView(model, "templates/index.vtl");
        }, new spark.template.velocity.VelocityTemplateEngine());

        get ("/todos/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("labels", labelDao.all());
            return new ModelAndView(model, "templates/new-todo.vtl");
        }, new spark.template.velocity.VelocityTemplateEngine());

        post("/todos", (req, res) -> {
            String content = req.queryParams("content");
            System.out.println(req.queryParams("label"));
            ToDo todo = new ToDo(content);
            todoDao.add(todo);
            res.redirect("/");
            return null;
        });


        get("/todos/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            todoDao.clearAll();
            res.redirect("/");
            return null;
        });
    }
}
