package se.iths.service;

import se.iths.entity.Subject;
import se.iths.entity.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class TeacherService {

    @PersistenceContext
    EntityManager entityManager;

    public List<Teacher> getAllTeachers(){
        return entityManager.createQuery("SELECT i FROM Teacher i", Teacher.class)
                .getResultList();
    }

    public void createTeacher(Teacher teacher){
        entityManager.persist(teacher);
    }

    public void deleteTeacher(Long id){
        Teacher foundTeacher = entityManager.find(Teacher.class, id);

        for (Subject subject: foundTeacher.getSubjects()){
            foundTeacher.removeSubjectFromTeacher(subject);
        }
        entityManager.remove(foundTeacher);
    }

    public List<Teacher> getTeacherEmails() {
        return entityManager.createQuery("SELECT i.email FROM Teacher i", Teacher.class)
                .getResultList();
    }

    public List<Teacher> getTeacherIDs() {
        return entityManager.createQuery("SELECT i.id FROM Teacher i", Teacher.class)
                .getResultList();
    }
    public Teacher findTeacherById(Long id) {
        String query = "SELECT i FROM Teacher i WHERE i.id = :id";
        return entityManager.createQuery(query, Teacher.class)
                .setParameter("id", id).getSingleResult();
    }
}
