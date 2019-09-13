import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ToDo {

    private int id;
    private String content;
    private int labelId;
    private static ArrayList<ToDo> todos = new ArrayList<>();

    public static void reset(){ //temporary helper method to pass test until replace arraylist with database
        todos = null;
        todos = new ArrayList<>();
    }

    public static void add(ToDo todo){
        todos.add(todo);
    }

    public static List<ToDo> all(){
        return todos;
    }

    public static ToDo find(int id){
        for(ToDo todo : todos){
            if(todo.getId() == id){
                return todo;
            }
        }
        return null;
    }

    public static void delete(int id){
        ToDo toDoToRemove = find(id);
        if(find(id) != null){
            todos.remove(toDoToRemove);
        }
    }

    public static void edit(int id, String content){
        ToDo toDoToEdit = find(id);
        if(find(id) != null){
            toDoToEdit.content = content;
        }
    }


    public static  ArrayList<ToDo> filterByLabel(int labelId){
        ArrayList<ToDo> filteredList = new ArrayList<>();
        for(ToDo todo : todos){
            if(todo.getLabelId() == labelId){
                filteredList.add(todo);
            }
        }
        return filteredList;
    }

    public ToDo(int id, String content, int labelId){
        this.id = id;
        this.content = content;
        this.labelId = labelId;
    }

    public ToDo(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getLabelId(){
        return labelId;
    }

}
