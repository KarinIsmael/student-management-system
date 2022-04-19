package se.iths.rest;

import se.iths.entity.Subject;
import se.iths.entity.Teacher;
import se.iths.service.SubjectService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
            throw new ExceptionHandler("This subject-name already exists, system does not allow duplicate subjects.");
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
            throw new ExceptionHandler("There is no subject with this id");
        }
        Subject foundSubject = subjectService.findSubjectById(id);
        return Response.ok(foundSubject).build();
    }

    @Path("/{subjectid}/student/{studentid}")
    @PUT
    public Response addStudentToSubject(@PathParam("subjectid") Long subjectid, @PathParam("studentid") Long studentid){

        if(!subjectService.getSubjectIDs().contains(subjectid) || !subjectService.getStudentIDs().contains(studentid)){
            throw new ExceptionHandler("Check the entered ID values, one or both do not exist in the system");
        }
        subjectService.addStudentToSubject(subjectid, studentid);

        return Response.ok().build();
    }

    @Path("addteachertosubject/{id}")
    @POST
    public Response addTeacherToSubject(@PathParam("id") Long id, Teacher teacher){

        if(!subjectService.getSubjectIDs().contains(id)){
            throw new ExceptionHandler("Check the entered ID values, Subject does not exist in the system");
        }
        subjectService.addTeacherToSubject(id, teacher.getId());

        return Response.ok().build();
    }

    @Path("{id}")
    @DELETE
    public Response deleteSubject(@PathParam("id")Long id){
        if(!subjectService.getSubjectIDs().contains(id)){
            throw new ExceptionHandler("no subject with this id found");
        }
        subjectService.deleteSubject(id);
        return Response.ok().build();
    }
}
