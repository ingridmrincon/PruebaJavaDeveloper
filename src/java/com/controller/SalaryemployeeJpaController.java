package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.model.Employee;
import com.model.Salaryemployee;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author Usuario Ingrid Rincón 
 */
public class SalaryemployeeJpaController implements Serializable {

    private static final long serialVersionUID = 1L;

    public SalaryemployeeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Salaryemployee salaryemployee) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Employee idemployee = salaryemployee.getIdemployee();
            if (idemployee != null) {
                idemployee = em.getReference(idemployee.getClass(), idemployee.getIdemployee());
                salaryemployee.setIdemployee(idemployee);
            }
            em.persist(salaryemployee);
            if (idemployee != null) {
                idemployee.getSalaryemployeeCollection().add(salaryemployee);
                idemployee = em.merge(idemployee);
            }
            utx.commit();
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Salaryemployee salaryemployee) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Salaryemployee persistentSalaryemployee = em.find(Salaryemployee.class, salaryemployee.getIdsalary());
            Employee idemployeeOld = persistentSalaryemployee.getIdemployee();
            Employee idemployeeNew = salaryemployee.getIdemployee();
            if (idemployeeNew != null) {
                idemployeeNew = em.getReference(idemployeeNew.getClass(), idemployeeNew.getIdemployee());
                salaryemployee.setIdemployee(idemployeeNew);
            }
            salaryemployee = em.merge(salaryemployee);
            if (idemployeeOld != null && !idemployeeOld.equals(idemployeeNew)) {
                idemployeeOld.getSalaryemployeeCollection().remove(salaryemployee);
                idemployeeOld = em.merge(idemployeeOld);
            }
            if (idemployeeNew != null && !idemployeeNew.equals(idemployeeOld)) {
                idemployeeNew.getSalaryemployeeCollection().add(salaryemployee);
                idemployeeNew = em.merge(idemployeeNew);
            }
            utx.commit();
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = salaryemployee.getIdsalary();
                if (findSalaryemployee(id) == null) {
                    throw new NonexistentEntityException("The salaryemployee with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Salaryemployee salaryemployee;
            try {
                salaryemployee = em.getReference(Salaryemployee.class, id);
                salaryemployee.getIdsalary();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The salaryemployee with id " + id + " no longer exists.", enfe);
            }
            Employee idemployee = salaryemployee.getIdemployee();
            if (idemployee != null) {
                idemployee.getSalaryemployeeCollection().remove(salaryemployee);
                idemployee = em.merge(idemployee);
            }
            em.remove(salaryemployee);
            utx.commit();
        } catch (NonexistentEntityException | IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Salaryemployee> findSalaryemployeeEntities() {
        return findSalaryemployeeEntities(true, -1, -1);
    }

    public List<Salaryemployee> findSalaryemployeeEntities(int maxResults, int firstResult) {
        return findSalaryemployeeEntities(false, maxResults, firstResult);
    }

    @SuppressWarnings("unchecked")
    private List<Salaryemployee> findSalaryemployeeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery <Object> cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Salaryemployee.class));
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

    public Salaryemployee findSalaryemployee(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Salaryemployee.class, id);
        } finally {
            em.close();
        }
    }


    @SuppressWarnings("unchecked")
    public int getSalaryemployeeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery <Object> cq = em.getCriteriaBuilder().createQuery();
            @SuppressWarnings("unchecked")
            Root<Salaryemployee> rt = cq.from(Salaryemployee.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Number) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
