package com.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Usuario Ingrid Rincón 
 */
@Stateless
public class SalaryemployeeFacade extends AbstractFacade<Salaryemployee> {

    @PersistenceContext(unitName = "EmployeesTestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SalaryemployeeFacade() {
        super(Salaryemployee.class);
    }
    
}
