package theschoolmanagmentsystem.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * @author ITHSivju
 */
@Entity
public class Education implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    private String name;

    @Basic
    private String start;

    @Basic
    private String end;

    @Basic
    private String schoolBreak;

    @OneToMany(mappedBy = "education")
    private List<Student> students;

    @ManyToMany
    private List<Course> courses;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return this.start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getSchoolBreak() {
        return this.schoolBreak;
    }

    public void setSchoolBreak(String schoolBreak) {
        this.schoolBreak = schoolBreak;
    }

    public List<Student> getStudents() {
        if (students == null) {
            students = new ArrayList<>();
        }
        return this.students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        getStudents().add(student);
        student.setEducation(this);
    }

    public void removeStudent(Student student) {
        getStudents().remove(student);
        student.setEducation(null);
    }

    public List<Course> getCourses() {
        if (courses == null) {
            courses = new ArrayList<>();
        }
        return this.courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        getCourses().add(course);
        course.getEducations().add(this);
    }

    public void removeCourse(Course course) {
        getCourses().remove(course);
        course.getEducations().remove(this);
    }

    @Override
    public String toString() {
        return "Id: " + id + ",\tName:" + name + ",\tlasts from " + start + " to " + end + ",\tschoolBreak " + schoolBreak;
    }
    
    

}