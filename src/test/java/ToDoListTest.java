import static org.hamcrest.Matchers.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ToDoListTest {

    ToDoList toDoList;
    ToDo toDo;
    Label label;

    @Before
    public void setUp(){
        toDoList = new ToDoList("My List");
        toDo = mock(ToDo.class);
        when(toDo.getId()).thenReturn(1);
//        when(toDo.content).thenReturn("Walk dog");
//        when(toDo.id).thenReturn(9);
    }

    @Test
    public void itHasAName(){
        assertEquals("My List", toDoList.getName());
    }

    @Test
    public void itCanAddAToDo(){
        toDoList.add(toDo);
        assertThat(toDoList.all(), contains(toDo));
    }

    @Test
    public void itCanReturnAllToDos(){
        ToDo toDoTwo = mock(ToDo.class);
        ToDo toDoThree = mock(ToDo.class);
        toDoList.add(toDo);
        toDoList.add(toDoTwo);
        toDoList.add(toDoThree);

        assertEquals(3, toDoList.all().size());
        assertThat(toDoList.all(), hasItems(toDo, toDoTwo, toDoThree));
    }

    @Test
    public void itCanReturnASpecificToDo(){
        toDoList.add(toDo);
        assertEquals(toDo, toDoList.find(1));
    }

    @Test
    public void itCanDeleteAToDo(){
        toDoList.add(toDo);
        toDoList.delete(1);
        assertThat(toDoList.all(), not(contains(toDo)));
    }

    @Test
    public void itCanEditATodo(){
        toDoList.add(toDo);
        toDoList.edit(1, "I say something else now.");
        String content = toDoList.find(1).getContent();
        assertEquals("I say something else now", content);
    }

    @After
    public void tearDown(){
        toDoList = null;
        toDo = null;
        label = null;
    }
}
