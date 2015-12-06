package com.dam.practic1.Model.Objects;

import java.io.Serializable;
import java.util.Date;

/**
 * Esta clase contiene el objeto Radiography
 */
public class Radiography implements Serializable
{
    private static final long serialVersionUID = 1L;
    public int idR;
    private Date reportDate;
    private Date receptionDate;
    private Date radiographyDate;
    private String study;
    private String inform;
    private String controlMonitored;
    private String performance; //prestaciones
    private int idPatient;

    public Radiography()
    {

    }

    public void setIdR(int id)
    {
        this.idR = id;
    }

    public int getIdR()
    {
        return this.idR;
    }

    public void setIdPatient(int id)
    {
        this.idPatient = id;
    }

    public int getIdPatient()
    {
        return this.idPatient;
    }

    public Date getReportDate()
    {
        return reportDate;
    }

    public void setReportDate(Date reportDate)
    {
        this.reportDate = reportDate;
    }

    public Date getReceptionDate()
    {
        return receptionDate;
    }

    public void setReceptionDate(Date receptionDate)
    {
        this.receptionDate = receptionDate;
    }

    public Date getRadiographyDate()
    {
        return radiographyDate;
    }

    public void setRadiographyDate(Date radiographyDate)
    {
        this.radiographyDate = radiographyDate;
    }

    public String getStudy()
    {
        return study;
    }

    public void setStudy(String study)
    {
        this.study = study;
    }

    public String getInform()
    {
        return inform;
    }

    public void setInform(String inform)
    {
        this.inform = inform;
    }

    public String getControlMonitored()
    {
        return controlMonitored;
    }

    public void setControlMonitored(String controlMonitored)
    {
        this.controlMonitored = controlMonitored;
    }

    public String getPerformance()
    {
        return performance;
    }

    public void setPerformance(String performance)
    {
        this.performance = performance;
    }

    public String toString()
    {
        return this.performance + ", " + this.radiographyDate + ", " + this.getReceptionDate();
    }
}