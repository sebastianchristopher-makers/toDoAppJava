package dao;
import dao.Sql2oToDoDao;
import models.ToDo;
import static org.hamcrest.Matchers.*;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class Sql2oToDoDaoTest {

    private Sql2oToDoDao todoDao;
    private Connection conn; //must be sql2o class conn

    @Before
    public void setUp() throws Exception {
//        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        String connectionString = "jdbc:postgresql://localhost:5432/toDoListTest";
        Sql2o sql2o = new Sql2o(connectionString, "student", "");
        todoDao = new Sql2oToDoDao(sql2o);
        conn = sql2o.open(); //keep connection open through entire test so it does not get erased
    }

    @Test
    public void addingToDoSetsId() throws Exception {
        ToDo todo = new ToDo ("mow the lawn", 1);
        int originalToDoId = todo.getId();
        todoDao.add(todo);
        assertNotEquals(originalToDoId, todo.getId());
    }

    @Test
    public void existingToDosCanBeFoundById() throws Exception {
        ToDo todo = new ToDo ("mow the lawn");
        todoDao.add(todo); //add to dao (takes care of saving)
        ToDo foundToDo = todoDao.find(todo.getId()); //retrieve
        assertEquals(todo, foundToDo); //should be the same
    }

    @Test
    public void allReturnsAllToDos(){
        ToDo todoOne = new ToDo("One");
        ToDo todoTwo = new ToDo("Two");
        ToDo todoThree = new ToDo("Three");

        todoDao.add(todoOne);
        todoDao.add(todoTwo);
        todoDao.add(todoThree);

        assertEquals(3, todoDao.all().size());
    }

    @Test
    public void allToDosContainAllToDos(){
        ToDo todoOne = new ToDo("One");
        ToDo todoTwo = new ToDo("Two");
        ToDo todoThree = new ToDo("Three");

        todoDao.add(todoOne);
        todoDao.add(todoTwo);
        todoDao.add(todoThree);

        assertThat(todoDao.all(), hasItems(todoOne, todoTwo, todoThree));
    }

    @Test
    public void allReturnsNothingIfNoToDos(){
        assertTrue(todoDao.all().isEmpty());
    }

    @Test
    public void itCanDeleteAToDo(){
        ToDo todo = new ToDo ("mow the lawn");
        todoDao.add(todo);
        todoDao.delete(todo.getId());
        assertThat(todoDao.all(), not(contains(todo)));
    }

    @Test
    public void itCanEditAToDo(){
        ToDo todo = new ToDo ("mow the lawn");
        String originalToDoContent = todo.getContent();
        todoDao.add(todo);
        todoDao.edit(todo.getId(), "I say something else now.", todo.getLabelId());
        String newToDoContent = todoDao.find(todo.getId()).getContent();
        assertNotEquals(originalToDoContent, newToDoContent);
    }

    @Test
    public void itCanEditAToDoAddingADifferentLabel(){
        String sql = "INSERT INTO label (name) VALUES (:chores), (:leisure)";
        conn.createQuery(sql)
                .addParameter("chores", "chores")
                .addParameter("leisure", "leisure")
                .executeUpdate();
        ToDo todo = new ToDo ("mow the lawn");
        int originalLabelId = todo.getLabelId();
        todoDao.add(todo);
        todoDao.edit(todo.getId(), "I say something else now.", 2);
        int newLabelId = todoDao.find(todo.getId()).getLabelId();
        assertNotEquals(originalLabelId, newLabelId);
    }

    @Test
    public void itCanDeleteAllToDos(){
        ToDo todoOne = new ToDo("One");
        ToDo todoTwo = new ToDo("Two");
        ToDo todoThree = new ToDo("Three");

        todoDao.add(todoOne);
        todoDao.add(todoTwo);
        todoDao.add(todoThree);

        todoDao.clearAll();
        assertEquals(0, todoDao.all().size());
        assertThat(todoDao.all(), not(hasItems(todoOne, todoTwo, todoThree)));
    }

    @After
    public void tearDown() throws Exception {
        String sql = "TRUNCATE todo CASCADE;";
        conn.createQuery(sql).executeUpdate();
        sql = "ALTER SEQUENCE todo_id_seq RESTART WITH 1;";
        conn.createQuery(sql).executeUpdate();

        sql = "TRUNCATE label CASCADE;";
        conn.createQuery(sql).executeUpdate();
        sql = "ALTER SEQUENCE label_id_seq RESTART WITH 1;";
        conn.createQuery(sql).executeUpdate();
        conn.createQuery("INSERT INTO label (name) VALUES (:none)")
                .addParameter("none", "(none)")
                .executeUpdate();
        conn.close();
    }

    @Ignore
    @Test
    public void itCanReturnToDosWithASpecificLabel(){
        String sql = "INSERT INTO label (name) VALUES (Chores, Leisure)";
        conn.createQuery(sql).executeUpdate();
        ToDo twoDo = new ToDo("foo", 2);
        ToDo threeDo = new ToDo("bar", 1);
        ToDo fourDo = new ToDo("four", 2);
        todoDao.add(twoDo);
        todoDao.add(threeDo);
        todoDao.add(fourDo);

        assertEquals(2, todoDao.filterByLabel(2).size());
        assertThat(todoDao.filterByLabel(2), hasItems(twoDo, fourDo));
    }

}
