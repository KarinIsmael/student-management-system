package se.iths.service;

import se.iths.entity.Student;

import javax.mail.FetchProfile;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class StudentService {

    @PersistenceContext
    EntityManager entityManager;

    public List<Student> getAllStudents(){
        return entityManager.createQuery("SELECT i FROM Student i", Student.class)
                .getResultList();
    }

    public List<Student> getStudentEmails(){
        return entityManager.createQuery("SELECT i.email FROM Student i", Student.class)
                .getResultList();
    }
    public List<Student> getStudentLastNames(){
        return entityManager.createQuery("SELECT i.lastName FROM Student i", Student.class)
                .getResultList();
    }
    public List<Student> getStudentIDs(){
        return entityManager.createQuery("SELECT i.id FROM Student i", Student.class)
                .getResultList();
    }

    public void createStudent(Student student){
        entityManager.persist(student);
    }

    public void deleteStudent(Long id){
        Student foundStudent = entityManager.find(Student.class, id);
        entityManager.remove(foundStudent);
    }

    public void updateStudent(Student student){
        entityManager.merge(student);
    }

    public Student updateName(Long id, String firstName, String lastName){
        Student foundStudent = entityManager.find(Student.class, id);
        foundStudent.setFirstName(firstName);
        foundStudent.setLastName(lastName);
        return foundStudent;
    }

    public Student getByLastNameNamedParameter(String lastName){
        String query = "SELECT i FROM Student i WHERE i.lastName = :lastName";
        return entityManager.createQuery(query, Student.class).setParameter("lastName", lastName).getSingleResult();
    }

}
