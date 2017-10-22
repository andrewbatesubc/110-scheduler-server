package com.api;


import com.controllers.ScheduleController;
import com.dto.ScheduleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Endpoint that the scheduler client will hit to grab/update existing schedules, and store
 * new ones
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleEndPoint {

    private final ScheduleController scheduleController;

    @Autowired
    public ScheduleEndPoint(final ScheduleController scheduleController){
        this.scheduleController = scheduleController;
    }

    @RequestMapping(method= RequestMethod.GET, value = "/getSchedule/{taName}")
    public ScheduleDto getSchedule(@PathVariable("taName") String taName) throws URISyntaxException, SQLException {
        return scheduleController.getSchedule(taName);
    }

    @RequestMapping(method= RequestMethod.POST, value = "/setSchedule/{taName}")
    public void setSchedule(@PathVariable("taName") String taName,
                            @RequestParam("scheduleDto") ScheduleDto schedule) throws URISyntaxException, SQLException {
        scheduleController.setSchedule(taName, schedule);
    }

}
