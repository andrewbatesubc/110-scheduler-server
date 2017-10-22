package com.dao;

import org.springframework.stereotype.Component;

@Component
public class PostgresSQLStatements {

    private final String tableName = "Schedules";

    public String createTableSQL() {
        return "CREATE TABLE IF NOT EXISTS " + tableName + "("
                        + "TAName varchar(255) UNIQUE,"
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

    private String upsertSQL = "INSERT INTO " + tableName + "(taName, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
            "ON CONFLICT (taName) DO UPDATE SET Monday = ?, Tuesday = ?, Wednesday = ?, Thursday = ?, Friday = ?, Saturday = ?, Sunday = ?";
    public String createUpsertSQL(final String taName, final String[] daysOfWeek) {
        return String.format(upsertSQL, taName, daysOfWeek[0], daysOfWeek[1], daysOfWeek[2], daysOfWeek[3], daysOfWeek[4], daysOfWeek[5], daysOfWeek[6],
                daysOfWeek[0], daysOfWeek[1], daysOfWeek[2], daysOfWeek[3], daysOfWeek[4], daysOfWeek[5], daysOfWeek[6]);
    }

    private String selectSQL = "SELECT Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday FROM " + tableName + " WHERE taName = ?";
    public String createSelectSQL(final String taName) {
        return String.format(selectSQL, taName);
    }
}
