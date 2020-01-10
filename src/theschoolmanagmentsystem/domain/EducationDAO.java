package theschoolmanagmentsystem.domain;

import java.util.List;
import theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException;

/**
 * Acces inteface for Education modell.
 *
 * @author ITHSivju
 */
public interface EducationDAO {

    /**
     * Save a newly created Education to database. Here is used commit() For
     * editing, use editEducation() method
     *
     * @param e - newly created Education
     */
    public void createEducation(Education e);

    /**
     * Method used for editing Education F.ex. after changing name or adding a
     * teacher. Here is used merge() For creating new Education use
     * createEducation()
     *
     * @param e - Education to edit
     * @throws theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException
     */
    public void editEducation(Education e) throws NonexistentEntityException, Exception;

    /**
     * Returns Education with the speciffic id.
     *
     * @param id
     * @return Education
     */
    public Education findEducationById(Long id);

    /**
     * Returns a List od Educations based on a String parameter.
     *
     * @param name
     * @return List of Education
     */
    public List<Education> findEducationByName(String name);

    /**
     * Delete a speciffic Education
     *
     * @param e - Education to be deleted.
     * @throws theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException
     */
    public void destroyEducation(Education e) throws NonexistentEntityException;

    /**
     * Delete a Education with a speciffic id.
     *
     * @param id
     * @throws theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException
     */
    public void destroyEducation(Long id) throws NonexistentEntityException;

    /**
     * Get all the Educations
     *
     * @return a List of all Education
     */
    public List<Education> findEducationEntities();
    
    /**
     * @return Education entities count 
     */
    public int getEducationCount();
}
