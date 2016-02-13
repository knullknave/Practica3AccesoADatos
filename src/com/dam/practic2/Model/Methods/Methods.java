package com.dam.practic2.Model.Methods;

import com.dam.practic2.Controller.Controller;
import com.dam.practic2.Model.Objects.Episode;
import com.dam.practic2.Model.Objects.History;
import com.dam.practic2.Model.Objects.Medic;
import com.dam.practic2.Model.Objects.Patient;
import com.dam.practic2.View.JConnection;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Date;

/**
 * @author Daniel Cano Mainar
 * @version 1.0 10/11/2015
 *
 * Esta clase contiene todos los métodos que se emplean en la clases Controller
 * Implementa un diseño MVC (modelo,vista,controlador)
 * Consta a su vez de varios atributos : medicList (este ArrayList almacenara los datos de los médicos, pacientes
 * y sus historias a lo largo de la ejecución), controller(le permite acceder a la clase methods a la clase Controller)
 *
 */
public class Methods
{
    private Controller controller;
    public int medicConnected;
    private SessionFactory sessionFactory;
    private Session session;

    /**
     * @param c Objeto del tipo Controller donde se procesan todas las peticiones de la ventana
     *
     * Este es el constructor de la clase Methods, donde compruebo la existencia de el fichero de configuracion y
     * de datos para cargar los datos al ArrayList o simplemente instanciarlo.
     *
     * A su vez añado el controlador, y almaceno en position la position que me han pasado (esta posicion es la
     * posicion que ocupa el medico en el vector en el momento de logearse).
     */
    public Methods(Controller c)
    {
        this.controller = c;
        createSession();
    }

    public void createSession()
    {
        Configuration configuration = new Configuration();
        configuration.configure();
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(
                configuration.getProperties()).buildServiceRegistry();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public void conect()
    {
        session = sessionFactory.openSession();
    }

    public ArrayList<Medic> SelectAllMedic()
    {
        conect();
        Query query = session.createQuery("FROM Medic");
        ArrayList<Medic> listaMedicos= (ArrayList<Medic>) query.list();

        return listaMedicos;

    }

    public Medic selectMedic(int idM)
    {
        Query query = session.createQuery("FROM Medic WHERE id=:idM");
        query.setParameter("idM", idM);

        Medic medic = (Medic) query.uniqueResult();

        return medic;
    }

    public Patient selectPatient(int idP)
    {
        Query query = session.createQuery("FROM Patient WHERE cias=:idP");
        query.setParameter("idP", idP);
        Patient p = (Patient) query.uniqueResult();

        return p;
    }

    public  ArrayList<Episode> selectAllEpisodes(int idP)
    {
        Query query = session.createQuery("FROM History h WHERE h.patient.cias=:idP");
        query.setParameter("idP", idP);
        ArrayList<History> listaHistorias = (ArrayList<History>) query.list();
        ArrayList<Episode> listaEpisodios = new ArrayList<>();

        for(int i=0;i<listaHistorias.size(); i++)
        {
            if(listaHistorias.get(i).getEpisode() != null)
            {
                query = session.createQuery("FROM Episode WHERE id=:id");
                query.setParameter("id", listaHistorias.get(i).getEpisode().getId());
                listaEpisodios.add((Episode) query.uniqueResult());
            }
        }

        return listaEpisodios;
    }

    public boolean existsMedic(String u, String p)
    {
        Query query = session.createQuery("SELECT COUNT(*) FROM Medic WHERE userName=:u AND userPasword=:p");
        query.setParameter("u", u);
        query.setParameter("p", p);

        int id = ((Long) query.uniqueResult()).intValue();
        if(id == 0)
            return false;
        else
            return true;
    }

    public boolean existsPatient(String name, String surname)
    {
        Query query = session.createQuery("SELECT COUNT(*) FROM Patient WHERE name=:u AND surname=:p");
        query.setParameter("u", name);
        query.setParameter("p", surname);

        int id = ((Long) query.uniqueResult()).intValue();
        if(id == 0)
            return false;
        else
            return true;
    }

    public boolean existsMedic2(String u, String p, int idM)
    {
        Query query = session.createQuery("SELECT COUNT(*) FROM Medic WHERE userName=:u AND userPasword=:p AND id=:id");
        query.setParameter("u", u);
        query.setParameter("p", p);
        query.setParameter("id", idM);

        int id = ((Long) query.uniqueResult()).intValue();
        if(id == 0)
            return false;
        else
            return true;
    }

    public boolean existsMedic3(int idM)
    {
        Query query = session.createQuery("SELECT COUNT(*) FROM Medic WHERE id=:id");
        query.setParameter("id", idM);

        int id = ((Long) query.uniqueResult()).intValue();
        if(id == 0)
            return false;
        else
            return true;
    }

    public void updateMedic(String name, String surname, String adress, String medCentre, String email, String medSpeciality, String telephone, java.sql.Date birth, int idM)
    {
        Query query = session.createQuery("FROM Medic WHERE id=:idM");
        query.setParameter("idM", idM);
        Medic m2 = (Medic) query.uniqueResult();
        Medic m = new Medic(idM, name, surname, adress, medCentre, email, medSpeciality, telephone, birth);
        m.setUserName(m2.getUserName());
        m.setUserPasword(m2.getUserPasword());
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(m);
        session.getTransaction().commit();
        //session.close();
    }

    public void deleteMedic(int idM)
    {
        Query query = session.createQuery("FROM History h WHERE h.medic.id=:idM");
        query.setParameter("idM", idM);
        History h = (History) query.uniqueResult();
        Episode episode = null;
        Patient p = null;
        if(h != null)
        {
            query = session.createQuery("FROM Patient WHERE id=:idM");
            query.setParameter("idM", h.getPatient().getCias());
            p = (Patient) query.uniqueResult();
            if(h.getEpisode() != null)
            {
                query = session.createQuery("FROM Episode WHERE id=:idM");
                query.setParameter("idM", h.getEpisode().getId());
                episode = (Episode) query.uniqueResult();
            }
        }

        query = session.createQuery("FROM Medic WHERE id=:idM");
        query.setParameter("idM", idM);
        Medic m = (Medic) query.uniqueResult();

        session = sessionFactory.openSession();
        session.beginTransaction();
        if(episode != null)
            session.delete(episode);
        if(p != null)
            session.delete(p);
        if(h != null)
            session.delete(h);
        session.delete(m);
        session.getTransaction().commit();
        //session.close();
    }

    public void insertMedic(String uName, String uPass, String name, String surname, String adress, String medCentre, String email, String medSpeciality, String telephone, java.sql.Date birth)
    {
        Medic m = new Medic(uName, uPass, name, surname, adress, medCentre, email, medSpeciality, telephone, birth);

        session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(m);
        session.getTransaction().commit();
        //session.close();
    }

    public ArrayList<Patient> selectAllPatient()
    {
        Query query = session.createQuery("FROM History h WHERE h.medic.id=:medic GROUP BY h.patient.cias");
        query.setParameter("medic", medicConnected);
        ArrayList<History> listaHistorias = (ArrayList<History>) query.list();

        ArrayList<Patient> listaFiltradaPacientes = new ArrayList<>();

        for(int i=0; i<listaHistorias.size(); i++)
        {
            query = session.createQuery("FROM Patient WHERE cias=:cias");
            query.setParameter("cias", listaHistorias.get(i).getPatient().getCias());
            listaFiltradaPacientes.add((Patient) query.uniqueResult());
        }

        return listaFiltradaPacientes;
    }

    public void insertPatient(String name, String surname, java.sql.Date birth, String adress)
    {
        Patient p = new Patient(name, surname, birth, adress);

        session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(p);


        Query query = session.createQuery("FROM Patient WHERE name=:name AND surname=:surname");
        query.setParameter("name", name);
        query.setParameter("surname", surname);

        Patient p2 = (Patient) query.uniqueResult();

        query = session.createQuery("FROM Medic WHERE id=:idMedic");
        query.setParameter("idMedic", this.medicConnected);
        Medic m = (Medic) query.uniqueResult();

        History h = new History();
        h.setPatient(p2);
        h.setMedic(m);

        session.save(p);
        session.save(h);

        session.getTransaction().commit();
        //session.close();
    }

    public int getCollegiateNumber(String user, String pass)
    {
        Query query = session.createQuery("FROM Medic WHERE userName=:userName AND userPasword=:userPasword");
        query.setParameter("userName", user);
        query.setParameter("userPasword", pass);
        return ((Medic)query.uniqueResult()).getId();
    }

    public void updatePatient(int idP, String name, String surname, Date birth, String adress)
    {
        Patient p = new Patient(idP, name, surname, birth, adress);
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(p);
        session.getTransaction().commit();
        //session.close();
    }

    public ArrayList<Object[]> listaDefinitva(String search)
    {
        ArrayList<Object[]> listado = new ArrayList<>();

        Query query = session.createQuery("FROM Medic");
        ArrayList<Medic> listaMedicos = (ArrayList<Medic>) query.list();
        query = session.createQuery("FROM Patient");
        ArrayList<Patient> listaPacientes = (ArrayList<Patient>) query.list();

        for(int i=0; i<listaMedicos.size(); i++)
        {
            if(listaMedicos.get(i).getName().equals(search) || listaMedicos.get(i).getSurname().equals(search))
            {
                Object[] objects = new Object[2];
                objects[0] = listaMedicos.get(i).getName();
                objects[1] = listaMedicos.get(i).getSurname();
                listado.add(objects);
            }
        }

        for(int i=0; i<listaPacientes.size(); i++)
        {
            if(listaPacientes.get(i).getName().equals(search) || listaPacientes.get(i).getSurname().equals(search))
            {
                Object[] objects = new Object[2];
                objects[0] = listaPacientes.get(i).getName();
                objects[1] = listaPacientes.get(i).getSurname();
                listado.add(objects);
            }
        }

        return listado;
    }

    public ArrayList<Object[]> listaDefinitva()
    {
        ArrayList<Object[]> listado = new ArrayList<>();

        Query query = session.createQuery("FROM Medic");
        ArrayList<Medic> listaMedicos = (ArrayList<Medic>) query.list();
        query = session.createQuery("FROM Patient");
        ArrayList<Patient> listaPacientes = (ArrayList<Patient>) query.list();

        for(int i=0; i<listaMedicos.size(); i++)
        {

            Object[] objects = new Object[2];
            objects[0] = listaMedicos.get(i).getName();
            objects[1] = listaMedicos.get(i).getSurname();
            listado.add(objects);

        }

        for(int i=0; i<listaPacientes.size(); i++)
        {

             Object[] objects = new Object[2];
             objects[0] = listaPacientes.get(i).getName();
             objects[1] = listaPacientes.get(i).getSurname();
             listado.add(objects);

        }

        return listado;
    }

    public ArrayList<Medic> listarMedicos(String search)
    {
        Query query = session.createQuery("FROM Medic WHERE name=:search OR surname=:search OR id=:search OR medicalSpeciality=:search");
        ArrayList<Medic> lista = (ArrayList<Medic>) query.list();

        return lista;
    }

    public ArrayList<Patient> listaPacientes(String search)
    {
        Query query = session.createQuery("FROM Patient WHERE name=:search OR surname=:search OR cias=:search OR adress=:search");
        query.setParameter("search", search);
        ArrayList<Patient> lista = (ArrayList<Patient>) query.list();

        return lista;
    }

    public void deletePatient(int idP)
    {
        Query query = session.createQuery("FROM Patient WHERE cias=:cias");
        query.setParameter("cias", idP);
        Patient p = (Patient) query.uniqueResult();

        query = session.createQuery("FROM History h WHERE h.patient.cias=:cias");
        query.setParameter("cias", idP);
        History h = (History) query.uniqueResult();

        query = session.createQuery("FROM Episode WHERE id=:id");
        query.setParameter("id", h.getEpisode());
        Episode e = (Episode) query.uniqueResult();

        session = sessionFactory.openSession();
        session.beginTransaction();
        if(e != null)
            session.delete(e);
        session.delete(h);
        session.delete(p);
        session.getTransaction().commit();
        //session.close();
    }

    public void insertEpisode(String d, java.sql.Date sD, java.sql.Date eD, String e, int idP)
    {
        Episode e1 = new Episode(d, sD, eD, e);
        session.beginTransaction();
        session.save(e1);
        session.getTransaction().commit();

        Query query = session.createQuery("FROM Episode WHERE descript=:d AND evolution=:e");
        query.setParameter("d", d);
        query.setParameter("e", e);
        Episode ep = (Episode) query.uniqueResult();
        System.out.println("EPISODIO: " + ep.getId() + " ," + ep.getDescription());

        query = session.createQuery("FROM Patient WHERE cias=:id");
        query.setParameter("id", idP);
        Patient p = (Patient) query.uniqueResult();

        query = session.createQuery("FROM Medic WHERE id=:id");
        query.setParameter("id", medicConnected);
        Medic m = (Medic) query.uniqueResult();

        History h = new History();
        h.setEpisode(ep);
        h.setMedic(m);
        h.setPatient(p);


        session.beginTransaction();
        session.save(h);
        session.getTransaction().commit();
    }

    public void deleteEpisode(int idE)
    {
        Query query = session.createQuery("FROM History h WHERE h.episode.id=:id");
        query.setParameter("id", idE);
        History h = (History) query.uniqueResult();

        query = session.createQuery("FROM Episode WHERE id=:idE");
        query.setParameter("idE", idE);
        Episode e = (Episode) query.uniqueResult();

        session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(e);
        session.delete(h);
        session.getTransaction().commit();
    }

    public Episode selectEpisode(int idE)
    {
        Query query =  session.createQuery("FROM Episode WHERE id=:idE");
        query.setParameter("idE", idE);
        return (Episode) query.uniqueResult();
    }

    public void updateEpisode(int idE, String desc, Date sD, Date eD, String evol)
    {
        Episode e = new Episode(idE, desc, sD, eD, evol);
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(e);
        session.getTransaction().commit();
        //session.close();
    }
}