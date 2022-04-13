package se.iths.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Subject{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty
    private String name;

    @ManyToMany()
    //@JsonManagedReference
    //@JsonIgnoreProperties("subjects")
    //@JsonIgnoreProperties(ignoreUnknown = true)
    //@JsonbTransient
    public List<Student> students = new ArrayList<>();

    @ManyToOne
    private Teacher teacher;

    /*Om subject har en elev kopplad till sig så kan man ta bort subject
     * utan att eleven försvinner och man får inte error.
     * Däremot om subject har en lärare kopplad till sig så går det inte att ta bort subject
     * utan att få error. Men går det inte heller att ta bort läraren utan att få error, hur
     * får bort kopplingen mellan subject och teacher?*/

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

    public void removeTeacherFromSubject(Teacher teacher){
        teacher.getSubjects().remove(this);
    }
}
