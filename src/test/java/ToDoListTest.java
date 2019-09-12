import static org.hamcrest.Matchers.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ToDoListTest {

    ToDoList toDoList;
    ToDo toDo;

    @Before
    public void setUp(){
        toDoList = new ToDoList("My List");
        toDo = mock(ToDo.class);
        when(toDo.getId()).thenReturn(1);
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
    public void itCanEditAToDo(){
        toDoList.add(toDo);
        toDoList.edit(1, "I say something else now.");
        verify(toDo, times(1)).edit("I say something else now.");
        when(toDo.getContent()).thenReturn("I say something else now.");
        String content = toDoList.find(1).getContent();
        assertEquals("I say something else now.", content);
    }

    @Test
    public void itCanReturnToDosWithASpecificLabel(){
        ToDo toDoTwo = mock(ToDo.class);
        ToDo toDoThree = mock(ToDo.class);
        when(toDo.getLabelId()).thenReturn(1);
        when(toDoTwo.getLabelId()).thenReturn(2);
        when(toDoThree.getLabelId()).thenReturn(1);
        toDoList.add(toDo);
        toDoList.add(toDoTwo);
        toDoList.add(toDoThree);

        ArrayList<ToDo> labelsWithOne = toDoList.filterByLabel(1);

        for(ToDo todo : labelsWithOne){
            System.out.println(todo.getLabelId());
        }
        assertEquals(2, labelsWithOne.size());

        assertThat(labelsWithOne, hasItems(toDo, toDoThree));
    }

    @After
    public void tearDown(){
        toDoList = null;
        toDo = null;
    }
}
