-- 211HTTT - ATBM05
-- PH1
-- 1. Xem danh sach tai khoan nguoi dung
CREATE OR REPLACE PROCEDURE SP_LIST_USER
AS
RESULTS SYS_REFCURSOR;
BEGIN
    OPEN RESULTS FOR
    select username, account_status, created, last_login, granted_role from dba_users join dba_role_privs on dba_users.username = dba_role_privs.grantee
    where (username like 'NV%' or username like 'SV%')
    ORDER BY USERNAME;
    DBMS_SQL.RETURN_RESULT(RESULTS);
END;
/
--BEGIN
--    SP_LIST_USER();
--END;
--/
-- 2. Xem thong tin ve quyen cua moi user/role tren doi tuong du lieu
CREATE OR REPLACE PROCEDURE SP_LIST_PRIV_USER_ROLE
AS
    RESULTS SYS_REFCURSOR;
BEGIN
    OPEN RESULTS FOR
    SELECT role_tab_privs.role, role_tab_privs.owner, role_tab_privs.table_name, role_tab_privs.column_name, role_tab_privs.privilege
    , role_tab_privs.grantable FROM role_tab_privs join user_role_privs on user_role_privs.username = role_tab_privs.owner WHERE owner = 'C##QLK';
    DBMS_SQL.RETURN_RESULT(RESULTS);
END;
/
BEGIN
    SP_LIST_PRIV_USER_ROLE();
END;
/
-- 2. Tim kiem user hoac role bat ky de xem thong tin ve quyen
CREATE OR REPLACE PROCEDURE SP_SEARCH_USER (
    INP_NAME IN VARCHAR2
    )
AS
    RESULTS SYS_REFCURSOR;
BEGIN
    OPEN RESULTS FOR
    SELECT * 
    FROM dba_users u
    JOIN dba_role_privs rp ON u.username = rp.grantee 
    WHERE (u.username LIKE 'NV%' OR u.username LIKE 'SV%') 
    AND u.username LIKE '%' || INP_NAME || '%';
    DBMS_SQL.RETURN_RESULT(RESULTS);
END;
/
CREATE OR REPLACE PROCEDURE SP_SEARCH_ROLE (
    INP_NAME IN VARCHAR2
    )
AS
    RESULTS SYS_REFCURSOR;
BEGIN
    OPEN RESULTS FOR
    SELECT * FROM role_tab_privs r WHERE r.owner = 'C##QLK' AND r.role LIKE  '%' || INP_NAME || '%';
    DBMS_SQL.RETURN_RESULT(RESULTS);
END;
/

-- 3.1. Tao moi user hoac role
CREATE OR REPLACE PROCEDURE SP_CREATE_USER(
    INP_NAME IN VARCHAR2,
    INP_PASSWORD IN VARCHAR2
    )
AS
    TMP_COUNT INT;
BEGIN
    SELECT COUNT(*) INTO TMP_COUNT 
    FROM ALL_USERS 
    WHERE USERNAME = INP_NAME;
    IF(TMP_COUNT != 0) THEN
        BEGIN
            RAISE_APPLICATION_ERROR(-20000,N'USER DA TON TAII!'); 
        END;
    ELSE 
    BEGIN
        
        EXECUTE IMMEDIATE 'CREATE USER ' || INP_NAME || ' IDENTIFIED BY ' || INP_PASSWORD;
        EXECUTE IMMEDIATE 'GRANT CREATE SESSION TO ' || INP_NAME;
        EXECUTE IMMEDIATE 'ALTER USER ' || INP_NAME || ' QUOTA ' || 10 || 'M ON ' || 'SYSTEM';
        END;
    END IF;
END;
/
--BEGIN
--    SP_CREATE_USER('USER_TEST', 'Atbm2024');
--END;
--/
CREATE OR REPLACE PROCEDURE SP_CREATE_ROLE(
    INP_ROLE IN VARCHAR2
)
AS
    ROLE_COUNT NUMBER;
BEGIN
    SELECT COUNT(*) INTO ROLE_COUNT FROM DBA_ROLES WHERE ROLE = INP_ROLE;
    IF role_count = 0 THEN
        
        EXECUTE IMMEDIATE 'CREATE ROLE ' || INP_ROLE;
        DBMS_OUTPUT.PUT_LINE('Role ' || INP_ROLE || ' TAO THANH CONG.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Role ' || INP_ROLE || ' DA TON TAI.');
        BEGIN
            RAISE_APPLICATION_ERROR(-20000,N'ROLE DA TON TAI!'); 
        END;
    END IF;
END;
/

-- 3.2. Xoa user hoac role
CREATE OR REPLACE PROCEDURE SP_DROP_USER(
    INP_NAME IN NVARCHAR2
)
AS
    TMP_COUNT INT;
BEGIN
    SELECT COUNT(*) INTO TMP_COUNT 
    FROM ALL_USERS 
    WHERE USERNAME = INP_NAME;
    
    IF(TMP_COUNT != 0) THEN
        BEGIN
            
            EXECUTE IMMEDIATE('DROP USER '|| INP_NAME);
        END;
    ELSE 
         RAISE_APPLICATION_ERROR(-20000,N'USER KHONG TON TAI'); 
    END IF;
END;
/
CREATE OR REPLACE PROCEDURE  SP_DROP_ROLE(
    ROLENAME IN VARCHAR2
)
AS
    TMP_COUNT INT;
BEGIN
    SELECT COUNT(*) INTO TMP_COUNT 
    FROM DBA_ROLES 
    WHERE ROLE = ROLENAME;
    
    IF(TMP_COUNT != 0) THEN
        BEGIN
            
            EXECUTE IMMEDIATE('DROP ROLE '|| ROLENAME);
        END;
    ELSE 
        BEGIN
            RAISE_APPLICATION_ERROR(-20000,N'ROLE KHONG TON TAI!'); 
        END;
    END IF;
END;
/
-- Tao tai khoan cho NHANSU
CREATE OR REPLACE PROCEDURE SP_EXECUTE_CREATE_NHANSU AS
    MANV_TEMP NHANSU.MANV%TYPE;
    INP_PASSWORD VARCHAR2(50);
    USER_COUNT NUMBER;
BEGIN
    FOR rec IN (SELECT MANV FROM NHANSU)
    LOOP
        MANV_TEMP := rec.MANV;
        INP_PASSWORD := 'Rack' || LOWER(MANV_TEMP);

        SELECT COUNT(*)
        INTO USER_COUNT
        FROM DBA_USERS
        WHERE USERNAME = MANV_TEMP;

        IF USER_COUNT = 0 THEN
            SP_CREATE_USER(MANV_TEMP, INP_PASSWORD, 'SYSTEM', 10);
        ELSE
            DBMS_OUTPUT.PUT_LINE('User ' || MANV_TEMP || ' da ton tai. Huy tao');
        END IF;
    END LOOP;
END;
/
--BEGIN
--    SP_EXECUTE_CREATE_NHANSU();
--END;
--/