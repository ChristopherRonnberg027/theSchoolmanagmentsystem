package theschoolmanagmentsystem.domain;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException;

/**
 * Acces inteface for Course modell.
 *
 * @author ITHSivju
 */
public interface CourseDAO {

    /**
     * Save a newly created Course to database. Here is used commit() For
     * editing, use editCourse() method
     *
     * @param c - newly created Course
     */
    public void createCourse(Course c);

    /**
     * Method used for editing course F.ex. after changing name or adding a
     * teacher. Here is used merge() For creating new Course use createCourse()
     *
     * @param c - Course to edit
     * @throws
     * theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException
     */
    public void editCourse(Course c) throws NonexistentEntityException, Exception;

    /**
     * Returns Course with the speciffic id.
     *
     * @param id
     * @return Course
     */
    public Course findCourseById(Long id) throws EntityNotFoundException;

    /**
     * Returns a List od Courses based on a String parameter.
     *
     * @param name
     * @return List of Course
     */
    public List<Course> findCourseByName(String name) throws EntityNotFoundException;

    /**
     * Delete a speciffic course
     *
     * @param c - Course to be deleted.
     * @throws
     * theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException
     */
    public void destroyCourse(Course c) throws NonexistentEntityException;

    /**
     * Delete a course with a speciffic id.
     *
     * @param id
     * @throws
     * theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException
     */
    public void destroyCourse(Long id) throws NonexistentEntityException;

    /**
     * Get all the courses
     *
     * @return a List of all courses
     */
    public List<Course> findCourseEntities();
    
    /**
     * @return Course entities count
     */
    public int getCourseCount();
}
