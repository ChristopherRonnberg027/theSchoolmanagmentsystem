
package theschoolmanagmentsystem.databaseControl;

import java.util.List;
import theschoolmanagmentsystem.domain.Course;
import theschoolmanagmentsystem.domain.Education;
import theschoolmanagmentsystem.domain.Student;
import theschoolmanagmentsystem.domain.Teacher;
import theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException;

/**
 * Acces inteface for database
 * @author I.S.op187
 */
public interface DbDAO {
    
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
     * @throws java.lang.Exception
     */
    void editCourse (Course c) throws Exception ;
    
    /**
     * Delete a course with a speciffic id.
     *
     * @param id
     * @throws theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException
     */
    void destroyCourse(Long id) throws NonexistentEntityException;
    
    /**
     * Delete a speciffic course
     *
     * @param c - Course to be deleted.
     * @throws theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException
     */
    void destroyCourse(Course c) throws NonexistentEntityException;
    
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
     * @throws java.lang.Exception
     */
    void editEducation(Education e) throws Exception;
    
    /**
     * Delete a Education with a speciffic id.
     *
     * @param id
     * @throws theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException
     */
    void destroyEducation(Long id) throws NonexistentEntityException;
    
    /**
     * Delete a speciffic Education
     *
     * @param e - Education to be deleted.
     * @throws theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException
     */
    void destroyEducation(Education e) throws NonexistentEntityException;
    
    /**
     * Returns Education with the speciffic id.
     *
     * @param id
     * @return Education
     */
    Education findEducationById(Long id);
    
    /**
     * Get all the Educations
     *
     * @return a List of all Education
     */
    List<Education> getAllEducations();
    
    /**
     * Returns a List od Educations based on a String parameter.
     *
     * @param name
     * @return List of Education
     */
    List<Education> findEducationByName(String name);
    
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
     * @throws java.lang.Exception
     */
    void editStudent(Student s) throws Exception;
    
    /**
     * Delete a Student with a speciffic id.
     * @param pn - here the Personal Number is used as id 
     * @throws theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException 
     */
    void destroyStudent(Long pn) throws NonexistentEntityException;
    
    /**
     * Delete a speciffic Student
     * 
     * @param s - Student to be deleted.
     * @throws theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException
     */
    void destroyStudent(Student s) throws NonexistentEntityException;
    
    /**
     * Returns Student with the speciffic id.
     * @param pn - here the Personal Number is used as id 
     * @return Student
     */
    Student findStudentById(Long pn);
    
    /**
     * @return a List of all Students
     */
    List<Student> getAllStudents();
    
    /**
     * Returns a List od Student that mach a certain name,
     * borh first and last.
     * @param name
     * @return List of Student
     */
    List<Student> findStudentByName(String name);
    
//    /**
//     * Returns a List od Student based on a
//     * String parameter.
//     * @param surName
//     * @return List of Student
//     */
//    List<Student> findStudentBySurName(String surName);
//    
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
     * @throws java.lang.Exception
     */
    void editTeacher(Teacher t) throws Exception;
    
    /**
     * Delete a Teacher with a speciffic id.
     *
     * @param pn - here the Personal Number is used as id
     * @throws theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException
     */
    void destroyTeacher(Long pn) throws NonexistentEntityException;
    
    /**
     * Delete a speciffic Teacher
     *
     * @param t - Teacher to be deleted.
     * @throws theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException
     */
    void destroyTeacher(Teacher t) throws NonexistentEntityException;
    
    /**
     * Returns Teacher with the speciffic id.
     *
     * @param pn - here the Personal Number is used as id
     * @return Teacher
     */
    Teacher findTeacherById(Long pn);
    
    /**
     * @return a List of all the teachers
     */
    List<Teacher> getAllTeachers();
    
    /**
     * Returns a List od Teacher based on a String parameter.
     * It searches both for name and surname
     * @param name
     * @return List of Teacher
     */
    List<Teacher> findTeacherByName(String name);
    
//    /**
//     * Returns a List od Teacher based on a String parameter.
//     *
//     * @param surName
//     * @return List of Teacher
//     */
//    List<Teacher> findTeacherBySurName(String surName);
//    
    /**
     * @return Teacher entities count
     */
    int getTeacherCount();
    
    
}
