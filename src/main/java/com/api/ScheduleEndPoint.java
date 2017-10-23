package com.api;

import com.controllers.ScheduleController;
import com.dto.ScheduleDto;
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
    public String[] getScheduleTypes() throws URISyntaxException, SQLException {
        return scheduleController.getScheduleTypes();
    }

    @RequestMapping(method= RequestMethod.POST, value = "/setSchedule")
    public void setSchedule(@RequestBody ScheduleDto schedule) throws URISyntaxException, SQLException {
        scheduleController.setSchedule(schedule);
    }

    @RequestMapping(method= RequestMethod.POST, value = "/setScheduleType/{scheduleType}")
    public void setSchedule(@PathVariable("scheduleType") String scheduleType) throws URISyntaxException, SQLException {
        scheduleController.setScheduleType(scheduleType);
    }

    // These are admin-only APIs. These will be surfaced to the course coordinator
    @RequestMapping(method= RequestMethod.GET, value = "/getAllSchedules")
    public ScheduleDto[] getAllSchedules() throws URISyntaxException, SQLException {
        return scheduleController.getAllSchedules();
    }

    @RequestMapping(method= RequestMethod.DELETE, value = "/deleteSchedule/{taName}")
    public void deleteSchedule(@PathVariable("taName") String taName) throws URISyntaxException, SQLException {
        scheduleController.deleteTASchedule(taName);
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
