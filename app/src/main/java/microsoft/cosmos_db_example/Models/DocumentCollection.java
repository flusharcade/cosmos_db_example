package microsoft.cosmos_db_example.Models;

/**
 * Created by mww121 on 27/10/17.
 */

public class DocumentCollection {
    private String id;
    private String rid;
    private String self;
    private String etag;
    private String partitionKey;

    public DocumentCollection(String id, String rid, String self, String etag, String partitionKey) {
        this.id = id;
        this.self = self;
        this.etag = etag;
        this.partitionKey = partitionKey;
    }

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public String getRid() { return this.rid; }

    public void setRid(String rid) { this.rid = rid; }

    public String getSelf() { return this.self; }

    public void setSelf(String self) { this.self = self; }

    public String getEtag() { return this.etag; }

    public void setEtag(String etag) { this.etag = etag; }

    public String getPartitionKey() { return this.partitionKey; }

    public void setPartitionKey(String colls) { this.partitionKey = partitionKey; }
}
