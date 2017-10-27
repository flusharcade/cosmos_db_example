package microsoft.cosmos_db_example.Contracts;

import java.util.ArrayList;

/**
 * Created by mww121 on 27/10/17.
 */

public class PartitionKeyContract {
    private ArrayList<String> paths;

    public ArrayList<String> getPaths() { return this.paths; }

    public void setPaths(ArrayList<String> paths) { this.paths = paths; }

    private String kind;

    public String getKind() { return this.kind; }

    public void setKind(String kind) { this.kind = kind; }
}
