package theschoolmanagmentsystem.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException;

/**
 * @author ITHSivju
 */
public class StudentJpaController implements Serializable, StudentDAO {

    //TODO implement Logger
    
    public StudentJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void createStudent(Student student) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Education education = student.getEducation();
            if (education != null) {
                education = em.getReference(education.getClass(), education.getId());
                student.setEducation(education);
            }
            em.persist(student);
            if (education != null) {
                education.getStudents().add(student);
                education = em.merge(education);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void editStudent(Student student) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Student persistentStudent = em.find(Student.class, student.getPn());
            Education educationOld = persistentStudent.getEducation();
            Education educationNew = student.getEducation();
            if (educationNew != null) {
                educationNew = em.getReference(educationNew.getClass(), educationNew.getId());
                student.setEducation(educationNew);
            }
            student = em.merge(student);
            if (educationOld != null && !educationOld.equals(educationNew)) {
                educationOld.getStudents().remove(student);
                educationOld = em.merge(educationOld);
            }
            if (educationNew != null && !educationNew.equals(educationOld)) {
                educationNew.getStudents().add(student);
                educationNew = em.merge(educationNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = student.getPn();
                if (findStudentById(id) == null) {
                    throw new NonexistentEntityException("The student with id " + id + " no longer exists.");
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
    public void destroyStudent(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Student student;
            try {
                student = em.getReference(Student.class, id);
                student.getPn();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The student with id " + id + " no longer exists.", enfe);
            }
            Education education = student.getEducation();
            if (education != null) {
                education.getStudents().remove(student);
                education = em.merge(education);
            }
            em.remove(student);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void destroyStudent(Student s) throws NonexistentEntityException {
        EntityManager em = null;
        long id;
        if (s != null) {
            id = s.getPn();
        } else {
            throw new NonexistentEntityException("No such Student");
        }

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Student student;
            try {
                student = em.getReference(Student.class, id);
                student.getPn();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The student with id " + id + " no longer exists.", enfe);
            }
            Education education = student.getEducation();
            if (education != null) {
                education.getStudents().remove(student);
                education = em.merge(education);
            }
            em.remove(student);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Student> findStudentEntities() {
        return findStudentEntities(true, -1, -1);
    }

    public List<Student> findStudentEntities(int maxResults, int firstResult) {
        return findStudentEntities(false, maxResults, firstResult);
    }

    private List<Student> findStudentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Student.class));
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
    public Student findStudentById(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Student.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getStudentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Student> rt = cq.from(Student.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Student> findStudentByName(String name) {

        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("Select s FROM Student s WHERE s.firstName LIKE :name OR s.surName LIKE :name");
            query.setParameter("name", name);
            return query.getResultList();
        } finally {
            em.close();
        }

    }

//    @Override
//    public List<Student> findStudentBySurName(String surName) {
//
//        EntityManager em = getEntityManager();
//        try {
//            Query query = em.createQuery("Select s FROM Student s WHERE s.surName LIKE :surName");
//            query.setParameter("surName", surName);
//            return query.getResultList();
//        } finally {
//            em.close();
//        }
//    }

}
