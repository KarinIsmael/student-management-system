package se.iths.rest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ExceptionHandler extends WebApplicationException{

    public ExceptionHandler(String message){
        super (Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorMessage("Operation did not work: " + message))
                .type(MediaType.APPLICATION_JSON).build());
    }
}
