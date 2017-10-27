package microsoft.cosmos_db_example.Contracts;

import java.util.ArrayList;

/**
 * Created by mww121 on 27/10/17.
 */

public class DocumentsContract {
    private String _rid;

    public String getRid() { return this._rid; }

    public void setRid(String _rid) { this._rid = _rid; }

    private ArrayList<Object> Documents;

    public ArrayList<Object> getDocuments() { return this.Documents; }

    public void setDocuments(ArrayList<Object> Documents) { this.Documents = Documents; }

    private int _count;

    public int getCount() { return this._count; }

    public void setCount(int _count) { this._count = _count; }

}
