package com.dao;

import org.springframework.stereotype.Component;

/**
 * Store stored (lol) procedures to query whichever SQL database we choose. Used to visibility and security.
 */
@Component
public class SQLStoredProcedures {

    private final String tableName = "Schedules";

    public final String createTableProcedureName = "ccpo_createTable";
    private String createTableCreationProcedure() {
        return "CREATE PROCEDURE " + createTableProcedureName + " "
                        + "AS "
                        + "BEGIN "
                        + "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                        + "TAName varchar(255) NOT NULL,"
                        + "Monday varchar(255) NOT NULL,"
                        + "Tuesday varchar(255) NOT NULL,"
                        + "Wednesday varchar(255) NOT NULL,"
                        + "Thursday varchar(255) NOT NULL,"
                        + "Friday varchar(255) NOT NULL,"
                        + "Saturday varchar(255) NOT NULL,"
                        + "Sunday varchar(255) NOT NULL,"
                        + "PRIMARY KEY (TAName)) "
                + "END";
    }
}
