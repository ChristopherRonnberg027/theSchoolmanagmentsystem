
package theschoolmanagmentsystem.domain;

import java.util.List;

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
     */
    public void editStudent(Student s);
    
    /**
     * Returns Student with the speciffic id.
     * @param id
     * @return Student
     */
    public Student findById(Long id);
    
    /**
     * Returns a List od Student based on a
     * String parameter.
     * @param firstName
     * @return List of Student
     */
    public List<Student> findByFirstName(String firstName);
    
    /**
     * Returns a List od Student based on a
     * String parameter.
     * @param lastName
     * @return List of Student
     */
    public List<Student> findByLasttName(String lastName);
    
    /**
     * Delete a speciffic Student
     * 
     * @param s - Student to be deleted.
     */
    public void deleteStudent(Student s);
    
    /**
     * Delete a Student with a speciffic id.
     *
     * @param id 
     */
    public void deleteStudent(Long id);
}
