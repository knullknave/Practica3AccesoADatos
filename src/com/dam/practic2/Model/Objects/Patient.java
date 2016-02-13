package com.dam.practic2.Model.Objects;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "patient")
public class Patient
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cias")
    private int cias;
    @Column(name="patientUser")
    private String patientUser;
    @Column(name="patientPassword")
    private String patientPassword;
    @Column(name="name")
    private String name;
    @Column(name="surname")
    private String surname;
    @Column(name="sex")
    private char sex;
    @Column(name="adress")
    private String adress;
    @Column(name="birthDate")
    private Date birthDate;
    @Column(name="telephone")
    private String telephone;
    @Column(name="bloodType")
    private String bloodType;

    public Patient()
    {

    }

    public Patient(int cias, String patientUser, String patientPassword, String name, String surname, String adress, char sex, Date birthDate, String telephone, String bloodType)
    {
        this.cias = cias;
        this.patientPassword = patientPassword;
        this.patientUser =  patientUser;
        this.name = name;
        this.surname = surname;
        this.adress = adress;
        this.sex = sex;
        this.birthDate = birthDate;
        this.telephone = telephone;
        this.bloodType = bloodType;
    }

    public Patient(String name, String surname, Date birthDate, String adress)
    {
        this.name = name;
        this.surname = surname;
        this.adress = adress;
        this.birthDate = birthDate;
    }

    public Patient(int cias, String name, String surname, Date birthDate, String adress)
    {
        this.cias = cias;
        this.name = name;
        this.surname = surname;
        this.adress = adress;
        this.birthDate = birthDate;
    }

    public int getCias()
    {
        return cias;
    }

    public void setCias(int cias)
    {
        this.cias = cias;
    }

    public String getPatientUser()
    {
        return patientUser;
    }

    public void setPatientUser(String patientUser)
    {
        this.patientUser = patientUser;
    }

    public String getPatientPassword()
    {
        return patientPassword;
    }

    public void setPatientPassword(String patientPassword)
    {
        this.patientPassword = patientPassword;
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

    public char getSex()
    {
        return sex;
    }

    public void setSex(char sex)
    {
        this.sex = sex;
    }

    public String getAdress()
    {
        return adress;
    }

    public void setAdress(String adress)
    {
        this.adress = adress;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    public String getBloodType()
    {
        return bloodType;
    }

    public void setBloodType(String bloodType)
    {
        this.bloodType = bloodType;
    }
}