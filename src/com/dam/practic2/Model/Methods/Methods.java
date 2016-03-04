package com.dam.practic2.Model.Methods;

import com.dam.practic2.Controller.Controller;
import com.dam.practic2.Model.Objects.*;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.*;
import java.sql.Date;

import static com.dam.practic2.Model.Methods.Constants.*;
//TODO FALLA ELIMINAR
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
    private MongoClient mongoClient;
    private MongoDatabase db;
    private Util u;

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
        u = new Util();
        connect();
    }

    public void connect()
    {
        mongoClient = new MongoClient();
        db = mongoClient.getDatabase(DATABASE);
    }

    public void disconnect()
    {
        mongoClient.close();
    }

    public List<Medic> SelectAllMedic()
    {
        FindIterable<Document> findIterable = db.getCollection(Medic.COLECCION).find();

        List<Medic> medicos = new ArrayList<Medic>();
        Medic m = null;
        Iterator<Document> iter = findIterable.iterator();

        while(iter.hasNext())
        {
            Document document = iter.next();
            m = new Medic();
            m.setId(Integer.parseInt(document.getString("codigo")));
            m.setUserName(document.getString("user"));
            m.setUserPasword(document.getString("pass"));
            m.setName(document.getString("name"));
            m.setSurname(document.getString("surname"));
            m.setAddress(document.getString("address"));
            m.setMedicalCentre(document.getString("centre"));
            m.setEmail(document.getString("email"));
            m.setMedicalSpeciality(document.getString("spec"));
            m.setTelephone(document.getString("phone"));
            m.setBirthDate(u.parseFecha(document.getString("birth")));

            medicos.add(m);
        }

        return medicos;

    }

    public Medic selectMedic(int idM)
    {
        FindIterable<Document> findIterable = db.getCollection(Medic.COLECCION).find(new Document("codigo", String.valueOf(idM)));
        Document document = findIterable.first();

        Medic m = new Medic();

        m.setId(Integer.parseInt(document.getString("codigo")));
        m.setUserName(document.getString("user"));
        m.setUserPasword(document.getString("pass"));
        m.setName(document.getString("name"));
        m.setSurname(document.getString("surname"));
        m.setAddress(document.getString("address"));
        m.setMedicalCentre(document.getString("centre"));
        m.setEmail(document.getString("email"));
        m.setMedicalSpeciality(document.getString("spec"));
        m.setTelephone(document.getString("phone"));
        m.setBirthDate(u.parseFecha(document.getString("birth")));


        return m;
    }

    public Patient selectPatient(int idP)
    {
        FindIterable<Document> findIterable = db.getCollection(Patient.COLECCION).find(new Document("codigo", String.valueOf(idP)));
        Document document = findIterable.first();

        Patient p = new Patient();

        p.setCias(Integer.parseInt(document.getString("codigo")));
        p.setName(document.getString("name"));
        p.setSurname(document.getString("surname"));
        p.setAddress(document.getString("address"));
        p.setBirthDate(u.parseFecha(document.getString("birth")));
        p.setIdMedic(Integer.parseInt(document.getString("idMedic")));

        return p;
    }

    public List<Patient> selectAllPatient(int idM)
    {
        FindIterable<Document> findIterable = db.getCollection(Patient.COLECCION).find(new Document("idMedic", String.valueOf(idM)));
        Iterator<Document> iter = findIterable.iterator();

        List<Patient> listaPacientes = new ArrayList<>();
        Patient p = null;

        while(iter.hasNext())
        {
            Document document = iter.next();
            p = new Patient();
            p.setCias(Integer.parseInt(document.getString("codigo")));
            p.setName(document.getString("name"));
            p.setSurname(document.getString("surname"));
            p.setAddress(document.getString("address"));
            p.setBirthDate(u.parseFecha(document.getString("birth")));
            p.setIdMedic(Integer.parseInt(document.getString("idMedic")));

            listaPacientes.add(p);
        }
        return listaPacientes;
    }

    public  List<Episode> selectAllEpisodes(int idP)
    {
        FindIterable<Document> findIterable = db.getCollection(Episode.COLECCION).find(new Document("idPatient", String.valueOf(idP)));
        Iterator<Document> iter = findIterable.iterator();

        List<Episode> listaEpisodios = new ArrayList<>();
        Episode e = null;

        while (iter.hasNext())
        {
            Document document = iter.next();
            e = new Episode();
            e.setId(Integer.parseInt(document.getString("codigo")));
            e.setDescript(document.getString("description"));
            e.setStartDate(u.parseFecha(document.getString("start")));
            e.setEndDate(u.parseFecha(document.getString("end")));
            e.setEvolution(document.getString("evolution"));
            e.setIdPatient(Integer.parseInt(document.getString("idPatient")));

            listaEpisodios.add(e);
        }
        return listaEpisodios;
    }

    public boolean existsMedic(String u, String p)
    {
        Document document = new Document("$or", Arrays.asList(
                new Document("user", u),
                new Document("pass", p)));

        FindIterable<Document> findIterable = db.getCollection(Medic.COLECCION).find(document);

        Iterator<Document> iter = findIterable.iterator();
        int i = 0;
        while (iter.hasNext())
        {
            iter.next();
            i++;
        }

        if(i == 0)
            return false;
        else
            return true;
    }

    public boolean existsPatient(String name, String surname)
    {
        Document document = new Document("$or", Arrays.asList(
                new Document("name", name),
                new Document("surname", surname)));

        FindIterable<Document> findIterable = db.getCollection(Patient.COLECCION).find(document);

        Iterator<Document> iter = findIterable.iterator();
        int i = 0;
        while (iter.hasNext())
        {
            iter.next();
            i++;
        }

        if(i == 0)
            return false;
        else
            return true;
    }

    public boolean existsMedic2(String u, String p, int idM)
    {
        Document document = new Document("$or", Arrays.asList(
                new Document("user", u),
                new Document("pass", p),
                new Document("codigo", idM)));

        FindIterable<Document> findIterable = db.getCollection(Medic.COLECCION).find(document);

        Iterator<Document> iter = findIterable.iterator();
        int i = 0;
        while (iter.hasNext())
        {
            iter.next();
            i++;
        }

        if(i == 0)
            return false;
        else
            return true;
    }

    public boolean existsMedic3(int idM)
    {
        FindIterable<Document> findIterable = db.getCollection(Medic.COLECCION).find(new Document("codigo", String.valueOf(idM)));

        Iterator<Document> iter = findIterable.iterator();
        int i = 0;
        while (iter.hasNext())
        {
            iter.next();
            i++;
        }

        if(i == 0)
            return false;
        else
            return true;
    }

    public int getCollegiateNumber(String user, String pass)
    {
        Document document = new Document("$or", Arrays.asList(
                new Document("user", user),
                new Document("pass", pass)));

        FindIterable<Document> findIterable = db.getCollection(Medic.COLECCION).find(document);

        Document doc = findIterable.first();

        return Integer.parseInt(doc.getString("codigo"));
    }

    public List<Medic> listarMedicos(String search)
    {
        List<Medic> listaMedicos = SelectAllMedic();
        List<Medic> listaFiltrada = new ArrayList<>();

        for(int i=0; i<listaMedicos.size(); i++)
        {
            Medic m = listaMedicos.get(i);
            if(m.getName().contains(search) || m.getSurname().contains(search) || m.getAddress().contains(search) || m.getMedicalCentre().contains(search) || m.getMedicalSpeciality().contains(search) || m.getEmail().contains(search))
            {
                listaFiltrada.add(m);
            }
        }
        return listaFiltrada;
    }

    public List<Patient> listaPacientes(String search)
    {
        List<Patient> listaPacientes = selectAllPatient(medicConnected);
        List<Patient> listaFiltrada = new ArrayList<>();
        for(int i=0; i<listaPacientes.size(); i++)
        {
            Patient p = listaPacientes.get(i);
            if(p.getName().contains(search) || p.getSurname().contains(search) || p.getAddress().contains(search))
            {
                listaFiltrada.add(p);
            }
        }
        return listaFiltrada;
    }

    public Episode selectEpisode(int idE)
    {
        FindIterable<Document> findIterable = db.getCollection(Episode.COLECCION).find(new Document("codigo", String.valueOf(idE)));
        Document document = findIterable.first();

        Episode e = new Episode();

        e.setId(Integer.parseInt(document.getString("codigo")));
        e.setDescript(document.getString("description"));
        e.setStartDate(u.parseFecha(document.getString("start")));
        e.setEndDate(u.parseFecha(document.getString("end")));
        e.setEvolution(document.getString("evolution"));
        e.setIdPatient(Integer.parseInt(document.getString("idPatient")));

        return e;
    }

    public void insertMedic(String uName, String uPass, String name, String surname, String adress, String medCentre, String email, String medSpeciality, String telephone, java.sql.Date birth)
    {
        Medic m = new Medic(uName, uPass, name, surname, adress, medCentre, email, medSpeciality, telephone, birth);
        int lastId = getLastIdMedic();
        Document document = new Document()
                .append("codigo", String.valueOf(lastId))
                .append("user", m.getUserName())
                .append("pass", m.getUserPasword())
                .append("name", m.getName())
                .append("surname", m.getSurname())
                .append("address", m.getAddress())
                .append("centre", m.getMedicalCentre())
                .append("email", m.getEmail())
                .append("spec", m.getMedicalSpeciality())
                .append("phone", m.getTelephone())
                .append("birth", u.formatFecha(m.getBirthDate()));
        db.getCollection(Medic.COLECCION).insertOne(document);
    }

    public void updateMedic(String name, String surname, String adress, String medCentre, String email, String medSpeciality, String telephone, java.sql.Date birth, int idM)
    {
        db.getCollection(Medic.COLECCION).replaceOne(new Document("codigo", String.valueOf(idM)),
                new Document()
                        .append("codigo", String.valueOf(idM))
                        .append("name", name)
                        .append("surname", surname)
                        .append("address", adress)
                        .append("centre", medCentre)
                        .append("email", email)
                        .append("spec", medSpeciality)
                        .append("phone", telephone)
                        .append("birth", u.formatFecha(birth)));
    }

    public void deleteMedic(int idM)
    {
        List<Patient> listaPacientes = selectAllPatient(idM);

        for(int i=0; i<listaPacientes.size(); i++)
        {
            int idP = listaPacientes.get(i).getCias();
            db.getCollection(Episode.COLECCION).deleteMany(new Document("idPatient", String.valueOf(idP)));
            db.getCollection(Patient.COLECCION).deleteOne(new Document("codigo", String.valueOf(idP)));
        }

        db.getCollection(Medic.COLECCION).deleteOne(new Document("codigo", String.valueOf(idM)));
    }

    public void insertPatient(String name, String surname, java.sql.Date birth, String adress)
    {
        Patient p = new Patient(name, surname, birth, adress);
        int lastId = getLastIdPatient();
        Document document = new Document()
                .append("codigo", String.valueOf(lastId))
                .append("name", p.getName())
                .append("surname", p.getSurname())
                .append("address", p.getAddress())
                .append("birth", u.formatFecha(p.getBirthDate()))
                .append("idMedic", String.valueOf(medicConnected));
        db.getCollection(Patient.COLECCION).insertOne(document);
    }

    public void updatePatient(int idP, String name, String surname, Date birth, String adress)
    {
        Patient p = new Patient(idP, name, surname, birth, adress);

        db.getCollection(Patient.COLECCION).replaceOne(new Document("codigo", String.valueOf(idP)),
                new Document()
                        .append("codigo", String.valueOf(idP))
                        .append("name", p.getName())
                        .append("surname", p.getSurname())
                        .append("address", p.getAddress())
                        .append("birth", u.formatFecha(p.getBirthDate())));
    }

    public void deletePatient(int idP)
    {
        db.getCollection(Episode.COLECCION).deleteMany(new Document("idPatient", String.valueOf(idP)));
        db.getCollection(Patient.COLECCION).deleteOne(new Document("codigo", String.valueOf(idP)));
    }

    public void insertEpisode(String d, java.sql.Date sD, java.sql.Date eD, String e, int idP)
    {
        Episode e1 = new Episode(d, sD, eD, e);
        int lastId = getLastIdEpisode();
        Document document = new Document()
                .append("codigo", String.valueOf(lastId))
                .append("description", e1.getDescript())
                .append("start", u.formatFecha(e1.getStartDate()))
                .append("end", u.formatFecha(e1.getEndDate()))
                .append("evolution", e1.getEvolution())
                .append("idPatient", String.valueOf(idP));
        db.getCollection(Episode.COLECCION).insertOne(document);
    }

    public void updateEpisode(int idE, String desc, Date sD, Date eD, String evol)
    {
        Episode e = new Episode(idE, desc, sD, eD, evol);

        db.getCollection(Episode.COLECCION).replaceOne(new Document("codigo", String.valueOf(idE)),
                new Document()
                        .append("codigo", String.valueOf(idE))
                        .append("description", e.getDescript())
                        .append("start", u.formatFecha(e.getStartDate()))
                        .append("end", u.formatFecha(e.getEndDate()))
                        .append("evolution", e.getEvolution()));
    }

    public void deleteEpisode(int idE)
    {
        db.getCollection(Episode.COLECCION).deleteMany(new Document("codigo", String.valueOf(idE)));
    }

    public int getLastIdMedic()
    {
        long i = db.getCollection(Medic.COLECCION).count();
        if((int)(long)i > 0)
        {
            FindIterable<Document> findIterable = db.getCollection(Medic.COLECCION).find().sort(new Document("codigo", -1));
            Document doc = findIterable.first();
            return Integer.valueOf(doc.getString("codigo")) + 1;
        }
        else
        {
            return 1;
        }
    }

    public int getLastIdPatient()
    {
        long i = db.getCollection(Patient.COLECCION).count();
        if((int)(long)i > 0)
        {
            FindIterable<Document> findIterable = db.getCollection(Patient.COLECCION).find().sort(new Document("codigo", -1));
            Document doc = findIterable.first();
            return Integer.valueOf(doc.getString("codigo")) + 1;
        }
        else
        {
            return 1;
        }
    }

    public int getLastIdEpisode()
    {
        long i = db.getCollection(Episode.COLECCION).count();
        if((int)(long)i > 0)
        {
            FindIterable<Document> findIterable = db.getCollection(Episode.COLECCION).find().sort(new Document("codigo", -1));
            Document doc = findIterable.first();
            return Integer.valueOf(doc.getString("codigo")) + 1;
        }
        else
        {
            return 1;
        }
    }
}