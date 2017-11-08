package com.controllers;

import com.dao.ScheduleDao;
import com.dto.ScheduleDto;
import com.dto.ScheduleTypesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public void deleteTASchedule(final String taName, final String scheduleType) throws URISyntaxException, SQLException {
        scheduleDao.deleteTASchedule(taName, scheduleType);
    }

    public void deleteTAScheduleType(final String scheduleType) throws URISyntaxException, SQLException {
        scheduleDao.deleteTAScheduleType(scheduleType);
    }

    public void deleteAllSchedules() throws URISyntaxException, SQLException {
        scheduleDao.deleteAllSchedules();
    }

    public ScheduleDto getSchedule(final String taName, final String scheduleType) throws URISyntaxException, SQLException {
        ResultSet rs = scheduleDao.getScheduleFromDataSource(taName, scheduleType);
        ScheduleDto schedule = new ScheduleDto(taName, scheduleType, null);
        if(rs != null){
            while(rs.next()){
                String[] results = getDailyValuesFromResultSet(rs);
                schedule.setSchedulesByDay(results);
            }
        }
        return schedule;
    }

    public ScheduleTypesDto getScheduleTypes() throws URISyntaxException, SQLException {
        ResultSet rs = scheduleDao.getScheduleTypesFromDataSource();
        List<String> scheduleTypes = new ArrayList<>();
        while(rs.next()){
            scheduleTypes.add(rs.getString("ScheduleType"));
        }
        return new ScheduleTypesDto(scheduleTypes.toArray(new String[scheduleTypes.size()]));
    }

    public ScheduleDto[] getAllSchedules() throws URISyntaxException, SQLException {
        List<ScheduleDto> schedules = new ArrayList<>();
        ResultSet rs = scheduleDao.getAllSchedulesFromDataSource();
        while(rs.next()){
            String[] results = getDailyValuesFromResultSet(rs);
            schedules.add(new ScheduleDto(rs.getString("TAName"), rs.getString("ScheduleType"), results));
        }
        return schedules.toArray(new ScheduleDto[schedules.size()]);
    }

    private String[] getDailyValuesFromResultSet(final ResultSet rs) throws SQLException {
        String[] results = new String[7];
        results[0] = rs.getString("Monday");
        results[1] = rs.getString("Tuesday");
        results[2] = rs.getString("Wednesday");
        results[3] = rs.getString("Thursday");
        results[4] = rs.getString("Friday");
        results[5] = rs.getString("Saturday");
        results[6] = rs.getString("Sunday");
        return results;
    }
}
