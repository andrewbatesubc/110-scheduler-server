package com.controllers;

import com.dao.ScheduleDao;
import com.dto.ScheduleDto;
import com.dto.ScheduleTypesDto;
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

    public void setSchedule(final ScheduleDto newSchedule) throws URISyntaxException, SQLException {
        scheduleDao.setScheduleInDataSource(newSchedule);
    }

    public void setScheduleType(final String newScheduleType) throws URISyntaxException, SQLException {
        scheduleDao.setScheduleTypeInDataSource(newScheduleType);
    }

    public ScheduleDto getSchedule(final String taName, final String scheduleType) throws URISyntaxException, SQLException {
        return scheduleDao.getScheduleFromDataSource(taName, scheduleType);
    }

    public ScheduleTypesDto getScheduleTypes() throws URISyntaxException, SQLException {
        return scheduleDao.getScheduleTypesFromDataSource();
    }

    public ScheduleDto[] getAllSchedules() throws URISyntaxException, SQLException {
        return scheduleDao.getAllSchedulesFromDataSource();
    }

    public void deleteTASchedule(final String taName, final String scheduleType) throws URISyntaxException, SQLException {
        scheduleDao.deleteTASchedule(taName, scheduleType);
    }

    public void deleteTAScheduleType(final String scheduleType) throws URISyntaxException, SQLException {
        scheduleDao.deleteTAScheduleType(scheduleType);
    }

    public void deleteAllSchedules() throws URISyntaxException, SQLException {
        scheduleDao.deleteAllSchedules();
    }


}
