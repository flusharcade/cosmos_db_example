package microsoft.cosmos_db_example.Contracts;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mww121 on 26/10/17.
 */

public class DatabasesContract extends BaseContract {
    @SerializedName("Databases")
    private ArrayList<DatabaseContract> databases;

    public ArrayList<DatabaseContract> getDatabases() { return this.databases; }

    public void setDatabases(ArrayList<DatabaseContract> databases) { this.databases = databases; }

    @SerializedName("_count")
    private int count;

    public int getCount() { return this.count; }

    public void setCount(int count) { this.count = count; }
}
