package se.iths.service;

import se.iths.entity.Student;
import se.iths.entity.Subject;
import se.iths.entity.Teacher;
import se.iths.rest.ExceptionHandler;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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
    }

    public Teacher findTeacherById(Long teacherId){
        String query = "SELECT i FROM Teacher i WHERE i.id = :teacherId";
        return entityManager.createQuery(query, Teacher.class).setParameter("teacherId", teacherId).getSingleResult();
    }

    public void addTeacherToSubject(Long subjectId, Long teacherId){
        Subject foundSubject = entityManager.find(Subject.class, subjectId);
        Teacher foundTeacher = entityManager.find(Teacher.class, teacherId);

        if(foundTeacher==null){
            throw new ExceptionHandler("Cannot find Teacher with such ID");
        }
        foundSubject.setTeacher(foundTeacher);
       foundTeacher.getSubjects().add(foundSubject);
    }

    public void deleteSubject(Long id){
        Subject foundSubject = entityManager.find(Subject.class, id);

        for (Student student: foundSubject.getStudents()){
            student.removeSubjectFromStudent(foundSubject);
        }
        entityManager.remove(foundSubject);

       /* if(!foundSubject.getStudents().isEmpty()){
            throw new ExceptionHandler("This subject has registered students");
        }*/

        if(foundSubject.getTeacher() != null){
            foundSubject.getTeacher().removeSubjectFromTeacher(foundSubject);
        }

    }

    public List<Subject> getSubjectIDs() {
        return entityManager.createQuery("SELECT i.id FROM Subject i", Subject.class)
                .getResultList();
    }

    public List<Student> getStudentIDs() {
        return entityManager.createQuery("SELECT i.id FROM Student i", Student.class)
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
