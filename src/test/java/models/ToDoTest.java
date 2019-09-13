package models;

import com.sun.tools.javac.comp.Todo;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
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
        toDo = new ToDo("Walk dog", 1);
    }

    @Test
    public void itInstantiates(){
        assertTrue(toDo instanceof ToDo);
    }

    @Test
    public void itHasContent(){
        Assert.assertEquals("Walk dog", toDo.getContent());
    }
    //    @Test
    //    public void itCanHaveALabelId(){
    //        Assert.assertEquals(1, toDo.getLabelId());
    //    }
    //
    //    @Test
    //    public void itCanNotHaveALabelId(){
    //        ToDo anotherToDo = new ToDo(1, "Walk dog");
    //        Assert.assertEquals(0, anotherToDo.getLabelId());
    //    }

        @After
        public void tearDown(){
            toDo = null;
            toDoClass = null;
        }

}
