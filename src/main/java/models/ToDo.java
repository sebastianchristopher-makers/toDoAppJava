package models;

import java.util.Objects;

public class ToDo {

    private int id;
    private String content;
    private int labelId;

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

    public ToDo(int id, String content, int labelId){
        this.id = id;
        this.content = content;
        this.labelId = labelId;
    }

    public ToDo(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public void edit(String content){
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
