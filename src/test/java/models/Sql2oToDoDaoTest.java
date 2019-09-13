package models;
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
        ToDo todo = new ToDo ("mow the lawn");
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
        todoDao.edit(todo, "I say something else now.");
        assertNotEquals(originalToDoContent, todo.getContent());
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
        conn.close();
    }

//    @Test
//    public void itCanReturnToDosWithASpecificLabel(){
//        ToDo toDoTwo = new ToDo(2, "foo", 2);
//        ToDo toDoThree = new ToDo(3, "bar", 1);
//        toDoClass.add(toDo);
//        toDoClass.add(toDoTwo);
//        toDoClass.add(toDoThree);
//
//        ArrayList<ToDo> labelsWithOne = toDoClass.filterByLabel(1);
//
//        assertEquals(2, labelsWithOne.size());
//        assertThat(labelsWithOne, Matchers.hasItems(toDo, toDoThree));
//    }
//
}