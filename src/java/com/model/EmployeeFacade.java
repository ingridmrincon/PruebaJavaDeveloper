package com.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Usuario Ingrid Rincón 
 */
@Stateless
public class EmployeeFacade extends AbstractFacade<Employee> {

    @PersistenceContext(unitName = "EmployeesTestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmployeeFacade() {
        super(Employee.class);
    }
    
}
