package com.api;


import com.controllers.ScheduleController;
import com.dto.ScheduleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint that the scheduler client will hit to grab/update existing schedules, and store
 * new ones
 */
@RestController
//@RequestMapping("/schedule")
public class ScheduleEndPoint {

    private final ScheduleController scheduleController;

    @Autowired
    public ScheduleEndPoint(final ScheduleController scheduleController){
        this.scheduleController = scheduleController;
    }

    @RequestMapping("/schedule")
    public ScheduleDto getSchedule() {
        return scheduleController.getSchedule("zsasdf");
    }

    /**
     * We will treat this as idempotent. If this is the first time we have seen the schedule,
     * we add it. If we have already seen it, we update it.
     * @param taName
     * @return

    @POST
    @Path("/setSchedule/taName/{taName}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Task<Void> setSchedule(@PathParam("taName") String taName,
                                  final ScheduleDto scheduleDto)
    {
        return Schedule.getActor(taName).setSchedule(taName, scheduleDto);
    }*/
}
