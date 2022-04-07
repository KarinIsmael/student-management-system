package se.iths.service;

import se.iths.entity.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;
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
}
