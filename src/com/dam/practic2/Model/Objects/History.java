package com.dam.practic2.Model.Objects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Esta clase contiene el objeto History
 */
public class History implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String ciasNumber;
    private ArrayList<Episodes>episodeList;
    private ArrayList<Analysis> analysisList;
    private ArrayList<Radiography> radiographyList;
    private ArrayList<Pharmacotherapy> pharmacotherapyList;
    private int idPatient;

    public History()
    {
        this.episodeList = new ArrayList<Episodes>();
        this.analysisList = new ArrayList<Analysis>();
        this.radiographyList = new ArrayList<Radiography>();
        this.pharmacotherapyList = new ArrayList<Pharmacotherapy>();
    }

    public void setIdPatient(int id)
    {
        this.idPatient = id;
    }

    public int getIdPatient()
    {
        return this.idPatient;
    }

    public String getCiasNumber()
    {
        return ciasNumber;
    }

    public void setCiasNumber(String ciasNumber)
    {
        this.ciasNumber = ciasNumber;
    }

    public ArrayList<Episodes> getEpisodeList()
    {
        return episodeList;
    }

    public void setEpisodeList(ArrayList<Episodes> episodeList)
    {
        this.episodeList = episodeList;
    }

    public ArrayList<Analysis> getAnalysisList()
    {
        return analysisList;
    }

    public void setAnalysisList(ArrayList<Analysis> analysisList)
    {
        this.analysisList = analysisList;
    }

    public ArrayList<Radiography> getRadiographyList()
    {
        return radiographyList;
    }

    public void setRadiographyList(ArrayList<Radiography> radiographyList)
    {
        this.radiographyList = radiographyList;
    }

    public ArrayList<Pharmacotherapy> getPharmacotherapyList()
    {
        return pharmacotherapyList;
    }

    public void setPharmacotherapyList(ArrayList<Pharmacotherapy> pharmacotherapyList)
    {
        this.pharmacotherapyList = pharmacotherapyList;
    }
}
