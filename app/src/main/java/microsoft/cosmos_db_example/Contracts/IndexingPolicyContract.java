package microsoft.cosmos_db_example.Contracts;

import java.util.ArrayList;

/**
 * Created by mww121 on 27/10/17.
 */

public class IndexingPolicyContract {
    private String indexingMode;

    public String getIndexingMode() { return this.indexingMode; }

    public void setIndexingMode(String indexingMode) { this.indexingMode = indexingMode; }

    private boolean automatic;

    public boolean getAutomatic() { return this.automatic; }

    public void setAutomatic(boolean automatic) { this.automatic = automatic; }

    private ArrayList<IncludedPathContract> includedPaths;

    public ArrayList<IncludedPathContract> getIncludedPaths() { return this.includedPaths; }

    public void setIncludedPaths(ArrayList<IncludedPathContract> includedPaths) { this.includedPaths = includedPaths; }

    private ArrayList<Object> excludedPaths;

    public ArrayList<Object> getExcludedPaths() { return this.excludedPaths; }

    public void setExcludedPaths(ArrayList<Object> excludedPaths) { this.excludedPaths = excludedPaths; }
}
