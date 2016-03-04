package com.dam.practic2.Model.Objects;

import java.util.*;

public class Episode
{
    public static String COLECCION = "Episode";

    //codigo
    private int id;
    //description
    private String descript;
    //start
    private Date startDate;
    //end
    private Date endDate;
    //evolution
    private String evolution;
    //idPatient
    private int idPatient;

    public Episode()
    {

    }

    public Episode(int id, String description, Date startDate, Date endDate, String evolution)
    {
        this.id = id;
        this.descript = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.evolution = evolution;
    }

    public Episode(String description, Date startDate, Date endDate, String evolution)
    {
        this.id = id;
        this.descript = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.evolution = evolution;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public String getEvolution()
    {
        return evolution;
    }

    public void setEvolution(String evolution)
    {
        this.evolution = evolution;
    }

    public String getDescript()
    {
        return descript;
    }

    public void setDescript(String descript)
    {
        this.descript = descript;
    }

    public int getIdPatient()
    {
        return idPatient;
    }

    public void setIdPatient(int idPatient)
    {
        this.idPatient = idPatient;
    }
}