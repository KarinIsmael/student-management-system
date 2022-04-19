package se.iths.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Subject{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty
    private String name;

    @ManyToMany (cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public List<Student> students = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Teacher teacher;

    public Subject() {
    }

    public Subject(String name) {
        this.name = name;
    }

    public Subject(String name, Teacher teacher) {
        this.name = name;
        this.teacher = teacher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void removeStudentFromSubject(Student student) {
        boolean remove = students.remove(student);
        if (remove) {
            student.getSubjects().remove(this);
        }
    }
}
