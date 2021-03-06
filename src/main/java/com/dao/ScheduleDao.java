package com.dao;

import com.dto.ScheduleDto;
import com.dto.ScheduleTypesDto;

import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Facilitates communication between the API and the persistent data source
 */
public interface ScheduleDao {
    void deleteTASchedule(final String taName, final String scheduleType) throws URISyntaxException, SQLException;
    void deleteTAScheduleType(final String scheduleType) throws URISyntaxException, SQLException;
    void deleteAllSchedules() throws URISyntaxException, SQLException;
    void setScheduleInDataSource(final ScheduleDto newSchedule) throws URISyntaxException, SQLException;
    void setScheduleTypeInDataSource(final String newScheduleType) throws URISyntaxException, SQLException;
    ResultSet getScheduleFromDataSource(final String taName, final String scheduleType) throws URISyntaxException, SQLException;
    ResultSet getScheduleTypesFromDataSource() throws URISyntaxException, SQLException;
    ResultSet getAllSchedulesFromDataSource() throws URISyntaxException, SQLException;
}
