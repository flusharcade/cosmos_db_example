package microsoft.cosmos_db_example.Contracts;

/**
 * Created by mww121 on 27/10/17.
 */

public class DocumentCollectionContract {
    private String id;

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    private IndexingPolicyContract indexingPolicy;

    public IndexingPolicyContract getIndexingPolicy() { return this.indexingPolicy; }

    public void setIndexingPolicy(IndexingPolicyContract indexingPolicy) { this.indexingPolicy = indexingPolicy; }

    private PartitionKeyContract partitionKey;

    public PartitionKeyContract getPartitionKey() { return this.partitionKey; }

    public void setPartitionKey(PartitionKeyContract partitionKey) { this.partitionKey = partitionKey; }

    private String _rid;

    public String getRid() { return this._rid; }

    public void setRid(String _rid) { this._rid = _rid; }

    private int _ts;

    public int getTs() { return this._ts; }

    public void setTs(int _ts) { this._ts = _ts; }

    private String _self;

    public String getSelf() { return this._self; }

    public void setSelf(String _self) { this._self = _self; }

    private String _etag;

    public String getEtag() { return this._etag; }

    public void setEtag(String _etag) { this._etag = _etag; }

    private String _docs;

    public String getDocs() { return this._docs; }

    public void setDocs(String _docs) { this._docs = _docs; }

    private String _sprocs;

    public String getSprocs() { return this._sprocs; }

    public void setSprocs(String _sprocs) { this._sprocs = _sprocs; }

    private String _triggers;

    public String getTriggers() { return this._triggers; }

    public void setTriggers(String _triggers) { this._triggers = _triggers; }

    private String _udfs;

    public String getUdfs() { return this._udfs; }

    public void setUdfs(String _udfs) { this._udfs = _udfs; }

    private String _conflicts;

    public String getConflicts() { return this._conflicts; }

    public void setConflicts(String _conflicts) { this._conflicts = _conflicts; }
}
