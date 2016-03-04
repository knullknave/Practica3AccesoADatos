package com.dam.practic2.Model.Objects;

import java.util.Date;

public class Patient
{
    public static String COLECCION = "Patient";

    //codigo
    private int cias;
    //name
    private String name;
    //surname
    private String surname;
    //address
    private String address;
    //birth
    private Date birthDate;
    //idMedic
    private int idMedic;

    public Patient()
    {

    }

    public Patient(int cias, String patientUser, String patientPassword, String name, String surname, String adress, char sex, Date birthDate, String telephone, String bloodType)
    {
        this.cias = cias;
        this.name = name;
        this.surname = surname;
        this.address = adress;
        this.birthDate = birthDate;
    }

    public Patient(String name, String surname, Date birthDate, String adress)
    {
        this.name = name;
        this.surname = surname;
        this.address = adress;
        this.birthDate = birthDate;
    }

    public Patient(int cias, String name, String surname, Date birthDate, String adress)
    {
        this.cias = cias;
        this.name = name;
        this.surname = surname;
        this.address = adress;
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

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public int getIdMedic()
    {
        return idMedic;
    }

    public void setIdMedic(int idMedic)
    {
        this.idMedic = idMedic;
    }

    @Override
    public String toString()
    {
        return "Patient{" +
                "cias=" + cias +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", birthDate=" + birthDate +
                ", idMedic=" + idMedic +
                '}';
    }
}