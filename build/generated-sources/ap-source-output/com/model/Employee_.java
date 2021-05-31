package com.model;

import com.model.Salaryemployee;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-05-31T14:28:38")
@StaticMetamodel(Employee.class)
public class Employee_ { 

    public static volatile SingularAttribute<Employee, String> profession;
    public static volatile SingularAttribute<Employee, String> identification;
    public static volatile SingularAttribute<Employee, Date> birthdate;
    public static volatile SingularAttribute<Employee, Long> idemployee;
    public static volatile SingularAttribute<Employee, String> name;
    public static volatile SingularAttribute<Employee, String> identificationtype;
    public static volatile SingularAttribute<Employee, Long> phonenumber;
    public static volatile CollectionAttribute<Employee, Salaryemployee> salaryemployeeCollection;
    public static volatile SingularAttribute<Employee, String> lastname;

}