package com.dam.practic1.Model.Objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Esta clase contiene el objeto Medic
 */
public class Medic implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int idM;
    private String name;
    private String surname;
    private String medicalSpeciality;
    private String collegiateNumber;
    private String address;
    private Date birthDate;
    private String user;
    private String password;
    private ArrayList<Patient> patientList;

    public Medic()
    {
        this.patientList = new ArrayList<Patient>();
    }

    public void setIdM(int id)
    {
        this.idM = id;
    }

    public int getIdM()
    {
        return this.idM;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getMedicalSpeciality()
    {
        return medicalSpeciality;
    }

    public void setMedicalSpeciality(String medicalSpeciality)
    {
        this.medicalSpeciality = medicalSpeciality;
    }

    public String getCollegiateNumber()
    {
        return collegiateNumber;
    }

    public void setCollegiateNumber(String collegiateNumber)
    {
        this.collegiateNumber = collegiateNumber;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public ArrayList<Patient> getPatientList()
    {
        return this.patientList;
    }

    public String toString()
    {
        return "Name: " + this.name + " " + this.surname + ", Medical Speciality: " + this.medicalSpeciality;
    }
}
