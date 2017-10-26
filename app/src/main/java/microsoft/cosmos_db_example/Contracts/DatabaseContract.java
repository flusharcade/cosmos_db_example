package microsoft.cosmos_db_example.Contracts;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mww121 on 26/10/17.
 */

public class DatabaseContract extends BaseContract {
    @SerializedName("id")
    private String id;

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    @SerializedName("_self")
    private String self;

    public String getSelf() { return this.self; }

    public void setSelf(String self) { this.self = self; }

    @SerializedName("_etag")
    private String etag;

    public String getEtag() { return this.etag; }

    public void setEtag(String etag) { this.etag = etag; }

    @SerializedName("_colls")
    private String colls;

    public String getColls() { return this.colls; }

    public void setColls(String colls) { this.colls = colls; }

    @SerializedName("_users")
    private String users;

    public String getUsers() { return this.users; }

    public void setUsers(String users) { this.users = users; }

    @SerializedName("_ts")
    private int ts;

    public int getTs() { return this.ts; }

    public void setTs(int ts) { this.ts = ts; }
}
