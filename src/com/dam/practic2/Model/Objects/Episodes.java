package com.dam.practic2.Model.Objects;

import java.io.Serializable;
import java.util.Date;

/**
 * Esta clase contiene el objeto Episodes
 */
public class Episodes implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int idE;
    private String description;
    private String observation;
    private String evolution;
    private Date startDate;
    private Date endDate;
    private int numberRepetitions;
    private boolean causeOffWork;
    private String disease;
    private int idPatient;

    public Episodes()
    {

    }

    public void setIdPatient(int id)
    {
        this.idPatient = id;
    }

    public int getIdPatient()
    {
        return this.idPatient;
    }

    public void setIdE(int id)
    {
        this.idE = id;
    }

    public int getIdE()
    {
        return this.idE;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getObservation()
    {
        return observation;
    }

    public void setObservation(String observation)
    {
        this.observation = observation;
    }

    public String getEvolution()
    {
        return evolution;
    }

    public void setEvolution(String evolution)
    {
        this.evolution = evolution;
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

    public int getNumberRepetitions()
    {
        return numberRepetitions;
    }

    public void setNumberRepetitions(int numberRepetitions)
    {
        this.numberRepetitions = numberRepetitions;
    }

    public boolean isCauseOffWork()
    {
        return causeOffWork;
    }

    public void setCauseOffWork(boolean causeOffWork)
    {
        this.causeOffWork = causeOffWork;
    }

    public String getDiseases()
    {
        return disease;
    }

    public void setDiseases(String diseases)
    {
        this.disease = diseases;
    }

    public String toString()
    {
        return this.observation + ", " + this.startDate + ", " + this.endDate + ", " + this.isCauseOffWork();
    }
}
