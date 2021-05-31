package com.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario Ingrid Rincón 
 */
@Entity
@Table(name = "salaryemployee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Salaryemployee.findAll", query = "SELECT s FROM Salaryemployee s")
    , @NamedQuery(name = "Salaryemployee.findByIdsalary", query = "SELECT s FROM Salaryemployee s WHERE s.idsalary = :idsalary")
    , @NamedQuery(name = "Salaryemployee.findByPosition", query = "SELECT s FROM Salaryemployee s WHERE s.position = :position")
    , @NamedQuery(name = "Salaryemployee.findBySalary", query = "SELECT s FROM Salaryemployee s WHERE s.salary = :salary")})
public class Salaryemployee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsalary")
    private Long idsalary;
    @Size(max = 50)
    @Column(name = "position")
    private String position;
    @Column(name = "salary")
    private Integer salary;
    @JoinColumn(name = "idemployee", referencedColumnName = "idemployee")
    @ManyToOne(optional = false)
    private Employee idemployee;

    public Salaryemployee() {
    }

    public Salaryemployee(Long idsalary) {
        this.idsalary = idsalary;
    }

    public Long getIdsalary() {
        return idsalary;
    }

    public void setIdsalary(Long idsalary) {
        this.idsalary = idsalary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        salary *= 12 ;
        this.salary = salary;
    }

    public Employee getIdemployee() {
        return idemployee;
    }

    public void setIdemployee(Employee idemployee) {
        this.idemployee = idemployee;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsalary != null ? idsalary.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Salaryemployee)) {
            return false;
        }
        Salaryemployee other = (Salaryemployee) object;
        return !((this.idsalary == null && other.idsalary != null) || (this.idsalary != null && !this.idsalary.equals(other.idsalary)));
    }

    @Override
    public String toString() {
        return "com.model.Salaryemployee[ idsalary=" + idsalary + " ]";
    }
    
}
