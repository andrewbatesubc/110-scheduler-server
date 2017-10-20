package com.api;

import cloud.orbit.concurrent.Task;
import com.actors.Schedule;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Endpoint that the scheduler client will hit to grab/update existing schedules, and store
 * new ones
 */
@Singleton
@Path("/schedule")
public class ScheduleEndPoint {

    @GET
    @Path("/taName/{taName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Task<String[]> getSchedule(@PathParam("taName") String taName)
    {
        return Schedule.getActor(taName).getSchedule(taName);
    }

    /**
     * We will treat this as idempotent. If this is the first time we have seen the schedule,
     * we add it. If we have already seen it, we update it.
     * @param taName
     * @return
     */
    @POST
    @Path("/taName/{taName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Task<Void> setSchedule(@PathParam("taName") String taName)
    {
        return Schedule.getActor(taName).setSchedule(taName, new String[1]);
    }
}
