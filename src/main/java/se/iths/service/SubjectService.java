package se.iths.service;

import se.iths.entity.Student;
import se.iths.entity.Subject;
import se.iths.entity.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;

@Transactional
public class SubjectService {

    @PersistenceContext
    EntityManager entityManager;

    public void createSubject(Subject subject){
        entityManager.persist(subject);
    }

    public List<Subject> getAllSubjects(){
        return entityManager.createQuery("SELECT i FROM Subject i ", Subject.class)
                .getResultList();

    }

    public void addStudentToSubject(Long subjectId, Long studentId){
        Subject foundSubject = entityManager.find(Subject.class,subjectId);
        Student foundStudent = entityManager.find(Student.class, studentId);
        foundSubject.getStudents().add(foundStudent);
        foundStudent.getSubjects().add(foundSubject);
        //return foundSubject;
    }

    public Teacher findTeacherById(Long teacherId){
        String query = "SELECT i FROM Teacher i WHERE i.id = :teacherId";
        return entityManager.createQuery(query, Teacher.class).setParameter("teacherId", teacherId).getSingleResult();
    }

    public void addTeacherToSubject(Long subjectId, Long teacherId){
        Subject foundSubject = entityManager.find(Subject.class, subjectId);
        Teacher foundTeacher = entityManager.find(Teacher.class, teacherId);
        foundSubject.setTeacher(foundTeacher);
       foundTeacher.getSubjects().add(foundSubject);
    }

    public void deleteSubject(Long id){
        Subject foundSubject = entityManager.find(Subject.class, id);

        for (Student student: foundSubject.getStudents()){
            student.removeSubjectFromStudent(foundSubject);
            //foundSubject.removeStudentFromSubject(student);
            //student.removeSubjectFromStudent(foundSubject);
        }

        /*if(!foundSubject.getStudents().isEmpty()){
            throw new WebApplicationException(Response.status(Response.Status.CONFLICT)
                    .entity("This subject has registered students")
                    .type(MediaType.TEXT_PLAIN_TYPE).build());
        }*/
        //foundSubject.students.clear();
        //entityManager.refresh(foundSubject);
        //foundSubject.setTea;

        foundSubject.getTeacher().removeSubjectFromTeacher(foundSubject);
        entityManager.remove(foundSubject);
    }

    public List<Subject> getSubjectIDs() {
        return entityManager.createQuery("SELECT i.id FROM Subject i", Subject.class)
                .getResultList();
    }

    public List<Subject> getSubjectNames() {
        return entityManager.createQuery("SELECT i.name FROM Subject i", Subject.class)
                .getResultList();
    }

    public Subject findSubjectById(Long id) {
        String query = "SELECT i FROM Subject i WHERE i.id = :id";
        return entityManager.createQuery(query, Subject.class)
                .setParameter("id", id).getSingleResult();
    }
}
