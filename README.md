# Faculty of Information Technology Management System
## Project Overview
This project aims to design and implement a comprehensive data management system for a faculty, utilizing various technologies such as PLSQL, Advanced PLSQL, JavaFX, and Object-Oriented Programming (OOP) principles. The project covers data security and database design with Oracle, PLSQL implementation, and Java application development.

## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

## Milestones

### 1. Stored Procedures Implementation (Day 1-2)
- Khoa Ly: writes stored procedures (SPs) allowing DBA to grant role to a user, grant privileges to a specific user or a role and revoke them from a user/role (allow to choose whether WITH GRANT OPTION or not and grant specific columns as SELECT and UPDATE privileges).
- Tin Vo: writes SPs for creating, updating, and deleting a user or a role.
- Nhan Le: writes SPs related to list and search the username, all privileges of each user or role on database objects in FIT Management System.
### 2. JavaFX Application Build (Day 3-4)
- Khoa Ly: Develop Java data transfer objects for User and Role using OOP principles and connect Oracle with JDBC.
- Tin Vo: Implement the log-in feature (do not allow other users who are not in DBA roles to access the system) and set up files, including API Controllers.
- Nhan Le: Design prototyping UI with Figma and build the structure of GUI with Scene Builder.
### 3. JavaFX Application Development (Day 5)
- Khoa Ly: Implement features related to granting/revoking privileges.
- Tin Vo: Implement features related to creating/updating/deleting users or roles.
- Nhan Le: Implement features related to listing/searching privileges of users or roles.
### 4. Testing and Reports (Day 6)
- Khoa Ly: Prepare presentation for the project.
- Tin Vo: Prepare instructions to build the application.
- Nhan Le: Test the application.
