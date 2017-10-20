package com.actors;

import cloud.orbit.actors.Actor;
import cloud.orbit.concurrent.Task;

/**
 * Created by andrewbates11 on 2017-05-28.
 */
public interface Player extends Actor {
    Task<Void> setName(final String newName);
    Task<String> getName();
    static Task<Player> getActor(final String playerId){
        return Task.fromValue(Actor.getReference(Player.class, playerId));
    }
}
