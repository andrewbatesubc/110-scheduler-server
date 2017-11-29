package com.dao;

import org.springframework.stereotype.Component;

@Component
public class PostgresSQLStatements {

    private final String scheduleTableName = "Schedules";
    private final String scheduleTypesTableName = "ScheduleTypes";

    public String createScheduleTableSQL() {
        return "CREATE TABLE IF NOT EXISTS " + scheduleTableName + "("
                        + "ScheduleType varchar(255) REFERENCES "+ scheduleTypesTableName +" NOT NULL,"
                        + "TAName varchar(255) NOT NULL,"
                        + "Monday varchar(255) NOT NULL,"
                        + "Tuesday varchar(255) NOT NULL,"
                        + "Wednesday varchar(255) NOT NULL,"
                        + "Thursday varchar(255) NOT NULL,"
                        + "Friday varchar(255) NOT NULL,"
                        + "Saturday varchar(255) NOT NULL,"
                        + "Sunday varchar(255) NOT NULL,"
                        + "Date varchar(255) NOT NULL,"
                        + "PRIMARY KEY (TAName, ScheduleType))";
    }

    public String createScheduleTypesTableSQL() {
        return "CREATE TABLE IF NOT EXISTS " + scheduleTypesTableName + "("
                + "ScheduleType varchar(255) NOT NULL,"
                + "PRIMARY KEY (ScheduleType))";
    }

    public String dropScheduleTableSQL() {
        return "DROP TABLE IF EXISTS " + scheduleTableName;
    }
    public String dropScheduleTypeTableSQL() {
        return "DROP TABLE IF EXISTS " + scheduleTypesTableName;
    }

    private String upsertScheduleSQL = "INSERT INTO " + scheduleTableName + "(TAName, ScheduleType, Date, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday) " +
            "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s') " +
            "ON CONFLICT (TAName, ScheduleType) DO UPDATE SET Date = '%s', Monday = '%s', Tuesday = '%s', " +
            "Wednesday = '%s', Thursday = '%s', Friday = '%s', Saturday = '%s', Sunday = '%s'";
    public String createUpsertScheduleSQL(final String taName, final String scheduleType, final String date, final String[] daysOfWeek) {
        return String.format(upsertScheduleSQL, taName, scheduleType, date, daysOfWeek[0], daysOfWeek[1], daysOfWeek[2], daysOfWeek[3], daysOfWeek[4], daysOfWeek[5], daysOfWeek[6],
                date, daysOfWeek[0], daysOfWeek[1], daysOfWeek[2], daysOfWeek[3], daysOfWeek[4], daysOfWeek[5], daysOfWeek[6]);
    }

    private String insertScheduleTypeSQL = "INSERT INTO " + scheduleTypesTableName + "(ScheduleType) "
            + "VALUES ('%s')";

    public String createInsertScheduleTypeSQL(final String scheduleType){
        return String.format(insertScheduleTypeSQL, scheduleType);
    }

    private String selectSQL = "SELECT Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday, Date FROM " + scheduleTableName +
            " WHERE TAName = '%s' " +
            " AND ScheduleType = '%s'";
    public String createSelectSQL(final String taName, final String scheduleType) {
        return String.format(selectSQL, taName, scheduleType);
    }

    public String selectAllSQL(){
        return "SELECT TAName, ScheduleType, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday, Date FROM " + scheduleTableName;
    }

    public String selectAllScheduleTypesSQL(){
        return "SELECT ScheduleType FROM " + scheduleTypesTableName;
    }

    public String deleteAllSQL(){
        return "DELETE FROM " + scheduleTableName;
    }

    private String deleteTASQL = "DELETE FROM " + scheduleTableName + " WHERE TAName = '%s' AND ScheduleType = '%s'";
    public String createDeleteTASQL(final String taName, final String scheduleType){
        return String.format(deleteTASQL, taName, scheduleType);
    }

    private String deleteScheduleByTypeSQL = "DELETE FROM " + scheduleTableName + " WHERE ScheduleType = '%s'";
    public String createDeleteScheduleByTypeSQL(final String scheduleType){
        return String.format(deleteScheduleByTypeSQL, scheduleType);
    }

    private String deleteScheduleTypeSQL = "DELETE FROM " + scheduleTypesTableName + " WHERE ScheduleType = '%s'";
    public String createDeleteScheduleTypeSQL(final String scheduleType){
        return String.format(deleteScheduleTypeSQL, scheduleType);
    }
}
