package com.dam.practic1.Model.Objects;

import java.io.Serializable;
import java.util.Date;

/**
 * Esta clase contiene el objeto Analysis
 */
public class Analysis implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int idA;
    private String report;
    private String hospital;
    private Date receptionDate;
    private Date analysisDate;
    private String analysisType;
    private Date reportDate;
    private int idPatient;

    public Analysis()
    {

    }

    public void setIdA(int id)
    {
        this.idA = id;
    }

    public int getIdA()
    {
        return this.idA;
    }

    public void setIdPatient(int id)
    {
        this.idPatient = id;
    }

    public int getIdPatient()
    {
        return this.idPatient;
    }

    public String getReport()
    {
        return report;
    }

    public void setReport(String report)
    {
        this.report = report;
    }

    public String getHospital()
    {
        return hospital;
    }

    public void setHospital(String hospital)
    {
        this.hospital = hospital;
    }

    public Date getReceptionDate()
    {
        return receptionDate;
    }

    public void setReceptionDate(Date receptionDate)
    {
        this.receptionDate = receptionDate;
    }

    public Date getAnalysisDate()
    {
        return analysisDate;
    }

    public void setAnalysisDate(Date analysisDate)
    {
        this.analysisDate = analysisDate;
    }

    public String getAnalysisType()
    {
        return analysisType;
    }

    public void setAnalysisType(String analysisType)
    {
        this.analysisType = analysisType;
    }

    public Date getReportDate()
    {
        return reportDate;
    }

    public void setReportDate(Date reportDate)
    {
        this.reportDate = reportDate;
    }

    public String toString()
    {
        return this.hospital + ", "  + this.reportDate + ", " + this.getAnalysisDate();
    }
}
