public class ToDo {

    private int id;
    private String content;
    private int labelId;

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
