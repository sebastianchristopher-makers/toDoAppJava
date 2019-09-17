package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ToDo {

    private int id;
    private String content;
    private int labelId;
//    private static ArrayList<models.ToDo> todos = new ArrayList<>(); // moved to db

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDo toDo = (ToDo) o;
        return id == toDo.id &&
                labelId == toDo.labelId &&
                Objects.equals(content, toDo.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, labelId);
    }

    public ToDo(String content, int labelId){
        this.content = content;
        this.labelId = labelId;
    }

    public ToDo(String content) {
        this.content = content;
        this.labelId = 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public int getLabelId(){
        return labelId;
    }

}
