package models;

import dao.Sql2oToDoDao;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;
public class ToDoTest {
    public ToDo toDo;
    ToDo toDoClass;
    private Sql2oToDoDao todoDao;
    private Connection conn; //must be sql2o class conn

    @Before
    public void setUp() {
        toDo = new ToDo("Walk dog", 1);
        String connectionString = "jdbc:postgresql://localhost:5432/toDoListTest";
        Sql2o sql2o = new Sql2o(connectionString, "student", "");
        conn = sql2o.open();
    }

    @Test
    public void itInstantiates(){
        assertTrue(toDo instanceof ToDo);
    }

    @Test
    public void itHasContent(){
        Assert.assertEquals("Walk dog", toDo.getContent());
    }

        @Test
        public void itCanHaveALabelId(){
            conn.createQuery("INSERT INTO label(name) VALUES(:chores)")
                    .addParameter("chores", "chores")
                    .executeUpdate();
            toDo = new ToDo("Walk dog", 2);
            Assert.assertEquals(2, toDo.getLabelId());
        }

        @Test
        public void defaultLabelIdIsOne(){
            ToDo anotherToDo = new ToDo("Walk dog");
            Assert.assertEquals(1, anotherToDo.getLabelId());
        }

        @After
        public void tearDown(){
            toDo = null;
            toDoClass = null;
            String sql = "TRUNCATE label CASCADE;";
            conn.createQuery(sql).executeUpdate();
            sql = "ALTER SEQUENCE label_id_seq RESTART WITH 1;";
            conn.createQuery(sql).executeUpdate();
            conn.createQuery("INSERT INTO label (name) VALUES (:none)")
                    .addParameter("none", "(none)")
                    .executeUpdate();
            conn.close();
        }

}
