package se.iths.rest;

import se.iths.entity.Student;
import se.iths.service.StudentService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;

@Path("student")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentRest {

    StudentService studentService;

    @Inject
    public StudentRest(StudentService studentService) {
        this.studentService = studentService;
    }

    @Path("")
    @GET
    public Response getAllStudents(){
        List<Student> foundStudents = studentService.getAllStudents();
        if(foundStudents.isEmpty()){
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("no students found").type(MediaType.TEXT_PLAIN_TYPE).build());
        }
        return Response.ok(foundStudents).build(); 
    }

    @Path("")
    @POST
    public Response createStudent(Student student){

        String newStudentEmail = student.getEmail();
        List<Student> existingEmails = studentService.getStudentEmails();

        if(student.getFirstName() == null || student.getLastName()==null || student.getEmail()==null){
            throw new WebApplicationException(Response.status(Response.Status.NOT_ACCEPTABLE)
                    .entity("value for 'firstName', 'lastName' and 'email' can not be excluded")
                    .type(MediaType.TEXT_PLAIN_TYPE).build());
        }
            if(existingEmails.contains(newStudentEmail)){
            throw new WebApplicationException(Response.status(Response.Status.NOT_ACCEPTABLE)
                        .entity("This email already exists, please try different email or the student is already registered")
                    .type(MediaType.TEXT_PLAIN_TYPE).build());
        }


         studentService.createStudent(student);

        return Response.ok(student).build();
    }

    @Path("{id}")
    @DELETE
    public Response deleteStudent(@PathParam("id")Long id){
        if(!studentService.getStudentIDs().contains(id)){
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("no student with this id found").type(MediaType.TEXT_PLAIN_TYPE).build());
        }
        studentService.deleteStudent(id);
        return Response.ok().build();
    }

    @Path("")
    @PUT
    public Response updateStudent(Student student){
        if(!studentService.getStudentIDs().contains(student.getId())){
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("no student with this id found").type(MediaType.TEXT_PLAIN_TYPE).build());
        }
        if(student.getFirstName() == null || student.getLastName()==null || student.getEmail()==null){
            throw new WebApplicationException(Response.status(Response.Status.NOT_ACCEPTABLE)
                    .entity("value for 'firstName', 'lastName' and 'email' can not be excluded")
                    .type(MediaType.TEXT_PLAIN_TYPE).build());
        }
        studentService.updateStudent(student);
        return Response.ok(student).build();
    }

    @Path("{id}")
    @PATCH
    public Response partialStudentUpdate(@PathParam("id") Long id, Student student){
        if(!studentService.getStudentIDs().contains(id)){
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("no student with this id found").type(MediaType.TEXT_PLAIN_TYPE).build());
        }
        Student updatedStudent = studentService.updateName(id, student.getFirstName(), student.getLastName());
        return Response.ok(updatedStudent).build();
    }

    @Path("getbylastname")
    @GET
    public Response getByLastName(@QueryParam("lastName")String lastName){
        if(!studentService.getStudentLastNames().contains(lastName)){
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                        .entity("There is no student with this lastname")
                    .type(MediaType.TEXT_PLAIN_TYPE).build());
        }
        List<Student> foundstudents = studentService.getByLastNameNamedParameter(lastName);
        return Response.ok(foundstudents).build();
    }
}
