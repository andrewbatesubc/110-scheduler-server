package com.actors;

import cloud.orbit.actors.Actor;
import cloud.orbit.concurrent.Task;

/**
 Actor to represent TA schedules
 */

public interface Schedule extends Actor {
    /**
     * Store a new TA schedule. Each index in the array represents a
     * day of the week, starting on Monday. The string at each index will take the form of that
     * TA's schedule for that day in each consecutive half hour block, using the letters A, N, P to represent
     * Available, Not Available, and Prefer Not. For example, 8:00 - Available, 8:30 Available,
     * 9:00 Prefer Not ... would be "AAP..."
     * @param newSchedule
     * @return
     */
    Task<Void> setSchedule(final String taName, final String[] newSchedule);

    /**
     * Retrieves a TA schedule.
     * @return
     */
    Task<String[]> getSchedule(final String taName);

    /**
     * Retrieves a TA actor keyed by the given firstname_lastname combination
     * @param taName
     * @return
     */
    static Schedule getActor(final String taName){
        return Actor.getReference(Schedule.class, taName);
    }
}
