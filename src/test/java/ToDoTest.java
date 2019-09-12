import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ToDoTest {
    public ToDo toDo;

    @Before
    public void setUp() {
        toDo = new ToDo(1, "Walk dog", 9);
    }

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
        assertEquals(9, toDo.getLabelId());
    }

    @Test
    public void itCanNotHaveALabelId(){
        ToDo anotherToDo = new ToDo(1, "Walk dog");
        assertEquals(0, anotherToDo.getLabelId());
    }


    @After
    public void tearDown(){
        toDo = null;
    }

}
