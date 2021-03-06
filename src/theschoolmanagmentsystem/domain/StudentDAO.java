
package theschoolmanagmentsystem.domain;

import java.util.List;
import theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException;

/**
 *  Acces inteface for Student modell. 
 * 
 * @author ITHSivju
 */
public interface StudentDAO {
    
    /**
     * Save a newly created Student to database.
     * Here is used commit()
     * For editing a Student, use editStudent() method
     * @param s - newly created Student
     */
    public void createStudent(Student s);
    
    /**
     * Method used for editing Student
     * F.ex. after changing name or
     * Education.
     * Here is used merge()
     * For creating new Student use createStudent()
     * 
     * @param s - Student to edit
     * @throws theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException
     */
    public void editStudent(Student s)throws NonexistentEntityException, Exception;
    
    /**
     * Returns Student with the speciffic id.
     * @param id
     * @return Student
     */
    public Student findStudentById(Long id);
    
    /**
     * Returns a List od Student that mach a certain name,
     * borh first and last.
     * @param name
     * @return List of Student
     */
    public List<Student> findStudentByName(String name);
    
//    /**
//     * Returns a List od Student based on a
//     * String parameter.
//     * @param surName
//     * @return List of Student
//     */
//    public List<Student> findStudentBySurName(String surName);
    
    /**
     * Delete a speciffic Student
     * 
     * @param s - Student to be deleted.
     * @throws theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException
     */
    public void destroyStudent(Student s)throws NonexistentEntityException;
    
    /**
     * Delete a Student with a speciffic id.
     *
     * @param id 
     * @throws theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException 
     */
    public void destroyStudent(Long id)throws NonexistentEntityException;
    
    /**
     * @return a List of all Students
     */
    public List<Student> findStudentEntities();
    
    /**
     * @return Student entities count
     */
    public int getStudentCount();
}
