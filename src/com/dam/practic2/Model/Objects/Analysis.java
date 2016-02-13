package com.dam.practic2.Model.Objects;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="analysis")
public class Analysis
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="analysisDate")
    private Date analysisDate;
    @Column(name="analysisType")
    private String analysisType;
    @Column(name="report")
    private String report;
    @Column(name="reportDate")
    private Date reportDate;

    public Analysis()
    {

    }

    public Analysis(int id, Date analysisDate, Date reportDate, String analysisType, String report)
    {
        this.id = id;
        this.analysisDate = analysisDate;
        this.report = report;
        this.reportDate = reportDate;
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

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
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

    public String getReport()
    {
        return report;
    }

    public void setReport(String report)
    {
        this.report = report;
    }
}