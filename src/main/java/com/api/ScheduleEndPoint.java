package com.api;

import com.controllers.ScheduleController;
import com.dto.ScheduleDto;
import com.dto.ScheduleListDto;
import com.dto.ScheduleTypesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Endpoint that the scheduler client will hit to grab/update existing schedules, and store
 * new ones. Also surfaces admin endpoints to grab all schedules, delete select schedules, and delete all schedules
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleEndPoint {


    private final ScheduleController scheduleController;

    @Autowired
    public ScheduleEndPoint(final ScheduleController scheduleController){
        this.scheduleController = scheduleController;
    }
    // These are TA-facing APIs
    @RequestMapping(method= RequestMethod.GET, value = "/getSchedule/{taName}/{scheduleType}")
    public ScheduleDto getSchedule(@PathVariable("taName") String taName,
                                   @PathVariable("scheduleType") String scheduleType) throws URISyntaxException, SQLException {
        return scheduleController.getSchedule(taName, scheduleType);
    }

    @RequestMapping(method= RequestMethod.GET, value = "/getScheduleTypes")
    public ScheduleTypesDto getScheduleTypes() throws URISyntaxException, SQLException {
        return scheduleController.getScheduleTypes();
    }

    @RequestMapping(method= RequestMethod.POST, value = "/setSchedule")
    public void setSchedule(@RequestBody ScheduleDto schedule) throws URISyntaxException, SQLException {
        String taName = schedule.getTaName();
        if(taName == null || taName.isEmpty() || taName.length() <= 3){
            throw new SQLException("Cannot have an empty TA name");
        }
        scheduleController.setSchedule(schedule);
    }

    // These are admin-only APIs. These will be surfaced to the course coordinator
    @RequestMapping(method= RequestMethod.GET, value = "/getAllSchedules")
    public ScheduleListDto getAllSchedules() throws URISyntaxException, SQLException {
        return new ScheduleListDto(scheduleController.getAllSchedules());
    }

    @RequestMapping(method= RequestMethod.POST, value = "/setScheduleType/{scheduleType}")
    public void setSchedule(@PathVariable("scheduleType") String scheduleType) throws URISyntaxException, SQLException {
        scheduleController.setScheduleType(scheduleType);
    }

    @RequestMapping(method= RequestMethod.DELETE, value = "/deleteSchedule/{taName}/{scheduleType}")
    public void deleteSchedule(@PathVariable("taName") String taName,
                               @PathVariable("scheduleType") String scheduleType) throws URISyntaxException, SQLException {
        scheduleController.deleteTASchedule(taName, scheduleType);
    }

    @RequestMapping(method= RequestMethod.DELETE, value = "/deleteScheduleType/{scheduleType}")
    public void deleteScheduleType(@PathVariable("scheduleType") String scheduleType) throws URISyntaxException, SQLException {
        scheduleController.deleteTAScheduleType(scheduleType);
    }

    @RequestMapping(method= RequestMethod.DELETE, value = "/deleteAllSchedules")
    public void deleteAllSchedules() throws URISyntaxException, SQLException {
        scheduleController.deleteAllSchedules();
    }



}
