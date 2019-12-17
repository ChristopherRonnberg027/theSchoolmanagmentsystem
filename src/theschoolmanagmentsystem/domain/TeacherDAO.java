
package theschoolmanagmentsystem.domain;

import java.util.List;

/**
 *  Acces inteface for Teacher modell. 
 * 
 * @author ITHSivju
 */
public interface TeacherDAO {
    
    /**
     * Save a newly created Teacher to database.
     * Here is used commit()
     * For editing a Teacher, use editStudent() method
     * @param t - newly created Teacher
     */
    public void createTeacher(Teacher t);
    
    /**
     * Method used for editing Teacher
     * F.ex. after changing name or
     * Course.
     * Here is used merge()
     * For creating new Teacher use createTeacher()
     * 
     * @param t - Teacher to edit
     */
    public void editTeacher(Teacher t);
    
    /**
     * Returns Teacher with the speciffic id.
     * @param id
     * @return Teacher
     */
    public Teacher findById(Long id);
    
    /**
     * Returns a List od Teacher based on a
     * String parameter.
     * @param firstName
     * @return List of Teacher
     */
    public List<Teacher> findByFirstName(String firstName);
    
    /**
     * Returns a List od Teacher based on a
     * String parameter.
     * @param lastName
     * @return List of Teacher
     */
    public List<Teacher> findByLasttName(String lastName);
    
    /**
     * Delete a speciffic Teacher
     * 
     * @param t - Teacher to be deleted.
     */
    public void deleteTeacher(Teacher t);
    
    /**
     * Delete a Teacher with a speciffic id.
     *
     * @param id 
     */
    public void deleteTeacher(Long id);
}
