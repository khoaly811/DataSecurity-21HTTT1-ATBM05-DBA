CREATE USER C##QLK IDENTIFIED BY Rack12345678;
GRANT CREATE SESSION TO C##QLK;
GRANT ALTER SESSION TO C##QLK;
GRANT select any dictionary  to C##QLK;
grant create session, create view, alter session, create sequence to C##QLK;
grant create synonym, create database link, resource, unlimited tablespace  to C##QLK;
grant create user, create role, alter user, alter any role,drop user, drop any role  to C##QLK;
grant create trigger  to C##QLK;
grant execute on sys.DBMS_SESSION to C##QLK;
grant execute on DBMS_CRYPTO  to C##QLK;
GRANT SELECT ON DBA_ROLES TO C##QLK;
GRANT SELECT ANY TABLE TO C##QLK;
GRANT UPDATE ANY TABLE TO C##QLK;
GRANT INSERT ANY TABLE TO C##QLK;
GRANT DELETE ANY TABLE TO C##QLK;
grant select on dba_sys_privs to C##QLK;
grant select on dba_tab_privs to C##QLK;
grant select on dba_tables to C##QLK;
grant DBA to C##QLK;
GRANT CREATE PROCEDURE TO C##QLK;
GRANT ALTER any PROCEDURE TO C##QLK;
GRANT CREATE SESSION TO C##QLK;
GRANT ALL PRIVILEGES TO C##QLK;
GRANT SYSDBA TO C##QLK;
GRANT EXECUTE ON DBMS_RLS TO C##QLK;
GRANT EXECUTE ON DBMS_CRYPTO TO C##QLK;
GRANT SELECT ON DBA_FGA_AUDIT_TRAIL TO C##QLK;
GRANT CONNECT, RESOURCE, DBA TO C##QLK;
GRANT UNLIMITED TABLESPACE TO C##QLK;
GRANT SELECT ON DBA_AUDIT_POLICIES TO C##QLK;
GRANT SELECT ON DBA_POLICIES TO C##QLK;

GRANT ALL PRIVILEGES TO C##QLK WITH ADMIN OPTION;
GRANT SELECT ON SYS.V_$INSTANCE TO C##QLK;
GRANT SELECT ON DBA_VIEWS TO C##QLK; 
GRANT SELECT ON DBA_TAB_COLUMNS TO C##QLK;

GRANT SELECT ON DBA_ROLES TO C##QLK; 
GRANT SELECT ON DBA_SYS_PRIVS TO C##QLK; 
GRANT SELECT ON DBA_TAB_PRIVS TO C##QLK; 
GRANT SELECT ON ROLE_TAB_PRIVS TO C##QLK; 
GRANT SELECT ON DBA_ROLE_PRIVS TO C##QLK;  
GRANT SELECT ON DBA_USERS TO C##QLK; 
GRANT SELECT ON DBA_COL_PRIVS TO C##QLK; 
GRANT CREATE ANY PROCEDURE TO C##QLK;
GRANT EXECUTE ANY PROCEDURE TO C##QLK;
GRANT DROP ANY PROCEDURE TO C##QLK;