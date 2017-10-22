package com.dao;

import com.dto.ScheduleDto;
import org.springframework.stereotype.Component;

/**
 * Facilitates communication between the API and the persistent data source
 */
public interface ScheduleDao {
    void setScheduleInDataSource(final ScheduleDto newSchedule);
    ScheduleDto getScheduleFromDataSource(final String taName);
}
