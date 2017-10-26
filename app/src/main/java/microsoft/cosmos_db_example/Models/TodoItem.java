package microsoft.cosmos_db_example.Models;

/**
 * Created by mww121 on 26/10/17.
 */

public class TodoItem {
    private String id;
    private String name;
    private String notes;
    private Boolean done;

    public TodoItem(String id, String name, String notes, Boolean done) {
        super();

        this.id = id;
        this.name = name;
        this.notes = notes;
        this.done = done;
    }

    public Boolean getDone() {
        return done;
    }

    public String getNotes() {
        return notes;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
