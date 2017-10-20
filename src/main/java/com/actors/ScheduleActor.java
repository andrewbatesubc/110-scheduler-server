package com.actors;

import cloud.orbit.actors.runtime.AbstractActor;
import cloud.orbit.concurrent.Task;

/**
 * Created by andrewbates11 on 2017-10-19.
 */
public class ScheduleActor extends AbstractActor<ScheduleActor.State> implements Schedule  {

    public static class State {
        String taName = null;
        String[] schedule = new String[0];
    }

    /**
     * Reach out to the DB and store the new schedule, then additionally set this schedule
     * in the cache
     * @param newSchedule
     * @return
     */
    @Override
    public Task<Void> setSchedule(String taName, String[] newSchedule) {
        //Note: this second case should never happen, but we'll add this to be a bit more defensive
        if(state().taName == null || state().taName != taName) {
            state().taName = taName;
        }

        //We reach out to the DB and store our schedule, then cache it in our state
        return null;
    }

    /**
     * First attempt to retrieve the schedule from the cached actor state. If that schedule
     * is empty, reach out and get it from the DB.
     * @return
     */
    @Override
    public Task<String[]> getSchedule(final String taName) {
        //Our schedule doesn't exist, so we return null
        if(state().taName == null || state().taName != taName){

        }
        return null;
    }


}
