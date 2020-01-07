
package theschoolmanagmentsystem.domain;

import java.util.List;

/**
 *  Acces inteface for Course modell. 
 * 
 * @author ITHSivju
 */
public interface CourseDAO {
    
    /**
     * Save a newly created Course to database.
     * Here is used commit()
     * For editing, use editCourse() method
     * @param c - newly created Course
     */
    public void create(Course c);
    
    /**
     * Method used for editing course
     * F.ex. after changing name or
     * adding a teacher.
     * Here is used merge()
     * For creating new Course use createCourse()
     * 
     * @param c - Course to edit
     */
    public void edit(Course c);
    
    /**
     * Returns Course with the speciffic id.
     * @param id
     * @return Course
     */
    public Course findCourseById(Long id);
    
    /**
     * Returns a List od Courses based on a
     * String parameter.
     * @param name
     * @return List of Course
     */
    public List<Course> findByName(String name);
    
    /**
     * Delete a speciffic course
     * 
     * @param c - Course to be deleted.
     */
    public void deleteCourse(Course c);
    
    /**
     * Delete a course with a speciffic id.
     *
     * @param id 
     */
    public void deleteCourse(Long id);
}
