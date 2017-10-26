package microsoft.cosmos_db_example.Models;

import java.util.Objects;

/**
 * Created by mww121 on 26/10/17.
 */

//@Document(collection = "mycollection")
public class TodoItem {
    private String id;
    private String description;
    private String owner;
    private boolean finished;

    public TodoItem() {
    }

    public TodoItem(String id, String description, String owner) {
        this.description = description;
        this.id = id;
        this.owner = owner;
        this.finished = false;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinish(boolean finished) {
        this.finished = finished;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || !(o instanceof TodoItem)) {
            return false;
        }
        final TodoItem group = (TodoItem) o;
        return this.getDescription().equals(group.getDescription())
                && this.getOwner().equals(group.getOwner())
                && this.getID().equals(group.getID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, id, owner);
    }
}