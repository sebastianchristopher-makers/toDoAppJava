import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static spark.Spark.get;
import static spark.Spark.post;
import dao.Sql2oToDoDao;
import dao.Sql2oLabelDao;
import models.ToDo;
import models.Label;
import org.mindrot.jbcrypt.BCrypt;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import static spark.Spark.*;

public class App {

    public static void main(String[] args) {

        String plainTextPassword = "Password1234";
        String hashPassword = BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
        System.out.println(BCrypt.checkpw(plainTextPassword, hashPassword));
        System.out.println(BCrypt.checkpw("DifferentPassword", hashPassword));

//        String connectionString = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        String connectionString = "jdbc:postgresql://localhost:5432/toDoList";
        Sql2o sql2o = new Sql2o(connectionString, "student", "");
        Sql2oToDoDao todoDao = new Sql2oToDoDao(sql2o);
        Sql2oLabelDao labelDao = new Sql2oLabelDao(sql2o);

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Label> labels = labelDao.all();
            model.put("labels", labels);
            model.put("labelDao", labelDao);
            List<ToDo> todos = todoDao.all();
            model.put("todos", todos);
            return new ModelAndView(model, "templates/index.vtl");
        }, new spark.template.velocity.VelocityTemplateEngine());

        get ("/todos/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int id = Integer.parseInt(req.params(":id"));
            model.put("labelDao", labelDao);
            model.put("labels", labelDao.all());
            model.put("todo", todoDao.find(id));
            return new ModelAndView(model, "templates/todo.vtl");
        }, new spark.template.velocity.VelocityTemplateEngine());

        get ("/todos/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("labels", labelDao.all());
            return new ModelAndView(model, "templates/new-todo.vtl");
        }, new spark.template.velocity.VelocityTemplateEngine());

        get ("/labels/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("labels", labelDao.all());
            return new ModelAndView(model, "templates/new-label.vtl");
        }, new spark.template.velocity.VelocityTemplateEngine());

        post("/todos/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int id = Integer.parseInt(req.params(":id"));
            String content = req.queryParams("content");
            int labelId = Integer.parseInt(req.queryParams("labels"));
            todoDao.edit(id, content, labelId);
            res.redirect("/");
            return null;
        });

        post("/todos/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int id = Integer.parseInt(req.params(":id"));
            todoDao.delete(id);
            res.redirect("/");
            return null;
        });

        post("/todos", (req, res) -> {
            String content = req.queryParams("content");
            int labelId = Integer.parseInt(req.queryParams("labels"));
            ToDo todo = new ToDo(content, labelId);
            todoDao.add(todo);
            res.redirect("/");
            return null;
        });

        post("/labels", (req, res) -> {
            String name = req.queryParams("name");
            Label label = new Label(name);
            labelDao.add(label);
            res.redirect("/");
            return null;
        });

        get("/labels/:id/todos", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            System.out.println(req.params("id"));
            int id = Integer.parseInt(req.params("id"));
            List<ToDo> todos = todoDao.filterByLabel(id);
            model.put("todos", todos);
            model.put("labelDao", labelDao);
            return new ModelAndView(model, "templates/labels/index.vtl");
        }, new spark.template.velocity.VelocityTemplateEngine());


        get("/todos/delete", (req, res) -> {
            todoDao.clearAll();
            res.redirect("/");
            return null;
        });
    }
}
