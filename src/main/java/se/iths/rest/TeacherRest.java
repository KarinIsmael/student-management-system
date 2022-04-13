package se.iths.rest;

import se.iths.entity.Student;
import se.iths.entity.Subject;
import se.iths.entity.Teacher;
import se.iths.service.TeacherService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("teacher")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TeacherRest {

    TeacherService teacherService;

    @Inject
    public TeacherRest(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @POST
    public Response createTeacher(Teacher teacher){

        String newTeacherEmail = teacher.getEmail();
        List<Teacher> existingEmails = teacherService.getTeacherEmails();

        if(teacher.getFirstName() == null || teacher.getLastName()==null || teacher.getEmail()==null){
            throw new WebApplicationException(Response.status(Response.Status.NOT_ACCEPTABLE)
                    .entity("value for 'firstName', 'lastName' and 'email' can not be excluded")
                    .type(MediaType.TEXT_PLAIN_TYPE).build());
        }
        if(existingEmails.contains(newTeacherEmail)){
            throw new WebApplicationException(Response.status(Response.Status.NOT_ACCEPTABLE)
                    .entity("This email already exists, please try different email or the teacher is already registered")
                    .type(MediaType.TEXT_PLAIN_TYPE).build());
        }
        teacherService.createTeacher(teacher);
        return Response.ok(teacher).build();
    }

    @GET
    public Response getAllTeachers(){
        List<Teacher> foundTeachers = teacherService.getAllTeachers();
        return Response.ok(foundTeachers).build();
    }

    @Path("{id}")
    @GET
    public Response getTeacherById(@PathParam("id") Long id){
        if(!teacherService.getTeacherIDs().contains(id)){
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("There is no teacher with this id")
                    .type(MediaType.APPLICATION_JSON_TYPE).build());
        }
        Teacher foundTeacher = teacherService.findTeacherById(id);
        return Response.ok(foundTeacher).build();
    }

    @Path("{id}")
    @DELETE
    public Response deleteTeacher(@PathParam("id") Long id){
        if(!teacherService.getTeacherIDs().contains(id)){
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("no teacher with this id found").type(MediaType.TEXT_PLAIN_TYPE).build());
        }
        teacherService.deleteTeacher(id);
        return Response.ok().build();
    }
}
