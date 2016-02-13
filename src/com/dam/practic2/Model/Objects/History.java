package com.dam.practic2.Model.Objects;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="history")
public class History
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idHistory")
    private int idHistory;
    @Column(name="receptionDate")
    private Date receptionDate;
    @Column(name="visitDate")
    private Date visitDate;
    @Column(name="medicalCentre")
    private String medicalCentre;

    @ManyToOne
    @JoinColumn(name="idAnalysis")
    private Analysis analysis;

    @ManyToOne
    @JoinColumn(name="idEpisode")
    private Episode episode;

    @ManyToOne
    @JoinColumn(name="idPatient")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name="idMedic")
    private Medic medic;

    @ManyToOne
    @JoinColumn(name="idPharmacotherapy")
    private Pharmacotherapy pharmacotherapy;

    public History()
    {

    }

    public History(int idHistory, Date receptionDate, Date visitDate, String medicalCentre)
    {
        this.idHistory = idHistory;
        this.receptionDate = receptionDate;
        this.visitDate = visitDate;
        this.medicalCentre = medicalCentre;
    }

    public int getIdHistory()
    {
        return idHistory;
    }

    public void setIdHistory(int idHistory)
    {
        this.idHistory = idHistory;
    }

    public Date getReceptionDate()
    {
        return receptionDate;
    }

    public void setReceptionDate(Date receptionDate)
    {
        this.receptionDate = receptionDate;
    }

    public Date getVisitDate()
    {
        return visitDate;
    }

    public void setVisitDate(Date visitDate)
    {
        this.visitDate = visitDate;
    }

    public String getMedicalCentre()
    {
        return medicalCentre;
    }

    public void setMedicalCentre(String medicalCentre)
    {
        this.medicalCentre = medicalCentre;
    }

    public Analysis getAnalysis()
    {
        return analysis;
    }

    public void setAnalysis(Analysis analysis)
    {
        this.analysis = analysis;
    }

    public Episode getEpisode()
    {
        return episode;
    }

    public void setEpisode(Episode episode)
    {
        this.episode = episode;
    }

    public Patient getPatient()
    {
        return patient;
    }

    public void setPatient(Patient patient)
    {
        this.patient = patient;
    }

    public Medic getMedic()
    {
        return medic;
    }

    public void setMedic(Medic medic)
    {
        this.medic = medic;
    }

    public Pharmacotherapy getPharmacotherapy()
    {
        return pharmacotherapy;
    }

    public void setPharmacotherapy(Pharmacotherapy pharmacotherapy)
    {
        this.pharmacotherapy = pharmacotherapy;
    }
}