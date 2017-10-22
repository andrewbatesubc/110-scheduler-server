package com.controllers;

import com.dao.ScheduleDao;
import com.dto.ScheduleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.sql.SQLException;

@Component
public class ScheduleController  {

    private final ScheduleDao scheduleDao;

    @Autowired
    public ScheduleController(@Qualifier("sqlScheduleDao") final ScheduleDao scheduleDao){
        this.scheduleDao = scheduleDao;
    }

    /**
     * Reach out to the DB and store the new schedule, then update the cache
     * @return
     */
    public void setSchedule(final ScheduleDto newSchedule) throws URISyntaxException, SQLException {
        scheduleDao.setScheduleInDataSource(newSchedule);
    }

    /**
     * First attempt to retrieve the schedule from the cached actor state. If that schedule
     * is empty, reach out and get it from the DB (if it exists)
     * @return
     */
    public ScheduleDto getSchedule(final String taName) throws URISyntaxException, SQLException {
        return scheduleDao.getScheduleFromDataSource(taName);
    }

    public void deleteTASchedule(final String taName) throws URISyntaxException, SQLException {
        scheduleDao.deleteTASchedule(taName);
    }

    public void deleteAllSchedules() throws URISyntaxException, SQLException {
        scheduleDao.deleteAllSchedules();
    }


}
