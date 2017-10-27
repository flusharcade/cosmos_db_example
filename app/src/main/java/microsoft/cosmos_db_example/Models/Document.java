package microsoft.cosmos_db_example.Models;

/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

public class Document {
    private String id;
    private String rid;
    private String self;
    private String etag;
    private String colls;
    private String users;
    private int ts;

    public Document(String id, String rid, String self, String etag, String colls, String users, int ts) {
        this.id = id;
        this.self = self;
        this.etag = etag;
        this.colls = colls;
        this.users = users;
        this.ts = ts;
    }

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public String getRid() { return this.rid; }

    public void setRid(String rid) { this.rid = rid; }

    public String getSelf() { return this.self; }

    public void setSelf(String self) { this.self = self; }

    public String getEtag() { return this.etag; }

    public void setEtag(String etag) { this.etag = etag; }

    public String getColls() { return this.colls; }

    public void setColls(String colls) { this.colls = colls; }

    public String getUsers() { return this.users; }

    public void setUsers(String users) { this.users = users; }

    public int getTs() { return this.ts; }

    public void setTs(int ts) { this.ts = ts; }
}