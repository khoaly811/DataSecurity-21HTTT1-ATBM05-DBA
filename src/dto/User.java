package dto;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalDate;

public class User {
    private final SimpleStringProperty USERNAME = new SimpleStringProperty();
    private final SimpleStringProperty ACCOUNT_STATUS = new SimpleStringProperty();
    private final SimpleObjectProperty<LocalDate> CREATED = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<LocalDate> LAST_LOGIN = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<LocalDate> PASSWORD_CHANGE_DATE = new SimpleObjectProperty<>();

    public User(){

    }

    public User(String USERNAME, String ACCOUNT_STATUS, LocalDate CREATED,  LocalDate LAST_LOGIN, LocalDate PASSWORD_CHANGE_DATE){
        this.USERNAME.set(USERNAME);
        this.ACCOUNT_STATUS.set(ACCOUNT_STATUS);
        this.CREATED.set(CREATED);
        this.LAST_LOGIN.set(LAST_LOGIN);
        this.PASSWORD_CHANGE_DATE.set(PASSWORD_CHANGE_DATE);
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
    public LocalDate getPASSWORD_CHANGE_DATE(){
        return PASSWORD_CHANGE_DATE.get();
    }
    public void setPASSWORD_CHANGE_DATE(LocalDate PASSWORD_CHANGE_DATE){
        this.PASSWORD_CHANGE_DATE.set(PASSWORD_CHANGE_DATE);
    }
    public SimpleObjectProperty<LocalDate> PASSWORD_CHANGE_DATEproperty(){
        return PASSWORD_CHANGE_DATE;
    }
}