package com.providers;

import cloud.orbit.concurrent.Task;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

import java.io.IOException;
import java.util.concurrent.CompletionException;

@Provider
public class TaskProvider implements WriterInterceptor
{
    @Override
    public void aroundWriteTo(final WriterInterceptorContext context) throws IOException, WebApplicationException
    {
        final Object entity = context.getEntity();

        if(entity instanceof Task)
        {
            Object result = null;

            try
            {
                result = ((Task<?>)entity).join();
            }
            catch(CompletionException e)
            {
                // We need to get the actual exception and rethrow it
                throw (RuntimeException) ((e.getCause() != null) ? e.getCause() : e);
            }

            context.setGenericType(null);

            // Do we actually have a result?
            if(result != null)
            {
                // Set the type we got
                context.setType(result.getClass());

                // Replace the entity
                context.setEntity(result);
            }
            else
            {

                // Result is null, throw a 204
                throw new WebApplicationException(204);
            }
        }

        context.proceed();
    }
}
