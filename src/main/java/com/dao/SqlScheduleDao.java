package com.dao;

import com.dto.ScheduleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("sqlScheduleDao")
public class SqlScheduleDao implements ScheduleDao {

    private final DataSourceCredentialsDao credentialsDao;

    @Autowired
    public SqlScheduleDao(@Qualifier("xmlDbCredentialsDao") final DataSourceCredentialsDao credentialsDao){
        this.credentialsDao = credentialsDao;
    }

    @Override
    public void setScheduleInDataSource(ScheduleDto newSchedule) {
    }

    @Override
    public ScheduleDto getScheduleFromDataSource(String taName) {
        String[] schedule = new String[]{"44", "33", "22"};
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setSchedulesByDay(schedule);
        return scheduleDto;
    }
}
