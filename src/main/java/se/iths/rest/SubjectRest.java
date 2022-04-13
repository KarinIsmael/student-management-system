package se.iths.rest;

import se.iths.entity.Student;
import se.iths.entity.Subject;
import se.iths.entity.Teacher;
import se.iths.service.SubjectService;

import javax.inject.Inject;
import javax.json.JsonException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("subject")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SubjectRest {

    SubjectService subjectService;

    @Inject
    public SubjectRest(SubjectService subjectService) {
        this.subjectService = subjectService;
    }


    @POST
    public Response createSubject(Subject subject){

        String newSubjectName = subject.getName();
        List<Subject> existingSubjectNames = subjectService.getSubjectNames();

        if(existingSubjectNames.contains(newSubjectName)){
            throw new WebApplicationException(Response.status(Response.Status.NOT_ACCEPTABLE)
                    .entity("This subject-name already exists, system does not allow duplicate subjects.")
                    .type(MediaType.TEXT_PLAIN_TYPE).build());
        }

        subjectService.createSubject(subject);
        return Response.ok(subject).build();
    }


    @GET
    public Response getAllSubjects(){
        List<Subject> foundSubjects = subjectService.getAllSubjects();
        return Response.ok(foundSubjects).build();
    }

    @Path("{id}")
    @GET
    public Response getSubjectById(@PathParam("id") Long id){
        if(!subjectService.getSubjectIDs().contains(id)){
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("There is no subject with this id")
                    .type(MediaType.TEXT_PLAIN_TYPE).build());
        }
        Subject foundSubject = subjectService.findSubjectById(id);
        return Response.ok(foundSubject).build();
    }

   /* @Path("addstudenttosubject/{id}")
    @PATCH
    public Response addStudentToSubject(@PathParam("id") Long id, Long studentId){

        Subject updatedSubject = subjectService.addStudentToSubject(id, studentId);

        return Response.ok(updatedSubject).build();
    }*/

    @Path("addstudenttosubject/{id}")
    @POST
    public Response addStudentToSubject(@PathParam("id") Long id, Student student){

        subjectService.addStudentToSubject(id, student.getId());

        return Response.ok().build();
    }

    @Path("addteachertosubject/{id}")
    @POST
    public Response addTeacherToSubject(@PathParam("id") Long id, Teacher teacher){

        subjectService.addTeacherToSubject(id, teacher.getId());

        //Teacher foundTeacher = subjectService.findTeacherById(teacherId);
        //subjectService.addTeacherToSubject(id, id);
        return Response.ok().build();
    }

    @Path("{id}")
    @DELETE
    public Response deleteSubject(@PathParam("id")Long id){
        if(!subjectService.getSubjectIDs().contains(id)){
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("no subject with this id found").type(MediaType.TEXT_PLAIN_TYPE).build());
        }
        subjectService.deleteSubject(id);
        return Response.ok().build();
    }
}
