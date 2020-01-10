package databaseControl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import theschoolmanagmentsystem.domain.Course;
import theschoolmanagmentsystem.domain.CourseJpaController;
import theschoolmanagmentsystem.domain.Education;
import theschoolmanagmentsystem.domain.EducationJpaController;
import theschoolmanagmentsystem.domain.Student;
import theschoolmanagmentsystem.domain.StudentJpaController;
import theschoolmanagmentsystem.domain.Teacher;
import theschoolmanagmentsystem.domain.TeacherJpaController;
import theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException;

/**
 *
 * @author I.S.op187
 */
public class dbControlJpaImpl implements dbDAO {

    private EntityManagerFactory emf;

    private CourseJpaController courseControler;
    private TeacherJpaController teacherControler;
    private EducationJpaController educationController;
    private StudentJpaController studentController;

    // Instantiates all entity controllers with local entity manager factory
    public dbControlJpaImpl() {
        emf = Persistence.createEntityManagerFactory("PU");
        this.courseControler = new CourseJpaController(emf);
        this.teacherControler = new TeacherJpaController(emf);
        this.educationController = new EducationJpaController(emf);
        this.studentController = new StudentJpaController(emf);
    }

    
    // Course entity control
    
    @Override
    public void createCourse(Course c) {
        courseControler.createCourse(c);
    }

    @Override
    public void editCourse(Course c) throws Exception {
        try {
            courseControler.editCourse(c);
        } catch (Exception ex) {
            throw new Exception("Something went wrong...", ex);
        }
    }

    @Override
    public void destroyCourse(Long id) throws NonexistentEntityException {
        try {
            courseControler.destroyCourse(id);
        } catch (NonexistentEntityException ex) {
            throw new NonexistentEntityException("No such entity", ex);
        }
    }

    @Override
    public void destroyCourse(Course c) throws NonexistentEntityException {
        try {
            courseControler.destroyCourse(c);
        } catch (NonexistentEntityException ex) {
            throw new NonexistentEntityException("No such entity", ex);
        }
    }

    @Override
    public Course findCourseById(Long id) {
        return courseControler.findCourseById(id);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseControler.findCourseEntities();
    }

    @Override
    public List<Course> findCourseByName(String name) {
        return courseControler.findCourseByName(name);
    }

    @Override
    public int getCourseCount() {
        return courseControler.getCourseCount();
    }
    
    
    // Education entity control

    @Override
    public void createEducation(Education e) {
        educationController.createEducation(e);
    }

    @Override
    public void editEducation(Education e) throws Exception {
        try {
            educationController.editEducation(e);
        } catch (Exception ex) {
            throw new Exception("Something went wrong...", ex);
        }
    }

    @Override
    public void destroyEducation(Long id) throws NonexistentEntityException {
        educationController.destroyEducation(id);
    }

    @Override
    public void destroyEducation(Education e) throws NonexistentEntityException {
        educationController.destroyEducation(e);
    }

    @Override
    public Education findEducationById(Long id) {
        return educationController.findEducationById(id);
    }

    @Override
    public List<Education> getAllEducations() {
        return educationController.findEducationEntities();
    }

    @Override
    public List<Education> findEducationByName(String name) {
        return educationController.findEducationByName(name);
    }

    @Override
    public int getEducationCount() {
        return educationController.getEducationCount();
    }
    
    
    // Student entity control

    @Override
    public void createStudent(Student s) {
        studentController.createStudent(s);
    }

    @Override
    public void editStudent(Student s) throws Exception {
        try {
            studentController.editStudent(s);
        } catch (Exception ex) {
            throw new Exception("Something went wrong...", ex);
        }
    }

    @Override
    public void destroyStudent(Long pn) throws NonexistentEntityException {
        try {
            studentController.destroyStudent(pn);
        } catch (NonexistentEntityException ex) {
            throw new NonexistentEntityException("No such entity", ex);
        }
    }

    @Override
    public void destroyStudent(Student s) throws NonexistentEntityException {
        try {
            studentController.destroyStudent(s);
        } catch (NonexistentEntityException ex) {
            throw new NonexistentEntityException("No such entity", ex);
        }
    }

    @Override
    public Student findStudentById(Long pn) {
        return studentController.findStudentById(pn);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentController.findStudentEntities();
    }

    @Override
    public List<Student> findStudentByFirstName(String firstName) {
        return studentController.findStudentByFirstName(firstName);
    }

    @Override
    public List<Student> findStudentBySurName(String surName) {
        return studentController.findStudentBySurName(surName);
    }

    @Override
    public int getStudentCount() {
        return studentController.getStudentCount();
    }

    
    // Teacher entity control
    
    @Override
    public void createTeacher(Teacher t) {
        teacherControler.createTeacher(t);
    }

    @Override
    public void editTeacher(Teacher t) throws Exception {
        try {
            teacherControler.editTeacher(t);
        } catch (Exception ex) {
            throw new Exception("Something went wwrong", ex);
        }
    }

    @Override
    public void destroyTeacher(Long pn) throws NonexistentEntityException {
        try {
            teacherControler.destroyTeacher(pn);
        } catch (NonexistentEntityException ex) {
            throw new NonexistentEntityException("No such entity", ex);
        }
    }

    @Override
    public void destroyTeacher(Teacher t) throws NonexistentEntityException {
        try {
            teacherControler.destroyTeacher(t);
        } catch (NonexistentEntityException ex) {
            throw new NonexistentEntityException("No such entity", ex);
        }
    }

    @Override
    public Teacher findTeacherById(Long pn) {
        return teacherControler.findTeacherById(pn);
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherControler.findTeacherEntities();
    }

    @Override
    public List<Teacher> findTeacherByFirstName(String firstName) {
        return teacherControler.findTeacherByFirstName(firstName);
    }

    @Override
    public List<Teacher> findTeacherBySurName(String surName) {
        return teacherControler.findTeacherBySurName(surName);
    }

    @Override
    public int getTeacherCount() {
        return teacherControler.getTeacherCount();
    }

}
