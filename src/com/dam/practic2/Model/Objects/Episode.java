package com.dam.practic2.Model.Objects;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="episodes")
public class Episode
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="descript")
    private String descript;
    @Column(name="startDate")
    private Date startDate;
    @Column(name="endDate")
    private Date endDate;
    @Column(name="evolution")
    private String evolution;

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

    public String getDescription()
    {
        return descript;
    }

    public void setDescription(String description)
    {
        this.descript = description;
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
}