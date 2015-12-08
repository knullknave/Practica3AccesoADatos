package com.dam.practic2.Model.Objects;

import java.io.Serializable;
import java.util.Date;

/**
 * Esta clase contiene el objeto Pharmacotherapy
 */
public class Pharmacotherapy implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int idPh;
    private Date startDate;
    private Date endDate;
    private String therapyType;
    private String dosage; // posologia
    private String description;
    private float initialWeight;
    private float finalWeight;
    private String medicines;
    private int idPatient;

    public Pharmacotherapy()
    {

    }

    public void setIdPh(int id)
    {
        this.idPh = id;
    }

    public int getIdPh()
    {
        return this.idPh;
    }

    public void setIdPatient(int id)
    {
        this.idPatient = id;
    }

    public int getIdPatient()
    {
        return this.idPatient;
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

    public String getTherapyType()
    {
        return therapyType;
    }

    public void setTherapyType(String therapyType)
    {
        this.therapyType = therapyType;
    }

    public String getDosage()
    {
        return dosage;
    }

    public void setDosage(String dosage)
    {
        this.dosage = dosage;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public float getInitialWeight()
    {
        return initialWeight;
    }

    public void setInitialWeight(float initialWeight)
    {
        this.initialWeight = initialWeight;
    }

    public float getFinalWeight()
    {
        return finalWeight;
    }

    public void setFinalWeight(float finalWeight)
    {
        this.finalWeight = finalWeight;
    }

    public String getMedicines()
    {
        return medicines;
    }

    public void setMedicines(String medicines)
    {
        this.medicines = medicines;
    }

    public String toString()
    {
        return this.therapyType + ", " + this.dosage + ", " + this.startDate + ", " + this.endDate;
    }
}