
package theschoolmanagmentsystem.domain;

import java.util.List;

/**
 *  Acces inteface for Education modell. 
 * 
 * @author ITHSivju
 */
public interface EducationDAO {
    
    /**
     * Save a newly created Education to database.
     * Here is used commit()
     * For editing, use editEducation() method
     * @param e - newly created Education
     */
    public void createEducation(Education e);
    
    /**
     * Method used for editing Education
     * F.ex. after changing name or
     * adding a teacher.
     * Here is used merge()
     * For creating new Education use createEducation()
     * 
     * @param e - Education to edit
     */
    public void editEducation(Education e);
    
    /**
     * Returns Education with the speciffic id.
     * @param id
     * @return Education
     */
    public Education findById(Long id);
    
    /**
     * Returns a List od Educations based on a
     * String parameter.
     * @param name
     * @return List of Education
     */
    public List<Education> findByName(String name);
    
    /**
     * Delete a speciffic Education
     * 
     * @param e - Education to be deleted.
     */
    public void deleteEducation(Education e);
    
    /**
     * Delete a Education with a speciffic id.
     *
     * @param id 
     */
    public void deleteEducation(Long id);
}
