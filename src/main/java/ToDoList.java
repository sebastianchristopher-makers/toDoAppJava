import java.util.ArrayList;

public class ToDoList {

    private String name;
    private ArrayList<ToDo> todos;

    public ToDoList(String name){
        this.name = name;
        todos = new ArrayList<>();
    }

    public void add(ToDo todo){
        todos.add(todo);
    }

    public ArrayList<ToDo> all(){
        return this.todos;
    }

    public ToDo find(int id){
        for(ToDo todo : todos){
            if(todo.getId() == id){
                return todo;
            }
        }
        return null;
    }

    public void delete(int id){
        ToDo toDoToRemove = find(id);
        if(find(id) != null){
            todos.remove(toDoToRemove);
        }
    }

    public String getName() {
        return name;
    }
}
