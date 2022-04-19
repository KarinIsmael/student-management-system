package se.iths.rest;

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
            throw new ExceptionHandler("value for 'firstName', 'lastName' and 'email' can not be excluded");
        }
        if(existingEmails.contains(newTeacherEmail)){
            throw new ExceptionHandler("This email already exists, please try different email or the teacher is already registered");
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
            throw new ExceptionHandler("There is no teacher with this id");
        }
        Teacher foundTeacher = teacherService.findTeacherById(id);
        return Response.ok(foundTeacher).build();
    }

    @Path("{id}")
    @DELETE
    public Response deleteTeacher(@PathParam("id") Long id){
        if(!teacherService.getTeacherIDs().contains(id)){
            throw new ExceptionHandler("There is no teacher with this id");
        }
        teacherService.deleteTeacher(id);
        return Response.ok().build();
    }
}
