package dto;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalDate;

public class User {
    private final SimpleStringProperty USERNAME = new SimpleStringProperty();
    private final SimpleStringProperty ACCOUNT_STATUS = new SimpleStringProperty();
    private final SimpleObjectProperty<LocalDate> CREATED = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<LocalDate> LAST_LOGIN = new SimpleObjectProperty<>();
    private final SimpleStringProperty GRANTED_ROLE = new SimpleStringProperty();
    public User(){

    }

    public User(String USERNAME, String ACCOUNT_STATUS, LocalDate CREATED,  LocalDate LAST_LOGIN, String GRANTED_ROLE){
        this.USERNAME.set(USERNAME);
        this.ACCOUNT_STATUS.set(ACCOUNT_STATUS);
        this.CREATED.set(CREATED);
        this.LAST_LOGIN.set(LAST_LOGIN);
        this.GRANTED_ROLE.set(GRANTED_ROLE);
    }
    public String getUSERNAME(){
        return USERNAME.get();
    }
    public void setUSERNAME(String USERNAME){
        this.USERNAME.set(USERNAME);
    }
    public SimpleStringProperty USERNAMEproperty(){
        return USERNAME;
    }

    public String getACCOUNT_STATUS(){
        return ACCOUNT_STATUS.get();
    }
    public void setACCOUNT_STATUS(String ACCOUNT_STATUS){
        this.ACCOUNT_STATUS.set(ACCOUNT_STATUS);
    }
    public SimpleStringProperty ACCOUNT_STATUSproperty(){
        return ACCOUNT_STATUS;
    }

    public LocalDate getCREATED(){
        return CREATED.get();
    }
    public void setCREATED(LocalDate CREATED){
        this.CREATED.set(CREATED);
    }
    public SimpleObjectProperty<LocalDate> CREATEDproperty(){
        return CREATED;
    }

    public LocalDate getLAST_LOGIN(){
        return LAST_LOGIN.get();
    }
    public void setLAST_LOGIN(LocalDate LAST_LOGIN){
        this.LAST_LOGIN.set(LAST_LOGIN);
    }
    public SimpleObjectProperty<LocalDate> LAST_LOGINproperty(){
        return LAST_LOGIN;
    }
    public String getGRANTED_ROLE(){
        return GRANTED_ROLE.get();
    }
    public void setGRANTED_ROLE(String GRANTED_ROLE){
        this.GRANTED_ROLE.set(GRANTED_ROLE);
    }
    public SimpleStringProperty GRANTED_ROLEproperty(){
        return GRANTED_ROLE;
    }
}