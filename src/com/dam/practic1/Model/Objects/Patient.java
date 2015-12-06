package com.dam.practic1.Model.Objects;

import java.io.Serializable;
import java.util.Date;

/**
 * Esta clase contiene el objeto Patient
 */
public class Patient implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int idP;
    private String name;
    private String surname;
    private Date birthDate;
    private String address;
    private History history;
    private int idMed;

    public Patient()
    {
        this.history = new History();
        this.history.setIdPatient(this.idP);
    }

    public void setIdP(int id)
    {
        this.idP = id;
    }

    public int getIdP()
    {
        return this.idP;
    }

    public void setIdMed(int id)
    {
        this.idMed = id;
    }

    public int getIdMed()
    {
        return this.idMed;
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

    public History getHistory()
    {
        return history;
    }

    public void setHistory(History history)
    {
        this.history = history;
    }

    public String toString()
    {
        return "Name: " + this.name + " " + this.surname + ", CIAS: " + this.history.getCiasNumber();
    }
}