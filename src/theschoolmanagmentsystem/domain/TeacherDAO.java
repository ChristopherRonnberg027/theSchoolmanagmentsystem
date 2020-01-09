package theschoolmanagmentsystem.domain;

import java.util.List;
import theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException;

/**
 * Acces inteface for Teacher modell.
 *
 * @author ITHSivju
 */
public interface TeacherDAO {

    /**
     * Save a newly created Teacher to database. Here is used commit() For
     * editing a Teacher, use editStudent() method
     *
     * @param t - newly created Teacher
     */
    public void createTeacher(Teacher t);

    /**
     * Method used for editing Teacher F.ex. after changing name or Course. Here
     * is used merge() For creating new Teacher use createTeacher()
     *
     * @param t - Teacher to edit
     * @throws theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException
     */
    public void editTeacher(Teacher t) throws NonexistentEntityException, Exception;

    /**
     * Returns Teacher with the speciffic id.
     *
     * @param id
     * @return Teacher
     */
    public Teacher findTeacherById(Long id);

    /**
     * Returns a List od Teacher based on a String parameter.
     *
     * @param firstName
     * @return List of Teacher
     */
    public List<Teacher> findTeacherByFirstName(String firstName);

    /**
     * Returns a List od Teacher based on a String parameter.
     *
     * @param lastName
     * @return List of Teacher
     */
    public List<Teacher> findTeacherByLasttName(String lastName);

    /**
     * Delete a speciffic Teacher
     *
     * @param t - Teacher to be deleted.
     * @throws theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException
     */
    public void destroyTeacher(Teacher t) throws NonexistentEntityException;

    /**
     * Delete a Teacher with a speciffic id.
     *
     * @param id
     * @throws theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException
     */
    public void destroyTeacher(Long id) throws NonexistentEntityException;

    /**
     *
     * @return a List of all the teachers
     */
    public List<Teacher> findTeacherEntities();

    /**
     *
     * @return Teacher entities count
     */
    public int getTeacherCount();
}
