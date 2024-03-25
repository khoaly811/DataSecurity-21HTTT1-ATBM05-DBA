package dto;
import javafx.beans.property.SimpleStringProperty;

public class Role {
    private final SimpleStringProperty ROLE = new SimpleStringProperty();
    private final SimpleStringProperty OWNER = new SimpleStringProperty();
    private final SimpleStringProperty TABLE_NAME = new SimpleStringProperty();
    private final SimpleStringProperty COLUMN_NAME = new SimpleStringProperty();
    private final SimpleStringProperty PRIVILEGE = new SimpleStringProperty();
    private final SimpleStringProperty GRANTABLE = new SimpleStringProperty();

    public Role(){

    }

    public Role(String ROLE, String OWNER, String TABLE_NAME,  String COLUMN_NAME, String PRIVILEGE, String GRANTABLE){
        this.ROLE.set(ROLE);
        this.OWNER.set(OWNER);
        this.TABLE_NAME.set(TABLE_NAME);
        this.COLUMN_NAME.set(COLUMN_NAME);
        this.PRIVILEGE.set(PRIVILEGE);
        this.GRANTABLE.set(GRANTABLE);
    }
    public String getROLE(){
        return ROLE.get();
    }
    public void setROLE(String ROLE){
        this.ROLE.set(ROLE);
    }
    public SimpleStringProperty ROLEproperty(){
        return ROLE;
    }

    public String getOWNER(){
        return OWNER.get();
    }
    public void setOWNER(String OWNER){
        this.OWNER.set(OWNER);
    }
    public SimpleStringProperty OWNERproperty(){
        return OWNER;
    }

    public String getTABLE_NAME(){
        return TABLE_NAME.get();
    }
    public void setTABLE_NAME(String TABLE_NAME){
        this.TABLE_NAME.set(TABLE_NAME);
    }
    public SimpleStringProperty TABLE_NAMEproperty(){
        return TABLE_NAME;
    }

    public String getCOLUMN_NAME(){
        return COLUMN_NAME.get();
    }
    public void setCOLUMN_NAME(String COLUMN_NAME){
        this.COLUMN_NAME.set(COLUMN_NAME);
    }
    public SimpleStringProperty COLUMN_NAMEproperty(){
        return COLUMN_NAME;
    }

    public String getPRIVILEGE(){
        return PRIVILEGE.get();
    }
    public void setPRIVILEGE(String PRIVILEGE){
        this.PRIVILEGE.set(PRIVILEGE);
    }
    public SimpleStringProperty PRIVILEGEproperty(){
        return PRIVILEGE;
    }

    public String getGRANTABLE(){
        return GRANTABLE.get();
    }
    public void setGRANTABLE(String GRANTABLE){
        this.GRANTABLE.set(GRANTABLE);
    }
    public SimpleStringProperty GRANTABLEproperty(){
        return GRANTABLE;
    }
}