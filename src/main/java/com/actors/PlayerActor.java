package com.actors;

import cloud.orbit.actors.Actor;
import cloud.orbit.actors.runtime.AbstractActor;
import cloud.orbit.concurrent.Task;

/**
 * Created by andrewbates11 on 2017-05-28.
 */
public class PlayerActor extends AbstractActor<PlayerActor.State> implements Player {

    public static class State {
        String name;
    }

    @Override
    public Task setName(String newName) {
        state().name = newName;
        return Task.done();
    }

    @Override
    public Task<String> getName() {
        return Task.fromValue(state().name);
    }

}
