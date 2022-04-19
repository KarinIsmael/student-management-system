package se.iths.rest;

import se.iths.entity.Student;
import se.iths.service.StudentService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("student")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentRest {

    StudentService studentService;

    @Inject
    public StudentRest(StudentService studentService) {
        this.studentService = studentService;
    }

    @GET
    public Response getAllStudents(){
        List<Student> foundStudents = studentService.getAllStudents();
        if(foundStudents.isEmpty()){
            throw new ExceptionHandler("no students found");
        }
        return Response.ok(foundStudents).build(); 
    }


    @POST
    public Response createStudent(Student student){

        String newStudentEmail = student.getEmail();
        List<Student> existingEmails = studentService.getStudentEmails();

        if(student.getFirstName() == null || student.getLastName()==null || student.getEmail()==null){
            throw new ExceptionHandler("value for 'firstName', 'lastName' and 'email' can not be excluded");
        }
            if(existingEmails.contains(newStudentEmail)){
                throw new ExceptionHandler("This email already exists, please try different email or the student is already registered");
        }
         studentService.createStudent(student);
        return Response.ok(student).build();
    }

    @Path("{id}")
    @DELETE
    public Response deleteStudent(@PathParam("id")Long id){
        if(!studentService.getStudentIDs().contains(id)){
            throw new ExceptionHandler("no student with this id found");
        }
        studentService.deleteStudent(id);
        return Response.ok().build();
    }


    @PUT
    public Response updateStudent(Student student){
        if(!studentService.getStudentIDs().contains(student.getId())){
            throw new ExceptionHandler("no student with this id found");
        }
        if(student.getFirstName() == null || student.getLastName()==null || student.getEmail()==null){
            throw new ExceptionHandler("value for 'firstName', 'lastName' and 'email' can not be excluded");
        }
        studentService.updateStudent(student);
        return Response.ok(student).build();
    }

    @Path("{id}")
    @PATCH
    public Response partialStudentUpdate(@PathParam("id") Long id, Student student){
        if(!studentService.getStudentIDs().contains(id)){
            throw new ExceptionHandler("no student with this id found");
        }

        Student updatedStudent = studentService.updateName(id, student.getFirstName(), student.getLastName());
        return Response.ok(updatedStudent).build();
    }

    @Path("getbylastname")
    @GET
    public Response getByLastName(@QueryParam("lastName")String lastName){
        if(!studentService.getStudentLastNames().contains(lastName)){
            throw new ExceptionHandler("There is no student with this lastname");
        }
        Student foundstudent = studentService.getByLastNameNamedParameter(lastName);
        return Response.ok(foundstudent).build();
    }
}
