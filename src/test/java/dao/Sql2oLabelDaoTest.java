package dao;

import models.Label;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class Sql2oLabelDaoTest {

    private Sql2oLabelDao labelDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/toDoListTest";
        Sql2o sql2o = new Sql2o(connectionString, "student", "");
        labelDao = new Sql2oLabelDao(sql2o);
        conn = sql2o.open();
    }

    @Test
    public void creatingALabelSetsId(){
        Label label = new Label("Chores");
        int originalLabelId = label.getId();
        labelDao.add(label);
        assertNotEquals(originalLabelId, label.getId());
    }

    @Test
    public void existingLabelsCanBeFoundById() throws Exception {
        Label label = new Label ("Chores");
        labelDao.add(label);
        Label foundLabel = labelDao.find(label.getId());
        assertEquals(label, foundLabel);
    }

    @Test
    public void allReturnsAllLabels(){
        Label labelOne = new Label("One");
        Label labelTwo = new Label("Two");
        Label labelThree = new Label("Three");

        labelDao.add(labelOne);
        labelDao.add(labelTwo);
        labelDao.add(labelThree);

        assertEquals(3, labelDao.all().size());
    }

    @Test
    public void allLabelsContainAllLabels(){
        Label labelOne = new Label("One");
        Label labelTwo = new Label("Two");
        Label labelThree = new Label("Three");

        labelDao.add(labelOne);
        labelDao.add(labelTwo);
        labelDao.add(labelThree);

        assertThat(labelDao.all(), hasItems(labelOne, labelTwo, labelThree));
    }

    @Test
    public void allReturnsNothingIfNoLabels(){
        assertTrue(labelDao.all().isEmpty());
    }

    @Test
    public void itCanDeleteALabel(){
        Label label = new Label ("mow the lawn");
        labelDao.add(label);
        labelDao.delete(label.getId());
        assertThat(labelDao.all(), not(contains(label)));
    }

    @Test
    public void itCanEditAToDo(){
        Label label = new Label ("Chore");
        String originalName = label.getName();
        labelDao.add(label);
        labelDao.edit(label.getId(), "Chores");
        Label newLabel = labelDao.find(label.getId());
        assertNotEquals(originalName, newLabel.getName());
    }

    @Test
    public void itCanDeleteAllToDos(){
        Label labelOne = new Label("One");
        Label labelTwo = new Label("Two");
        Label labelThree = new Label("Three");

        labelDao.add(labelOne);
        labelDao.add(labelTwo);
        labelDao.add(labelThree);

        labelDao.clearAll();
        assertEquals(0, labelDao.all().size());
        assertThat(labelDao.all(), not(hasItems(labelOne, labelTwo, labelThree)));
    }

    @After
    public void tearDown() throws Exception {
        String sql = "TRUNCATE label CASCADE;";
        conn.createQuery(sql).executeUpdate();
        sql = "ALTER SEQUENCE label_id_seq RESTART WITH 1;";
        conn.createQuery(sql).executeUpdate();
        conn.close();
    }

}
