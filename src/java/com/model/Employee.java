package com.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Usuario Ingrid Rincón 
 */
@Entity
@Table(name = "employee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e")
    , @NamedQuery(name = "Employee.findByIdemployee", query = "SELECT e FROM Employee e WHERE e.idemployee = :idemployee")
    , @NamedQuery(name = "Employee.findByName", query = "SELECT e FROM Employee e WHERE e.name = :name")
    , @NamedQuery(name = "Employee.findByLastname", query = "SELECT e FROM Employee e WHERE e.lastname = :lastname")
    , @NamedQuery(name = "Employee.findByIdentificationtype", query = "SELECT e FROM Employee e WHERE e.identificationtype = :identificationtype")
    , @NamedQuery(name = "Employee.findByIdentification", query = "SELECT e FROM Employee e WHERE e.identification = :identification")
    , @NamedQuery(name = "Employee.findByBirthdate", query = "SELECT e FROM Employee e WHERE e.birthdate = :birthdate")
    , @NamedQuery(name = "Employee.findByPhonenumber", query = "SELECT e FROM Employee e WHERE e.phonenumber = :phonenumber")
    , @NamedQuery(name = "Employee.findByProfession", query = "SELECT e FROM Employee e WHERE e.profession = :profession")})
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idemployee")
    private Long idemployee;
    @Size(max = 50)
    @Column(name = "name")
    private String name;
    @Size(max = 50)
    @Column(name = "lastname")
    private String lastname;
    @Size(max = 50)
    @Column(name = "identificationtype")
    private String identificationtype;
    @Size(max = 50)
    @Column(name = "identification")
    private String identification;
    @Column(name = "birthdate")
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @Column(name = "phonenumber")
    private long phonenumber;
    @Size(max = 100)
    @Column(name = "profession")
    private String profession;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idemployee")
    private Collection<Salaryemployee> salaryemployeeCollection;

    public Employee() {
    }

    public Employee(Long idemployee) {
        this.idemployee = idemployee;
    }

    public Long getIdemployee() {
        return idemployee;
    }

    public void setIdemployee(Long idemployee) {
        this.idemployee = idemployee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getIdentificationtype() {
        return identificationtype;
    }

    public void setIdentificationtype(String identificationtype) {
        this.identificationtype = identificationtype;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Long getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(Long phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @XmlTransient
    public Collection<Salaryemployee> getSalaryemployeeCollection() {
        return Collections.unmodifiableCollection(salaryemployeeCollection);
    }

    public void setSalaryemployeeCollection(Collection<Salaryemployee> salaryemployeeCollection) {
        this.salaryemployeeCollection = salaryemployeeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idemployee != null ? idemployee.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        return !((this.idemployee == null && other.idemployee != null) || (this.idemployee != null && !this.idemployee.equals(other.idemployee)));
    }

    @Override
    public String toString() {
        return "com.model.Employee[ idemployee=" + idemployee + " ]";
    }
    
}
