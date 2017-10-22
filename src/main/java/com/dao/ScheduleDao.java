package com.dao;

import com.dto.ScheduleDto;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Facilitates communication between the API and the persistent data source
 */
public interface ScheduleDao {
    void deleteTASchedule(final String taName) throws URISyntaxException, SQLException;
    void deleteAllSchedules() throws URISyntaxException, SQLException;
    void setScheduleInDataSource(final ScheduleDto newSchedule) throws URISyntaxException, SQLException;
    ScheduleDto getScheduleFromDataSource(final String taName) throws URISyntaxException, SQLException;
    ScheduleDto[] getAllSchedulesFromDataSource() throws URISyntaxException, SQLException;

}
