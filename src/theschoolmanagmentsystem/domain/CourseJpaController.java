package theschoolmanagmentsystem.domain;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException;

/**
 * @author ITHSivju
 */
public class CourseJpaController implements Serializable, CourseDAO {

    //TODO implement Logger
    public CourseJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void createCourse(Course course) {
        if (course.getEducations() == null) {
            course.setEducations(new ArrayList<Education>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Education> attachedEducations = new ArrayList<Education>();
            for (Education educationsEducationToAttach : course.getEducations()) {
                educationsEducationToAttach = em.getReference(educationsEducationToAttach.getClass(), educationsEducationToAttach.getId());
                attachedEducations.add(educationsEducationToAttach);
            }
            course.setEducations(attachedEducations);
            em.persist(course);
            for (Education educationsEducation : course.getEducations()) {
                educationsEducation.getCourses().add(course);
                educationsEducation = em.merge(educationsEducation);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void editCourse(Course course) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Course persistentCourse = em.find(Course.class, course.getId());
            List<Education> educationsOld = persistentCourse.getEducations();
            List<Education> educationsNew = course.getEducations();
            List<Education> attachedEducationsNew = new ArrayList<Education>();
            for (Education educationsNewEducationToAttach : educationsNew) {
                educationsNewEducationToAttach = em.getReference(educationsNewEducationToAttach.getClass(), educationsNewEducationToAttach.getId());
                attachedEducationsNew.add(educationsNewEducationToAttach);
            }
            educationsNew = attachedEducationsNew;
            course.setEducations(educationsNew);
            course = em.merge(course);
            for (Education educationsOldEducation : educationsOld) {
                if (!educationsNew.contains(educationsOldEducation)) {
                    educationsOldEducation.getCourses().remove(course);
                    educationsOldEducation = em.merge(educationsOldEducation);
                }
            }
            for (Education educationsNewEducation : educationsNew) {
                if (!educationsOld.contains(educationsNewEducation)) {
                    educationsNewEducation.getCourses().add(course);
                    educationsNewEducation = em.merge(educationsNewEducation);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = course.getId();
                if (findCourseById(id) == null) {
                    throw new NonexistentEntityException("The course with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void destroyCourse(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Course course;
            try {
                course = em.getReference(Course.class, id);
                course.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The course with id " + id + " no longer exists.", enfe);
            }
            List<Education> educations = course.getEducations();
            for (Education educationsEducation : educations) {
                educationsEducation.getCourses().remove(course);
                educationsEducation = em.merge(educationsEducation);
            }
            em.remove(course);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void destroyCourse(Course c) throws NonexistentEntityException {
        EntityManager em = null;
        Long id;
        if (c != null) {
            id = c.getId();
        } else {
            throw new NonexistentEntityException("No such course");
        }
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Course course;
            try {
                course = em.getReference(Course.class, id);
                course.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The course with id " + id + " no longer exists.", enfe);
            }
            List<Education> educations = course.getEducations();
            for (Education educationsEducation : educations) {
                educationsEducation.getCourses().remove(course);
                educationsEducation = em.merge(educationsEducation);
            }
            em.remove(course);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Course> findCourseEntities() {
        return findCourseEntities(true, -1, -1);
    }

    public List<Course> findCourseEntities(int maxResults, int firstResult) {
        return findCourseEntities(false, maxResults, firstResult);
    }

    private List<Course> findCourseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Course.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Course findCourseById(Long id) throws EntityNotFoundException {
        Course courseToReturn;
        EntityManager em = getEntityManager();
        try {
            courseToReturn = em.find(Course.class, id);
            if(courseToReturn!=null)
                return courseToReturn;
            else
                throw new EntityNotFoundException("Entity not found");
        } finally {
            em.close();
        }
    }

    @Override
    public int getCourseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Course> rt = cq.from(Course.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Course> findCourseByName(String name) throws EntityNotFoundException{
        EntityManager em = getEntityManager();
        List<Course> listToReturn;
        try {
            Query query = em.createQuery("Select c FROM Course c WHERE c.name LIKE :name");
            query.setParameter("name", name);
            listToReturn = query.getResultList();
            if(listToReturn!=null)
                return listToReturn;
            else 
                throw new EntityNotFoundException("There are no courses in database.");
        } finally {
            em.close();
        }
    }

}
