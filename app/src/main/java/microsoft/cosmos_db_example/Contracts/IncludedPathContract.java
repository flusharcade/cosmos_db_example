package microsoft.cosmos_db_example.Contracts;

import java.util.ArrayList;

/**
 * Created by mww121 on 27/10/17.
 */

public class IncludedPathContract {
    private String path;

    public String getPath() { return this.path; }

    public void setPath(String path) { this.path = path; }

    private ArrayList<IndexContract> indexes;

    public ArrayList<IndexContract> getIndexes() { return this.indexes; }

    public void setIndexes(ArrayList<IndexContract> indexes) { this.indexes = indexes; }
}
