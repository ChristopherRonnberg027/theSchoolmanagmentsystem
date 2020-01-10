
package databaseControl;

import java.util.List;
import theschoolmanagmentsystem.domain.Course;
import theschoolmanagmentsystem.domain.Education;
import theschoolmanagmentsystem.domain.Student;
import theschoolmanagmentsystem.domain.Teacher;

/**
 * Acces inteface for database
 * @author I.S.op187
 */
public interface dbDAO {
    
    /**
     * Save a newly created Course to database. Here is used commit() For
     * editing, use editCourse() method
     *
     * @param c - newly created Course
     */
    void createCourse(Course c);
    
    /**
     * Method used for editing course f.ex. after changing name or adding a
     * teacher. Here is used merge() For creating new Course use createCourse()
     *
     * @param c - Course to edit
     */
    void editCourse(Course c);
    
    /**
     * Delete a course with a speciffic id.
     *
     * @param id
     */
    void destroyCourse(Long id);
    
    /**
     * Delete a speciffic course
     *
     * @param c - Course to be deleted.
     */
    void destroyCourse(Course c);
    
    /**
     * Returns Course with the speciffic id.
     *
     * @param id
     * @return Course
     */
    Course findCourseById(Long id);
    
    /**
     * Get all the courses
     *
     * @return a List of all courses
     */
    List<Course> getAllCourses();
    
    /**
     * Returns a List od Courses based on a String parameter.
     *
     * @param name
     * @return List of Course
     */
    List<Course> findCourseByName(String name);
    
    /**
     * 
     * @return Course entities count
     */
    int getCourseCount();
    
    
    
    /**
     * Save a newly created Education to database. Here is used commit() For
     * editing, use editEducation() method
     *
     * @param e - newly created Education
     */
    void createEducation(Education e);
    
    /**
     * Method used for editing Education F.ex. after changing name or adding a
     * teacher. Here is used merge() For creating new Education use
     * createEducation()
     *
     * @param e - Education to edit
     */
    void editEducation(Education e);
    
    /**
     * Delete a Education with a speciffic id.
     *
     * @param id
     */
    void destroyEducation(Long id);
    
    /**
     * Delete a speciffic Education
     *
     * @param e - Education to be deleted.
     */
    void destroyEducation(Education e);
    
    /**
     * Returns Education with the speciffic id.
     *
     * @param id
     * @return Education
     */
    Course findEducationById(Long id);
    
    /**
     * Get all the Educations
     *
     * @return a List of all Education
     */
    List<Course> getAllEducations();
    
    /**
     * Returns a List od Educations based on a String parameter.
     *
     * @param name
     * @return List of Education
     */
    List<Course> findEducationByName(String name);
    
    /**
     * @return Education entities count 
     */
    int getEducationCount();
    
    
    /**
     * Save a newly created Student to database.
     * Here is used commit()
     * For editing a Student, use editStudent() method
     * @param s - newly created Student
     */
    void createStudent(Student s);
    
    /**
     * Method used for editing Student
     * F.ex. after changing name or
     * Education.
     * Here is used merge()
     * For creating new Student use createStudent()
     * 
     * @param s - Student to edit
     */
    void editStudent(Student s);
    
    /**
     * Delete a Student with a speciffic id.
     * @param pn - here the Personal Number is used as id 
     */
    void destroyStudent(Long pn);
    
    /**
     * Delete a speciffic Student
     * 
     * @param s - Student to be deleted.
     */
    void destroyStudent(Student s);
    
    /**
     * Returns Student with the speciffic id.
     * @param pn - here the Personal Number is used as id 
     * @return Student
     */
    Course findStudentById(Long pn);
    
    /**
     * @return a List of all Students
     */
    List<Course> getAllStudents();
    
    /**
     * Returns a List od Student based on a
     * String parameter.
     * @param firstName
     * @return List of Student
     */
    List<Course> findStudentByFirstName(String firstName);
    
    /**
     * Returns a List od Student based on a
     * String parameter.
     * @param surName
     * @return List of Student
     */
    List<Course> findStudentBySurName(String surName);
    
    /**
     * @return Student entities count
     */
    int getStudentCount();
    
    
    /**
     * Save a newly created Teacher to database. Here is used commit() For
     * editing a Teacher, use editStudent() method
     *
     * @param t - newly created Teacher
     */
    void createTeacher(Teacher t);
    
    /**
     * Method used for editing Teacher F.ex. after changing name or Course. Here
     * is used merge() For creating new Teacher use createTeacher()
     *
     * @param t - Teacher to edit
     */
    void editTeacher(Teacher t);
    
    /**
     * Delete a Teacher with a speciffic id.
     *
     * @param pn - here the Personal Number is used as id
     */
    void destroyTeacher(Long pn);
    
    /**
     * Delete a speciffic Teacher
     *
     * @param t - Teacher to be deleted.
     */
    void destroyTeacher(Teacher t);
    
    /**
     * Returns Teacher with the speciffic id.
     *
     * @param pn - here the Personal Number is used as id
     * @return Teacher
     */
    Course findTeacherById(Long pn);
    
    /**
     * @return a List of all the teachers
     */
    List<Course> getAllTeachers();
    
    /**
     * Returns a List od Teacher based on a String parameter.
     *
     * @param firstName
     * @return List of Teacher
     */
    List<Course> findTeacherByFirstName(String firstName);
    
    /**
     * Returns a List od Teacher based on a String parameter.
     *
     * @param surName
     * @return List of Teacher
     */
    List<Course> findTeacherBySurName(String surName);
    
    /**
     * @return Teacher entities count
     */
    int getTeacherCount();
    
    
}
