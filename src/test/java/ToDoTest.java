import com.sun.tools.javac.comp.Todo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class ToDoTest {
    public ToDo toDo;
    ToDo toDoClass;

    @Before
    public void setUp() {
        toDoClass.reset();
        toDo = new ToDo(1, "Walk dog", 1);
    }

    // class methods

    @Test
    public void itCanAddAToDo(){
        toDoClass.add(toDo);
        assertThat(toDoClass.all(), contains(toDo));
    }

    @Test
    public void itCanReturnAllToDos(){
        ToDo toDoTwo = new ToDo(2, "Content");
        ToDo toDoThree = new ToDo(3, "More content");
        toDoClass.add(toDo);
        toDoClass.add(toDoTwo);
        toDoClass.add(toDoThree);

        assertEquals(3, toDoClass.all().size());
        assertThat(toDoClass.all(), hasItems(toDo, toDoTwo, toDoThree));
    }

    @Test
    public void itCanReturnASpecificToDo(){
        toDoClass.add(toDo);
        assertEquals(toDo, toDoClass.find(1));
    }

    @Test
    public void itCanDeleteAToDo(){
        toDoClass.add(toDo);
        toDoClass.delete(1);
        assertThat(toDoClass.all(), not(contains(toDo)));
    }

    @Test
    public void itCanEditAToDo(){
        toDoClass.add(toDo);
        toDoClass.edit(1, "I say something else now.");
        String content = toDoClass.find(1).getContent();
        assertEquals("I say something else now.", content);
    }

    @Test
    public void itCanReturnToDosWithASpecificLabel(){
        ToDo toDoTwo = new ToDo(2, "foo", 2);
        ToDo toDoThree = new ToDo(3, "bar", 1);
        toDoClass.add(toDo);
        toDoClass.add(toDoTwo);
        toDoClass.add(toDoThree);

        ArrayList<ToDo> labelsWithOne = toDoClass.filterByLabel(1);

        assertEquals(2, labelsWithOne.size());
        assertThat(labelsWithOne, hasItems(toDo, toDoThree));
    }

    // instance methods

    @Test
    public void itHasAnId(){
        assertEquals(1, toDo.getId());
    }

    @Test
    public void itHasContent(){
        assertEquals("Walk dog", toDo.getContent());
    }
    @Test
    public void itCanHaveALabelId(){
        assertEquals(1, toDo.getLabelId());
    }

    @Test
    public void itCanNotHaveALabelId(){
        ToDo anotherToDo = new ToDo(1, "Walk dog");
        assertEquals(0, anotherToDo.getLabelId());
    }

    @After
    public void tearDown(){
        toDo = null;
        toDoClass = null;
    }

}
