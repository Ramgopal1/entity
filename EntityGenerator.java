package controller;

import model.Column;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EntityGenerator {
    public String entity;
    public String id;
    public String generatedClassName;
    public String table;
    public String schema;
    public int lineno = 0;
    public List<Column> columns = new ArrayList<>();
    public  boolean flag=false;



    public String getGeneratedClassName() {
        return generatedClassName;
    }

    public void setGeneratedClassName(String generatedClassName) {
        this.generatedClassName = generatedClassName;
    }

    public Map<String, String> types = new HashMap<String, String>() {
        {
            put("varchar", "String");
            put("int", "Integer");
            put("datetime2", "Calendar");
            put("char", "Character");
            put("numeric", "BigDecimal");
            put("bigint", "Long");
            put("datetime", "Calendar");
            put("nvarchar","String");
            put("date","Calendar");
            put("smallint","Integer");
            put("tinyint","Integer");
        }
    };
    public void generateEntity(String inputFile) throws IOException {

        try {
            File f = new File(inputFile);

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";

            while ((readLine = b.readLine()) != null) {
                lineno++;
                String[] strings = readLine.trim().split(" ");

                if (strings[0].equals("CREATE")&&flag==false) {
                    String[] str1 = strings[2].split("\\.");
                    schema = str1[0].substring(1, str1[0].length() - 1).toUpperCase();
                    table = str1[1].substring(1, str1[1].length() - 2).toUpperCase();
                    setGeneratedClassName(table.substring(0, 1) + table.substring(1).toLowerCase());
                } else if (strings[0].startsWith("[")&&flag==false) {
                    Column column = new Column();
                    column.setName(strings[0].substring(1, strings[0].indexOf(']')));
                    column.setType(types.get(strings[1].substring(1, strings[1].indexOf(']'))));
                    if(strings[1].contains("(")) {
                        column.setLength(strings[1].substring(strings[1].indexOf("(") + 1, strings[1].indexOf(")")));
                    }
                    if (column.getType() == "BigDecimal") {
                        if(strings[1].contains(",")) {
                            column.setPrecision(strings[1].substring(strings[1].indexOf('(') + 1, strings[1].indexOf(',')));
                            column.setScale(strings[2].substring(0, strings[2].indexOf(')')));
                        }
                        else if(strings[1].contains("(")) {
                            column.setLength(strings[1].substring(strings[1].indexOf("(") + 1, strings[1].indexOf(")")));
                        }

                    }

                    if (strings[2].equals("NOT")) {
                        column.setNullable("false");
                    }

                    columns.add(column);

                }

                else if(strings[0].equals("CONSTRAINT")||flag==true){
                        flag=true;
                        if(strings[0].startsWith("[")){
                            id=strings[0].substring(strings[0].indexOf('[')+1,strings[0].indexOf(']'));
                        }
                }

            }
            flag=false;
        }
        catch (FileNotFoundException e) {
            throw e;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        entity = "import lombok.*;\n" +
                "\n" +
                "import javax.persistence.*;\n" +
                "import java.io.Serializable;\n" +
                "import java.math.BigDecimal;\n" +
                "import java.util.Calendar;\n" +
                "\n" +
                "\n" +
                "import static javax.persistence.GenerationType.SEQUENCE;\n" +
                "\n" +
                "@Builder\n" +
                "@Getter\n" +
                "@Setter\n" +
                "@AllArgsConstructor\n" +
                "@NoArgsConstructor\n" +
                "@ToString\n" +
                "@Entity\n" +
                "@Table(name = " + "\"" + table + "\", schema = \"" + schema + "\")\n" +
                "public class " + generatedClassName + " implements Serializable {\n" +
                "    private static final long serialVersionUID = 1L;\n" +
                "\n";

        for(Column column:columns){
            if(column.getName().equalsIgnoreCase(id)){
                entity=entity+"    @Id\n"+"    @Column(name = \""+column.getName().toUpperCase()+"\", unique = true, nullable = false)\n";
                entity=entity+"    private "+column.getType()+" "+toCamelCase(column.getName())+";\n\n";
            }
        }

        for (Column column : columns) {
            if (column.getName().equalsIgnoreCase(id)) {

            }
            else {
                if (column.getType() == "BigDecimal") {
                    if (column.getNullable() == "false") {
                        entity = entity + forDecimalAndNotNull(column.getName(), column.getPrecision(), column.getScale(), column.getType(), column.getLength());
                    } else {
                        entity = entity + forDecimal(column.getName(), column.getPrecision(), column.getScale(), column.getType(),column.getLength());
                    }

                } else {
                    if (column.getNullable() == "false") {
                        entity = entity + forColumnAndNotNull(column.getName(), column.getType(),column.getLength());
                    } else {
                        entity = entity + forColumn(column.getName(), column.getType(),column.getLength());
                    }
                }
            }

        }
        entity = entity + "\n}";
//         System.out.println(entity);

        File file = new File("C:/EntityGenerator/JavaEntities/" + generatedClassName + ".java");
        if (!file.createNewFile()) {
            file.delete();
        }
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writer.write(entity);
        writer.close();
        columns.clear();
        entity="";
        System.out.println("Pring the enitity"+entity);
    }

    public String toCamelCase(String s1){
        String temp="";
        String[] strings=s1.split("_");
        temp=strings[0].toLowerCase();
        if(strings.length>1) {
            for (int i = 1; i<strings.length; i++) {
                temp = temp + strings[i].substring(0, 1).toUpperCase() + strings[i].substring(1).toLowerCase();
            }
        }
        return  temp;
    }
    public String forColumn(String name,String type,String length){

        String temp= "    @Column (name = \"" + name + "\")\n    private " + type + " " + toCamelCase(name) + ";\n\n";

        return  temp;
    }
    public String forColumnAndNotNull(String name,String type,String length){
        String temp="    @Column (name = \""+name+"\", nullable = false)\n    private "+type+" "+toCamelCase(name)+";\n\n";
        return  temp;
    }

    public String forDecimal(String name, String precision, String scale,String type,String length){
        String temp="    @Column (name = \""+name+"\", precision = "+precision+", scale = "+scale+")\n    private "+type+" "+toCamelCase(name)+";\n\n";
        return  temp;
    }
    public String forDecimalAndNotNull(String name, String precision, String scale,String type,String length){
        String temp="    @Column (name = \""+name+"\", precision = "+precision+", scale = "+scale+", nullable = false)\n    private "+type+" "+toCamelCase(name)+";\n\n";
        return  temp;
    }
}


