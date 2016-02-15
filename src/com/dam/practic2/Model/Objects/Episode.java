package com.dam.practic2.Model.Objects;

import javax.persistence.*;
import java.util.*;

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

    @ManyToMany(cascade = {CascadeType.DETACH})
    @JoinTable(name="episodedisease", joinColumns = {@JoinColumn(name="idEpisode")}, inverseJoinColumns = {@JoinColumn(name="idDisease")})
    List<Disease> listaEnfermedades = new ArrayList<>();

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

    public List<Disease> getListaEnfermedades()
    {
        return listaEnfermedades;
    }

    public void setListaEnfermedades(List<Disease> listaEnfermedades)
    {
        this.listaEnfermedades = listaEnfermedades;
    }
}