package com.dam.practic2.Model.Objects;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="disease")
public class Disease
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="descr")
    private String descr;
    @Column(name="evolution")
    private String evolution;
    @Column(name="treatment")
    private String treatment;
    @Column(name="prevention")
    private String prevention;
    @Column(name="diseaseType")
    private String diseaseType;
    @Column(name="pathogenesis")
    private String pathogenesis;

    @ManyToMany(cascade = {CascadeType.DETACH}, mappedBy = "listaEnfermedades")
    List<Episode> listaEpisodios = new ArrayList<>();

    public Disease(String name, String descr, String treatment)
    {
        this.name = name;
        this.descr = descr;
        this.treatment = treatment;
    }

    public Disease(int id, String name, String descr, String treatment)
    {
        this.id = id;
        this.name = name;
        this.descr = descr;
        this.treatment = treatment;
    }

    public Disease()
    {

    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescr()
    {
        return descr;
    }

    public void setDescr(String descr)
    {
        this.descr = descr;
    }

    public String getEvolution()
    {
        return evolution;
    }

    public void setEvolution(String evolution)
    {
        this.evolution = evolution;
    }

    public String getTreatment()
    {
        return treatment;
    }

    public void setTreatment(String treatment)
    {
        this.treatment = treatment;
    }

    public String getPrevention()
    {
        return prevention;
    }

    public void setPrevention(String prevention)
    {
        this.prevention = prevention;
    }

    public String getDiseaseType()
    {
        return diseaseType;
    }

    public void setDiseaseType(String diseaseType)
    {
        this.diseaseType = diseaseType;
    }

    public String getPathogenesis()
    {
        return pathogenesis;
    }

    public void setPathogenesis(String pathogenesis)
    {
        this.pathogenesis = pathogenesis;
    }

    public List<Episode> getListaEpisodios()
    {
        return listaEpisodios;
    }

    public void setListaEpisodios(List<Episode> listaEpisodios)
    {
        this.listaEpisodios = listaEpisodios;
    }
}