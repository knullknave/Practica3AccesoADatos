package com.dam.practic2.Model.Objects;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Pharmacotherapy")
public class Pharmacotherapy
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="descript")
    private String descript;
    @Column(name="dosage")
    private String dosage;
    @Column(name="startDate")
    private Date startDate;
    @Column(name="endDate")
    private Date endDate;
    @Column(name="initialWeight")
    private float initialWeight;
    @Column(name="finalWeight")
    private float finalWeight;

    public Pharmacotherapy()
    {

    }

    public Pharmacotherapy(int id, String descript, String dosage, Date startDate, Date endDate, float initialWeight, float finalWeight)
    {
        this.id = id;
        this.descript = descript;
        this.dosage = dosage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.initialWeight = initialWeight;
        this.finalWeight = finalWeight;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getDescript()
    {
        return descript;
    }

    public void setDescript(String descript)
    {
        this.descript = descript;
    }

    public String getDosage()
    {
        return dosage;
    }

    public void setDosage(String dosage)
    {
        this.dosage = dosage;
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
}