package com.api;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

import cloud.orbit.actors.Actor;
import cloud.orbit.concurrent.Task;
import cloud.orbit.exception.UncheckedException;
import com.actors.Player;
import com.actors.PlayerActor;

/**
 * Created by joe@bioware.com on 2016-02-16.
 */
@Singleton
@Path("/player")
public class PlayerEndPoint
{
    public static class HelloResult
    {
        private int helloCount;

        public int getHelloCount()
        {
            return helloCount;
        }

        public void setHelloCount(int helloCount)
        {
            this.helloCount = helloCount;
        }
    }

    private int count = 0;

    public PlayerEndPoint()
    {
        count = 0;
    }

    @GET
    @Path("/helloRaw")
    @Produces(MediaType.APPLICATION_JSON)
    public HelloResult getHelloRaw()
    {
        HelloResult result = new HelloResult();
        result.setHelloCount(++count);
        return result;
    }

    @GET
    @Path("/helloTask")
    @Produces(MediaType.APPLICATION_JSON)
    public Task<HelloResult> getHelloTask()
    {
        HelloResult result = new HelloResult();
        result.setHelloCount(++count);
        return Task.fromValue(result);
    }

    @GET
    @Path("/serverErrorRaw")
    @Produces(MediaType.APPLICATION_JSON)
    public HelloResult getServerErrorRaw()
    {
        throw new UncheckedException("serverError");
    }

    @GET
    @Path("/serverErrorTask")
    @Produces(MediaType.APPLICATION_JSON)
    public Task<HelloResult> getServerErrorTask()
    {
        throw new UncheckedException("serverError");
    }

    @GET
    @Path("/serverErrorNestedTask")
    @Produces(MediaType.APPLICATION_JSON)
    public Task<HelloResult> getServerErrorNestedTask()
    {
        return Task.supplyAsync(() -> {throw new UncheckedException("serverError"); });

    }

    @GET
    @Path("/forbiddenRaw")
    @Produces(MediaType.APPLICATION_JSON)
    public HelloResult getForbiddenRaw()
    {
        throw new ForbiddenException("forbidden");
    }

    @GET
    @Path("/forbiddenTask")
    @Produces(MediaType.APPLICATION_JSON)
    public Task<HelloResult> getForbiddenTask()
    {
        return Task.supplyAsync(() -> {throw new ForbiddenException("forbidden"); });
    }

    @GET
    @Path("/listTask")
    @Produces(MediaType.APPLICATION_JSON)
    public Task<List<HelloResult>> getListTask()
    {
        List<HelloResult> results = new ArrayList<>();
        HelloResult helloResult = new HelloResult();
        helloResult.setHelloCount(++count);
        results.add(helloResult);
        helloResult = new HelloResult();
        helloResult.setHelloCount(++count);
        results.add(helloResult);

        return Task.fromValue(results);
    }

    @GET
    @Path("/voidTask")
    @Produces(MediaType.APPLICATION_JSON)
    public Task<Void> getVoidTask()
    {
        return Task.done();
    }

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Task<String> getPlayerName(@PathParam("id") String playerId){
        return Player.getActor(playerId).thenCompose(player ->
                player.getName());
    }

    @POST
    @Path("/id/{id}/name/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public Task<Void> setPlayerName(
            @PathParam("id") String playerId,
            @PathParam("name") String newName){
       return Player.getActor(playerId).thenCompose(player ->
               player.setName(newName));
    }
}