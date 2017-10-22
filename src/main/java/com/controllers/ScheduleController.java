package com.controllers;

import com.dao.ScheduleDao;
import com.dto.ScheduleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ScheduleController  {

    private final ScheduleDao scheduleDao;

    @Autowired
    public ScheduleController(@Qualifier("sqlScheduleDao") final ScheduleDao scheduleDao){
        this.scheduleDao = scheduleDao;
    }

    /**
     * Reach out to the DB and store the new schedule, then update the cache
     * @param newSchedule
     * @return
     */
    public void setSchedule(final String taName, final ScheduleDto newSchedule) {
        //We reach out to the DB and store our schedule, then cache it in our state
        scheduleDao.setScheduleInDataSource(newSchedule);
    }

    /**
     * First attempt to retrieve the schedule from the cached actor state. If that schedule
     * is empty, reach out and get it from the DB (if it exists)
     * @return
     */
    public ScheduleDto getSchedule(final String taName) {
        return scheduleDao.getScheduleFromDataSource(taName);
    }


}
