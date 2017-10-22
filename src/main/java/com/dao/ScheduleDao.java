package com.dao;

import com.dto.ScheduleDto;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Facilitates communication between the API and the persistent data source
 */
public interface ScheduleDao {
    void setScheduleInDataSource(final String taName, final ScheduleDto newSchedule) throws URISyntaxException, SQLException;
    ScheduleDto getScheduleFromDataSource(final String taName) throws URISyntaxException, SQLException;
}
