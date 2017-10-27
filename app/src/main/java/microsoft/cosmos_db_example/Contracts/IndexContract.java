package microsoft.cosmos_db_example.Contracts;

/**
 * Created by mww121 on 27/10/17.
 */

public class IndexContract {
    private String kind;

    public String getKind() { return this.kind; }

    public void setKind(String kind) { this.kind = kind; }

    private String dataType;

    public String getDataType() { return this.dataType; }

    public void setDataType(String dataType) { this.dataType = dataType; }

    private int precision;

    public int getPrecision() { return this.precision; }

    public void setPrecision(int precision) { this.precision = precision; }
}
