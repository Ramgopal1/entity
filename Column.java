package model;

public class Column {
    String name;
    String type;
    String nullable;
    String precision;
    String scale;
    String length;
    Boolean id =false;

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }



    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

}
