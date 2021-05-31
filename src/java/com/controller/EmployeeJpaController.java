/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.controller.exceptions.IllegalOrphanException;
import com.controller.exceptions.NonexistentEntityException;
import com.controller.exceptions.RollbackFailureException;
import com.model.Employee;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.model.Salaryemployee;
import java.util.ArrayList;
import java.util.Collection;
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
 * @author Usuario
 */
public class EmployeeJpaController implements Serializable {

    private static final long serialVersionUID = 1L;

    public EmployeeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Employee employee) throws RollbackFailureException, Exception {
        if (employee.getSalaryemployeeCollection() == null) {
            employee.setSalaryemployeeCollection(new ArrayList<>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Salaryemployee> attachedSalaryemployeeCollection = new ArrayList<>();
            for (Salaryemployee salaryemployeeCollectionSalaryemployeeToAttach : employee.getSalaryemployeeCollection()) {
                salaryemployeeCollectionSalaryemployeeToAttach = em.getReference(salaryemployeeCollectionSalaryemployeeToAttach.getClass(), salaryemployeeCollectionSalaryemployeeToAttach.getIdsalary());
                attachedSalaryemployeeCollection.add(salaryemployeeCollectionSalaryemployeeToAttach);
            }
            employee.setSalaryemployeeCollection(attachedSalaryemployeeCollection);
            em.persist(employee);
            for (Salaryemployee salaryemployeeCollectionSalaryemployee : employee.getSalaryemployeeCollection()) {
                Employee oldIdemployeeOfSalaryemployeeCollectionSalaryemployee = salaryemployeeCollectionSalaryemployee.getIdemployee();
                salaryemployeeCollectionSalaryemployee.setIdemployee(employee);
                salaryemployeeCollectionSalaryemployee = em.merge(salaryemployeeCollectionSalaryemployee);
                if (oldIdemployeeOfSalaryemployeeCollectionSalaryemployee != null) {
                    oldIdemployeeOfSalaryemployeeCollectionSalaryemployee.getSalaryemployeeCollection().remove(salaryemployeeCollectionSalaryemployee);
                    oldIdemployeeOfSalaryemployeeCollectionSalaryemployee = em.merge(oldIdemployeeOfSalaryemployeeCollectionSalaryemployee);
                }
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

    public void edit(Employee employee) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Employee persistentEmployee = em.find(Employee.class, employee.getIdemployee());
            Collection<Salaryemployee> salaryemployeeCollectionOld = persistentEmployee.getSalaryemployeeCollection();
            Collection<Salaryemployee> salaryemployeeCollectionNew = employee.getSalaryemployeeCollection();
            List<String> illegalOrphanMessages = null;
            for (Salaryemployee salaryemployeeCollectionOldSalaryemployee : salaryemployeeCollectionOld) {
                if (!salaryemployeeCollectionNew.contains(salaryemployeeCollectionOldSalaryemployee)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain Salaryemployee " + salaryemployeeCollectionOldSalaryemployee + " since its idemployee field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Salaryemployee> attachedSalaryemployeeCollectionNew = new ArrayList<>();
            for (Salaryemployee salaryemployeeCollectionNewSalaryemployeeToAttach : salaryemployeeCollectionNew) {
                salaryemployeeCollectionNewSalaryemployeeToAttach = em.getReference(salaryemployeeCollectionNewSalaryemployeeToAttach.getClass(), salaryemployeeCollectionNewSalaryemployeeToAttach.getIdsalary());
                attachedSalaryemployeeCollectionNew.add(salaryemployeeCollectionNewSalaryemployeeToAttach);
            }
            salaryemployeeCollectionNew = attachedSalaryemployeeCollectionNew;
            employee.setSalaryemployeeCollection(salaryemployeeCollectionNew);
            employee = em.merge(employee);
            for (Salaryemployee salaryemployeeCollectionNewSalaryemployee : salaryemployeeCollectionNew) {
                if (!salaryemployeeCollectionOld.contains(salaryemployeeCollectionNewSalaryemployee)) {
                    Employee oldIdemployeeOfSalaryemployeeCollectionNewSalaryemployee = salaryemployeeCollectionNewSalaryemployee.getIdemployee();
                    salaryemployeeCollectionNewSalaryemployee.setIdemployee(employee);
                    salaryemployeeCollectionNewSalaryemployee = em.merge(salaryemployeeCollectionNewSalaryemployee);
                    if (oldIdemployeeOfSalaryemployeeCollectionNewSalaryemployee != null && !oldIdemployeeOfSalaryemployeeCollectionNewSalaryemployee.equals(employee)) {
                        oldIdemployeeOfSalaryemployeeCollectionNewSalaryemployee.getSalaryemployeeCollection().remove(salaryemployeeCollectionNewSalaryemployee);
                        oldIdemployeeOfSalaryemployeeCollectionNewSalaryemployee = em.merge(oldIdemployeeOfSalaryemployeeCollectionNewSalaryemployee);
                    }
                }
            }
            utx.commit();
        } catch (IllegalOrphanException | IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = employee.getIdemployee();
                if (findEmployee(id) == null) {
                    throw new NonexistentEntityException("The employee with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Employee employee;
            try {
                employee = em.getReference(Employee.class, id);
                employee.getIdemployee();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The employee with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Salaryemployee> salaryemployeeCollectionOrphanCheck = employee.getSalaryemployeeCollection();
            for (Salaryemployee salaryemployeeCollectionOrphanCheckSalaryemployee : salaryemployeeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This Employee (" + employee + ") cannot be destroyed since the Salaryemployee " + salaryemployeeCollectionOrphanCheckSalaryemployee + " in its salaryemployeeCollection field has a non-nullable idemployee field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(employee);
            utx.commit();
        } catch (IllegalOrphanException | NonexistentEntityException | IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
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

    public List<Employee> findEmployeeEntities() {
        return findEmployeeEntities(true, -1, -1);
    }

    public List<Employee> findEmployeeEntities(int maxResults, int firstResult) {
        return findEmployeeEntities(false, maxResults, firstResult);
    }

    @SuppressWarnings({"unchecked", "unchecked"})
    private List<Employee> findEmployeeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery <Object> cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Employee.class));
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

    public Employee findEmployee(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }

    @SuppressWarnings("unchecked")
    public int getEmployeeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery <Object> cq = em.getCriteriaBuilder().createQuery();
            @SuppressWarnings("unchecked")
            Root<Employee> rt = cq.from(Employee.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
