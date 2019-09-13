package models;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LabelTest {
    Label label;

    @Before
    public void setUp(){
        label = new Label(1, "Chores");
    }

    @Test
    public void itHasAName(){
        Assert.assertEquals("Chores", label.getName());
    }

    @Test
    public void itHasAnId(){
        Assert.assertEquals(1, label.getId());
    }

    @After
    public void tearDown(){
        label = null;
    }
}
