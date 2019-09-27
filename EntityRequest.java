package model;

import java.io.Serializable;

public class EntityRequest implements Serializable {
    private String db;
    private String schema;
    private String table;
    private String filePath;

    public String getDb() {
        return db;
    }

    public String getSchema() {
        return schema;
    }

    public String getTable() {
        return table;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public EntityRequest(String db, String schema, String table, String filePath) {
        this.db = db;
        this.schema = schema;
        this.table = table;
        this.filePath = filePath;
    }

    public EntityRequest(){}

    @Override public String toString() {
        return "EntityRequest{" +
            "db='" + db + '\'' +
            ", schema='" + schema + '\'' +
            ", table='" + table + '\'' +
            ", filePath='" + filePath + '\'' +
            '}';
    }
}
