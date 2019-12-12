package fabflix.models;

public class MetadataModel {
   public String table_name;
    public String column_name;
   public  String field_type;
public MetadataModel(String table, String col, String type){
this.table_name=table;
this.column_name=col;
this.field_type=type;
}
public MetadataModel(MetadataModel data){
    this.table_name=data.table_name;
    this.column_name=data.column_name;
    this.field_type=data.field_type;
}

}
