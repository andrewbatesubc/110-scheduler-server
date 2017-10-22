package com.dao;

import org.springframework.stereotype.Component;

@Component
public class SQLStatements {

    private final String tableName = "Schedules";

    public String createTableSQL() {
        return "CREATE TABLE IF NOT EXISTS " + tableName + "("
                        + "TAName varchar(255) NOT NULL UNIQUE,"
                        + "Monday varchar(255) NOT NULL,"
                        + "Tuesday varchar(255) NOT NULL,"
                        + "Wednesday varchar(255) NOT NULL,"
                        + "Thursday varchar(255) NOT NULL,"
                        + "Friday varchar(255) NOT NULL,"
                        + "Saturday varchar(255) NOT NULL,"
                        + "Sunday varchar(255) NOT NULL,"
                        + "PRIMARY KEY (TAName))";
    }

    public String dropTableSQL() {
        return "DROP TABLE IF EXISTS " + tableName;
    }
}
