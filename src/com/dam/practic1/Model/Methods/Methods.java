package com.dam.practic1.Model.Methods;

import com.dam.practic1.Controller.Controller;
import com.dam.practic1.Model.Objects.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import static com.dam.practic1.Model.Methods.Constants.CONFIGURE_FILE_PATH;

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
    public static ArrayList<Medic> medicList;
    private Controller controller;
    private int position;

    private int idLastM;
    private int idLastP;
    private int idLastE;
    private int idLastA;
    private int idLastR;
    private int idLastPh;

    /**
     * @param c Objeto del tipo Controller donde se procesan todas las peticiones de la ventana
     * @param position Posicion que ocupa en el vector el medico que se ha logeado
     *
     * Este es el constructor de la clase Methods, donde compruebo la existencia de el fichero de configuracion y
     * de datos para cargar los datos al ArrayList o simplemente instanciarlo.
     *
     * A su vez añado el controlador, y almaceno en position la position que me han pasado (esta posicion es la
     * posicion que ocupa el medico en el vector en el momento de logearse).
     */
    public Methods(Controller c, int position)
    {
        this.controller = c;

        File f = new File("configuration.props");
        /**
         * Comprueba que el fichero existe
         */
        if (!f.exists())
            writeConfigureFile();
        File f2 = new File(readConfigureFile());
        if (f2.exists())
        {
            readFile();
        }
        else
        {
            this.medicList = new ArrayList<Medic>();
        }
        this.idLastM = this.medicList.size();

        this.position = position;
    }

    /**
     * @return
     *
     * Esta función devuelve una cadena con un formato específico generado de manera aleatoria.
     * Sirve para rellenar los valores de ciasNumber del objeto History y collegiateNumber del objeto Medic.
     */
    public static String RandomCias()
    {
        Random n = new Random();
        String value = "";

        /**
         * Estos primeros 7 valores generados son de tipo numérico.
         */
        for (int i = 0; i < 7; i++)
        {
            int num = n.nextInt(99);
            value = value + num;
        }

        /**
         * Estos últimos 2 valores generados son de tipo carácter
         */
        for (int i = 0; i < 2; i++)
        {
            int num = 34;
            while ((num < 65) || (num > 90))
            {
                num = n.nextInt(90);
            }
            value = value + (char) num;
        }
        return value;
    }

    /**
     * @param v1
     * @param v2
     * @param v3
     * @param v4
     * @param v5
     * @param v6
     * @param v7
     * @param v8
     *
     * Este metodo permite añadir al vector medicList un médico con sus respectivos pacientes e historias
     * Además almaceno la posicion que ocupa el último medico para añadirla al id del próximo médico.
     */
    public void registerMedic(String v1, String v2, String v3, String v4, String v5, Date v6, String v7, String v8)
    {
        Medic m = new Medic();

        m.setIdM(this.idLastM);
        m.setName(v1);
        m.setSurname(v2);
        m.setMedicalSpeciality(v3);
        m.setCollegiateNumber(v4);
        m.setAddress(v5);
        m.setBirthDate(v6);
        m.setUser(v7);
        m.setPassword(v8);

        this.medicList.add(m);

        this.idLastM = this.idLastM + 1;
    }

    /**
     * @param v1
     * @param v2
     * @param v3
     * @param v4
     * @param v5
     *
     * Este método permite registrar pacientes. También guardo la ultima posicion que ocupa para añadirla al próximo
     * paciente
     */
    public void registerPatient(String v1, String v2, Date v3, String v4, int v5)
    {
        Patient p = new Patient();

        this.idLastP = this.medicList.get(v5).getPatientList().size();

        p.setIdP(this.idLastP);
        p.setName(v1);
        p.setSurname(v2);
        p.setBirthDate(v3);
        p.setAddress(v4);
        p.getHistory().setCiasNumber(RandomCias());
        p.getHistory().setIdPatient(this.idLastP);
        p.setIdMed(v5);

        this.medicList.get(v5).getPatientList().add(p);
        this.idLastP = this.idLastP + 1;
    }

    /**
     * @param v1
     * @param v2
     * @param v3
     * @param v4
     * @param v5
     * @param v6
     *
     * Este método permite modificar los datos de un paciente
     */
    public void updatePatient(String v1, String v2, Date v3, String v4, int v5, int v6)
    {
        Patient p = this.medicList.get(v5).getPatientList().get(v6);

        p.setName(v1);
        p.setSurname(v2);
        p.setBirthDate(v3);
        p.setAddress(v4);
    }

    /**
     * @param v1
     * @param v2
     * @param v3
     * @param v4
     * @param v5
     * @param v6
     * @param v7
     * @param v8
     * @param pos
     *
     * Este metodo permite actualizar los datos de un médico
     */
    public void updateMedic(String v1, String v2, String v3, String v4, String v5, Date v6, String v7, String v8, int pos)
    {
        Medic m = this.medicList.get(pos);

        m.setName(v1);
        m.setSurname(v2);
        m.setMedicalSpeciality(v3);
        m.setCollegiateNumber(v4);
        m.setAddress(v5);
        m.setBirthDate(v6);
        m.setUser(v7);
        m.setPassword(v8);
    }

    /**
     * @param v1
     * @param v2
     * @param v3
     * @param v4
     * @param v5
     * @param v6
     * @param v7
     * @param v8
     * @param v9
     * @param v10
     *
     * Este método permite añadir Episodos a un paciente
     */
    public void registerEpisode(String v1, String v2, String v3, Date v4, Date v5, int v6, String v7, String v8, int v9, int v10)
    {
        Episodes e = new Episodes();

        this.idLastE = this.medicList.get(v9).getPatientList().get(v10).getHistory().getEpisodeList().size();

        e.setIdE(this.idLastE);
        e.setDescription(v1);
        e.setObservation(v2);
        e.setEvolution(v3);
        e.setStartDate(v4);
        e.setEndDate(v5);
        e.setNumberRepetitions(v6);
        boolean b = false;
        if (v7.equals("Yes"))
            b = true;
        else
            b = false;
        e.setCauseOffWork(b);
        e.setDiseases(v8);
        e.setIdPatient(v10);

        this.medicList.get(v9).getPatientList().get(v10).getHistory().getEpisodeList().add(e);
        this.idLastE = this.idLastE + 1;
    }

    /**
     * @param value
     * @return
     *
     * Esta función permite comprobar si un valor guardado en una String es un valor numérico o no.
     * Para comprobarlo, es necesario recorrer la cadena y aplicarle la función matches("\\d*")
     * Esta función indica si lo que ha encontrado es numerico o char mediante un true o false.
     */
    public boolean checkNumber(String value)
    {
        boolean number = true;
        if (value.substring(0, 1).equalsIgnoreCase("-"))
        {
            for (int j = 1; j < value.length(); j++)
            {
                /**
                 * La función matches comprueba si el valor que le estas pasando es realmente numérico, y a su vez
                 */
                if (!value.substring(j, j + 1).matches("\\d*"))
                {
                    number = false;
                    if (value.substring(j, j + 1).equalsIgnoreCase("."))
                        number = true;
                }
            }
        }
        else
        {
            for (int j = 0; j < value.length(); j++)
            {
                if (!value.substring(j, j + 1).matches("\\d*"))
                    number = false;
                if (value.substring(j, j + 1).equalsIgnoreCase("."))
                    number = true;
            }
        }
        return number;
    }

    /**
     * @param medPos
     * @param row
     *
     * Este método permite elimiar un paciente
     */
    public void removePatient(int medPos, int row)
    {
        this.medicList.get(medPos).getPatientList().remove(row);

        for(Patient p : this.medicList.get(medPos).getPatientList())
        {
            int newId = p.getIdP();
            if(newId > 1)
                p.setIdP(newId - 1);
        }
        this.idLastP = this.medicList.get(medPos).getPatientList().size();
    }

    /**
     * @param medPos
     *
     * Este método permite elimiar un médico
     */
    public void removeMedic(int medPos)
    {
        this.medicList.remove(medPos);

        for(Medic m : this.medicList)
        {
            int newId = m.getIdM();
            if(newId > 1)
                m.setIdM(newId - 1);
        }
        this.idLastM = this.medicList.size();
    }

    /**
     * @param v1
     * @param v2
     * @param v3
     * @param v4
     * @param v5
     * @param v6
     * @param v7
     * @param v8
     * @param v9
     * @param v10
     * @param v11
     *
     * Este método permite actualizar un Episodio.
     */
    public void updateEpisode(String v1, String v2, String v3, Date v4, Date v5, int v6, String v7, String v8, int v9, int v10, int v11)
    {
        Episodes ep = this.medicList.get(v9).getPatientList().get(v10).getHistory().getEpisodeList().get(v11);

        ep.setDescription(v1);
        ep.setObservation(v2);
        ep.setEvolution(v3);
        ep.setStartDate(v4);
        ep.setEndDate(v5);
        ep.setNumberRepetitions(v6);
        if (v7.equals("Yes"))
            ep.setCauseOffWork(true);
        else
            ep.setCauseOffWork(false);
        ep.setDiseases(v8);
    }

    /**
     * @param v1
     * @param v2
     * @param v3
     *
     * Este método permite eliminar un Episodio.
     */
    public void removeEpisode(int v1, int v2, int v3)
    {
        this.medicList.get(v1).getPatientList().get(v2).getHistory().getEpisodeList().remove(v3);

        for(Episodes e : this.medicList.get(v1).getPatientList().get(v2).getHistory().getEpisodeList())
        {
            int newId = e.getIdE();
            if(newId > 1)
                e.setIdE(newId - 1);
        }
        this.idLastE = this.medicList.get(v1).getPatientList().get(v2).getHistory().getEpisodeList().size();
    }

    /**
     * @param v1
     * @param v2
     * @param v3
     * @param v4
     * @param v5
     * @param v6
     * @param v7
     * @param v8
     *
     * Este método permite registrar un Análisis
     */
    public void registerAnalysis(String v1, String v2, Date v3, Date v4, String v5, Date v6, int v7, int v8) {
        Analysis a = new Analysis();

        this.idLastA = this.medicList.get(v7).getPatientList().get(v8).getHistory().getAnalysisList().size();

        a.setIdA(this.idLastA);
        a.setReport(v1);
        a.setHospital(v2);
        a.setReceptionDate(v3);
        a.setAnalysisDate(v4);
        a.setAnalysisType(v5);
        a.setReportDate(v6);
        a.setIdPatient(v8);

        this.medicList.get(v7).getPatientList().get(v8).getHistory().getAnalysisList().add(a);
        this.idLastA = this.idLastA + 1;
    }

    /**
     * @param v1
     * @param v2
     * @param v3
     * @param v4
     * @param v5
     * @param v6
     * @param v7
     * @param v8
     * @param v9
     *
     * Este método permite actualizar un Análisis
     */
    public void updateAnalysis(String v1, String v2, Date v3, Date v4, String v5, Date v6, int v7, int v8, int v9) {
        Analysis a = this.medicList.get(v7).getPatientList().get(v8).getHistory().getAnalysisList().get(v9);

        a.setReport(v1);
        a.setHospital(v2);
        a.setReceptionDate(v3);
        a.setAnalysisDate(v4);
        a.setAnalysisType(v5);
        a.setReportDate(v6);
    }

    /**
     * @param v1
     * @param v2
     * @param v3
     *
     * Este método permite eliminar un Análisis.
     */
    public void removeAnalysis(int v1, int v2, int v3)
    {
        this.medicList.get(v1).getPatientList().get(v2).getHistory().getAnalysisList().remove(v3);

        for(Analysis a : this.medicList.get(v1).getPatientList().get(v2).getHistory().getAnalysisList())
        {
            int newId = a.getIdA();
            if(newId > 1)
                a.setIdA(newId - 1);
        }
        this.idLastA = this.medicList.get(v1).getPatientList().get(v2).getHistory().getAnalysisList().size();
    }

    /**
     * @param v1
     * @param v2
     * @param v3
     * @param v4
     * @param v5
     * @param v6
     * @param v7
     * @param v8
     * @param v9
     *
     * Este método permite añadir Radiografías
     */
    public void registerRadiography(Date v1, Date v2, Date v3, String v4, String v5, String v6, String v7, int v8, int v9) {
        Radiography r = new Radiography();

        this.idLastR = this.medicList.get(v8).getPatientList().get(v9).getHistory().getRadiographyList().size();

        r.setIdR(this.idLastR);
        r.setReportDate(v1);
        r.setReceptionDate(v2);
        r.setRadiographyDate(v3);
        r.setStudy(v4);
        r.setControlMonitored(v5);
        r.setInform(v6);
        r.setPerformance(v7);
        r.setIdPatient(v9);

        this.medicList.get(v8).getPatientList().get(v9).getHistory().getRadiographyList().add(r);
        this.idLastR = this.idLastR + 1;
    }

    /**
     * @param v1
     * @param v2
     * @param v3
     * @param v4
     * @param v5
     * @param v6
     * @param v7
     * @param v8
     * @param v9
     * @param v10
     *
     * Este método permite actualizar Radiografías
     */
    public void updateRadiography(Date v1, Date v2, Date v3, String v4, String v5, String v6, String v7, int v8, int v9, int v10) {
        Radiography r = this.medicList.get(v8).getPatientList().get(v9).getHistory().getRadiographyList().get(v10);

        r.setReportDate(v1);
        r.setReceptionDate(v2);
        r.setRadiographyDate(v3);
        r.setStudy(v4);
        r.setControlMonitored(v5);
        r.setInform(v6);
        r.setPerformance(v7);
    }

    /**
     * @param v1
     * @param v2
     * @param v3
     *
     * Este método permite eliminar Radiografías
     */
    public void removeRadiography(int v1, int v2, int v3)
    {
        this.medicList.get(v1).getPatientList().get(v2).getHistory().getRadiographyList().remove(v3);

        for(Radiography r : this.medicList.get(v1).getPatientList().get(v2).getHistory().getRadiographyList())
        {
            int newId = r.getIdR();
            if(newId > 1)
                r.setIdR(newId - 1);
        }

        this.idLastR = this.medicList.get(v1).getPatientList().get(v2).getHistory().getRadiographyList().size();
    }

    /**
     * @param v1
     * @param v2
     * @param v3
     * @param v4
     * @param v5
     * @param v6
     * @param v7
     * @param v8
     * @param v9
     * @param v10
     *
     * Este metodo permite añadir un Tratamiento Farmacologico
     */
    public void registerPharmacotherapy(Date v1, Date v2, String v3, String v4, String v5, float v6, float v7, String v8, int v9, int v10) {
        Pharmacotherapy ph = new Pharmacotherapy();

        this.idLastPh = this.medicList.get(v9).getPatientList().get(v10).getHistory().getPharmacotherapyList().size();

        ph.setIdPh(this.idLastPh);
        ph.setStartDate(v1);
        ph.setEndDate(v2);
        ph.setTherapyType(v3);
        ph.setDosage(v4);
        ph.setDescription(v5);
        ph.setInitialWeight(v6);
        ph.setFinalWeight(v7);
        ph.setMedicines(v8);
        ph.setIdPatient(v10);

        this.medicList.get(v9).getPatientList().get(v10).getHistory().getPharmacotherapyList().add(ph);
        this.idLastPh = this.idLastPh + 1;
    }

    /**
     * @param v1
     * @param v2
     * @param v3
     * @param v4
     * @param v5
     * @param v6
     * @param v7
     * @param v8
     * @param v9
     * @param v10
     * @param v11
     *
     * Este método permite modificar un Tratamiento Farmacológico
     */
    public void updatePharmacotherapy(Date v1, Date v2, String v3, String v4, String v5, Float v6, Float v7, String v8, int v9, int v10, int v11) {
        Pharmacotherapy ph = this.medicList.get(v9).getPatientList().get(v10).getHistory().getPharmacotherapyList().get(v10);

        ph.setStartDate(v1);
        ph.setEndDate(v2);
        ph.setTherapyType(v3);
        ph.setDosage(v4);
        ph.setDescription(v5);
        ph.setInitialWeight(v6);
        ph.setFinalWeight(v7);
        ph.setMedicines(v8);
    }

    /**
     * @param v1
     * @param v2
     * @param v3
     *
     * Este metodo permite eliminar un Tratamiento Farmacologico
     */
    public void removePharmacotherapy(int v1, int v2, int v3)
    {
        this.medicList.get(v1).getPatientList().get(v2).getHistory().getPharmacotherapyList().remove(v3);

        for(Pharmacotherapy ph : this.medicList.get(v1).getPatientList().get(v2).getHistory().getPharmacotherapyList())
        {
            int newId = ph.getIdPh();
            if(newId > 1)
                ph.setIdPh(newId - 1);
        }

        this.idLastPh = this.medicList.get(v1).getPatientList().get(v2).getHistory().getPharmacotherapyList().size();
    }

    /**
     * Este método permite escribir un archivo de configuracion, el cual almacena el PATH de los datos del vector medicList
     */
    public void writeConfigureFile()
    {
        Properties configuration = new Properties();
        configuration.setProperty("FilePath", CONFIGURE_FILE_PATH);

        try
        {
            configuration.store(new FileOutputStream("configuration.props"), "Path_of_Medic");
        }
        catch (FileNotFoundException fnfe)
        {
            fnfe.printStackTrace();
        }
        catch (IOException ioex)
        {
            ioex.printStackTrace();
        }
    }

    /**
     * @param path
     *
     * Este método permite escribir un archivo de configuracion con un PATH distinto y escogido por el usuario
     */
    public void writeConfigureFile(String path)
    {
        Properties configuration = new Properties();
        configuration.setProperty("FilePath", path);

        try
        {
            configuration.store(new FileOutputStream("configuration.props"), "Path_of_Medic");
            writeFile();
        }
        catch (FileNotFoundException fnfe)
        {
            fnfe.printStackTrace();
        }
        catch (IOException ioex)
        {
            ioex.printStackTrace();
        }
    }

    /**
     * @return
     * Este función devuelve la localización del archivo que almacena los datos de medicList, leyendo un archivo de configuración
     */
    public String readConfigureFile()
    {
        Properties configuration = new Properties();

        File f = new File("configuration.props");
        try
        {
            configuration.load(new FileInputStream(f.getAbsolutePath()));
            return configuration.getProperty("FilePath");
        }
        catch (FileNotFoundException fnfe)
        {
            fnfe.printStackTrace();
        }
        catch (IOException ioex)
        {
            ioex.printStackTrace();
        }
        return null;
    }

    /**
     * Este método permite escribir los datos de medicList
     */
    public void writeFile()
    {
        ObjectOutputStream obj = null;
        try
        {
            obj = new ObjectOutputStream(new FileOutputStream(readConfigureFile()));
            obj.writeObject(this.medicList);
        }
        catch (IOException ioex)
        {
            ioex.printStackTrace();
        }
        finally
        {
            if (obj != null)
            {
                try
                {
                    obj.close();
                }
                catch (IOException ioex)
                {
                    ioex.printStackTrace();
                }
            }
        }
    }

    /**
     * @param path
     * Este método permite escribir los datos de medicList en una localizacion escogida por el usuario
     */
    public void writeFile(String path)
    {
        ObjectOutputStream obj = null;
        try
        {
            obj = new ObjectOutputStream(new FileOutputStream(path));
            obj.writeObject(this.medicList);
        }
        catch (IOException ioex)
        {
            ioex.printStackTrace();
        }
        finally
        {
            if (obj != null)
            {
                try
                {
                    obj.close();
                }
                catch (IOException ioex)
                {
                    ioex.printStackTrace();
                }
            }
        }
    }

    /**
     * Este método permite leer el archivo con los datos de medicList
     */
    public void readFile()
    {
        ObjectInputStream obj = null;
        try
        {
            obj = new ObjectInputStream(new FileInputStream(readConfigureFile()));
            this.medicList = (ArrayList<Medic>) obj.readObject();

        }
        catch (FileNotFoundException fnfe)
        {
            fnfe.printStackTrace();
        }
        catch (ClassNotFoundException cnfe)
        {
            cnfe.printStackTrace();
        }
        catch (IOException ioex)
        {
            ioex.printStackTrace();
        }
        finally
        {
            if (obj != null)
            {
                try
                {
                    obj.close();
                }
                catch (IOException ioex)
                {
                    ioex.printStackTrace();
                }
            }
        }
    }

    /**
     * @param path
     *
     * Este método permite escribir un archivo XML con los datos de medicList en la ubicacion y con el nombre deseado
     */
    public void writeXML(String path)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document = null;
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        try
        {
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation dom = builder.getDOMImplementation();
            document = dom.createDocument(null, "xml", null);

            Element root = document.createElement("Medics");
            document.getDocumentElement().appendChild(root);

            Element medicNode = null;
            Element patientsNode = null;
            Element patientNode = null;
            Element historyNode = null;
            Element episodesNode = null;
            Element episodeNode = null;
            Element analysisNode = null;
            Element analysNode = null;
            Element radiographsNode = null;
            Element radiographyNode = null;
            Element pharmacotherapsNode = null;
            Element pharmacotherapyNode = null;


            Element dataNode;
            Text text = null;

            for (Medic m : this.medicList)
            {
                medicNode = document.createElement("Medic");
                root.appendChild(medicNode);

                dataNode = document.createElement("IdMedic");
                medicNode.appendChild(dataNode);

                text = document.createTextNode(String.valueOf(m.getIdM()));
                dataNode.appendChild(text);

                dataNode = document.createElement("Name");
                medicNode.appendChild(dataNode);

                text = document.createTextNode(m.getName());
                dataNode.appendChild(text);

                dataNode = document.createElement("Surname");
                medicNode.appendChild(dataNode);

                text = document.createTextNode(m.getSurname());
                dataNode.appendChild(text);

                dataNode = document.createElement("MedicalSpeciality");
                medicNode.appendChild(dataNode);

                text = document.createTextNode(m.getMedicalSpeciality());
                dataNode.appendChild(text);

                dataNode = document.createElement("CollegiateNumber");
                medicNode.appendChild(dataNode);

                text = document.createTextNode(m.getCollegiateNumber());
                dataNode.appendChild(text);

                dataNode = document.createElement("Address");
                medicNode.appendChild(dataNode);

                text = document.createTextNode(m.getAddress());
                dataNode.appendChild(text);

                dataNode = document.createElement("BirthDate");
                medicNode.appendChild(dataNode);

                text = document.createTextNode(df.format(m.getBirthDate()));
                dataNode.appendChild(text);

                dataNode = document.createElement("User");
                medicNode.appendChild(dataNode);

                text = document.createTextNode(m.getUser());
                dataNode.appendChild(text);

                dataNode = document.createElement("Password");
                medicNode.appendChild(dataNode);

                text = document.createTextNode(m.getPassword());
                dataNode.appendChild(text);

                patientsNode = document.createElement("Patients");
                medicNode.appendChild(patientsNode);
                for (Patient p : m.getPatientList())
                {
                    patientNode = document.createElement("Patient");
                    patientsNode.appendChild(patientNode);

                    dataNode = document.createElement("IdPatient");
                    patientNode.appendChild(dataNode);

                    text = document.createTextNode(String.valueOf(p.getIdP()));
                    dataNode.appendChild(text);

                    dataNode = document.createElement("IdMedic");
                    patientNode.appendChild(dataNode);

                    text = document.createTextNode(String.valueOf(p.getIdMed()));
                    dataNode.appendChild(text);

                    dataNode = document.createElement("Name");
                    patientNode.appendChild(dataNode);

                    text = document.createTextNode(p.getName());
                    dataNode.appendChild(text);

                    dataNode = document.createElement("Surname");
                    patientNode.appendChild(dataNode);

                    text = document.createTextNode(p.getSurname());
                    dataNode.appendChild(text);

                    dataNode = document.createElement("BirthDate");
                    patientNode.appendChild(dataNode);

                    text = document.createTextNode(df.format(p.getBirthDate()));
                    dataNode.appendChild(text);

                    dataNode = document.createElement("Address");
                    patientNode.appendChild(dataNode);

                    text = document.createTextNode(p.getAddress());
                    dataNode.appendChild(text);

                    historyNode = document.createElement("History");
                    patientNode.appendChild(historyNode);

                    dataNode = document.createElement("HistoryNumber");
                    historyNode.appendChild(dataNode);

                    text = document.createTextNode(p.getHistory().getCiasNumber());
                    dataNode.appendChild(text);

                    dataNode = document.createElement("IdPatient");
                    historyNode.appendChild(dataNode);

                    text = document.createTextNode(String.valueOf(p.getHistory().getIdPatient()));
                    dataNode.appendChild(text);

                    episodesNode = document.createElement("Episodes");
                    historyNode.appendChild(episodesNode);
                    for (Episodes e : p.getHistory().getEpisodeList())
                    {
                        episodeNode = document.createElement("Episode");
                        episodesNode.appendChild(episodeNode);

                        dataNode = document.createElement("IdEpisode");
                        episodeNode.appendChild(dataNode);

                        text = document.createTextNode(String.valueOf(e.getIdE()));
                        dataNode.appendChild(text);

                        dataNode = document.createElement("IdPatient");
                        episodeNode.appendChild(dataNode);

                        text = document.createTextNode(String.valueOf(e.getIdPatient()));
                        dataNode.appendChild(text);

                        dataNode = document.createElement("Description");
                        episodeNode.appendChild(dataNode);

                        text = document.createTextNode(e.getDescription());
                        dataNode.appendChild(text);

                        dataNode = document.createElement("Observation");
                        episodeNode.appendChild(dataNode);

                        text = document.createTextNode(e.getObservation());
                        dataNode.appendChild(text);

                        dataNode = document.createElement("Evolution");
                        episodeNode.appendChild(dataNode);

                        text = document.createTextNode(e.getEvolution());
                        dataNode.appendChild(text);

                        dataNode = document.createElement("StartDate");
                        episodeNode.appendChild(dataNode);

                        text = document.createTextNode(df.format(e.getStartDate()));
                        dataNode.appendChild(text);

                        dataNode = document.createElement("EndDate");
                        episodeNode.appendChild(dataNode);

                        text = document.createTextNode(df.format(e.getEndDate()));
                        dataNode.appendChild(text);

                        dataNode = document.createElement("NumberRepetitions");
                        episodeNode.appendChild(dataNode);

                        text = document.createTextNode(String.valueOf(e.getNumberRepetitions()));
                        dataNode.appendChild(text);

                        dataNode = document.createElement("CauseOffWork");
                        episodeNode.appendChild(dataNode);

                        text = document.createTextNode(String.valueOf(e.isCauseOffWork()));
                        dataNode.appendChild(text);

                        dataNode = document.createElement("Disease");
                        episodeNode.appendChild(dataNode);

                        text = document.createTextNode(e.getDiseases());
                        dataNode.appendChild(text);
                    }

                    analysisNode = document.createElement("Analisys");
                    historyNode.appendChild(analysisNode);
                    for (Analysis a : p.getHistory().getAnalysisList())
                    {
                        analysNode = document.createElement("Analys");
                        analysisNode.appendChild(analysNode);

                        dataNode = document.createElement("IdAnalysis");
                        analysNode.appendChild(dataNode);

                        text = document.createTextNode(String.valueOf(a.getIdA()));
                        dataNode.appendChild(text);

                        dataNode = document.createElement("IdPatient");
                        analysNode.appendChild(dataNode);

                        text = document.createTextNode(String.valueOf(a.getIdPatient()));
                        dataNode.appendChild(text);

                        dataNode = document.createElement("Report");
                        analysNode.appendChild(dataNode);

                        text = document.createTextNode(a.getReport());
                        dataNode.appendChild(text);

                        dataNode = document.createElement("Hospital");
                        analysNode.appendChild(dataNode);

                        text = document.createTextNode(a.getHospital());
                        dataNode.appendChild(text);

                        dataNode = document.createElement("ReceptionDate");
                        analysNode.appendChild(dataNode);

                        text = document.createTextNode(df.format(a.getReceptionDate()));
                        dataNode.appendChild(text);

                        dataNode = document.createElement("AnalysisDate");
                        analysNode.appendChild(dataNode);

                        text = document.createTextNode(df.format(a.getAnalysisDate()));
                        dataNode.appendChild(text);

                        dataNode = document.createElement("AnalysisType");
                        analysNode.appendChild(dataNode);

                        text = document.createTextNode(a.getAnalysisType());
                        dataNode.appendChild(text);

                        dataNode = document.createElement("ReportDate");
                        analysNode.appendChild(dataNode);

                        text = document.createTextNode(df.format(a.getReportDate()));
                        dataNode.appendChild(text);
                    }

                    radiographsNode = document.createElement("Radiographs");
                    historyNode.appendChild(radiographsNode);
                    for (Radiography r : p.getHistory().getRadiographyList())
                    {
                        radiographyNode = document.createElement("Radiography");
                        radiographsNode.appendChild(radiographyNode);

                        dataNode = document.createElement("IdRadiography");
                        radiographyNode.appendChild(dataNode);

                        text = document.createTextNode(String.valueOf(r.getIdR()));
                        dataNode.appendChild(text);

                        dataNode = document.createElement("IdPatient");
                        radiographyNode.appendChild(dataNode);

                        text = document.createTextNode(String.valueOf(r.getIdPatient()));
                        dataNode.appendChild(text);

                        dataNode = document.createElement("ReportDate");
                        radiographyNode.appendChild(dataNode);

                        text = document.createTextNode(df.format(r.getReportDate()));
                        dataNode.appendChild(text);

                        dataNode = document.createElement("ReceptionDate");
                        radiographyNode.appendChild(dataNode);

                        text = document.createTextNode(df.format(r.getReceptionDate()));
                        dataNode.appendChild(text);

                        dataNode = document.createElement("RadiographyDate");
                        radiographyNode.appendChild(dataNode);

                        text = document.createTextNode(df.format(r.getRadiographyDate()));
                        dataNode.appendChild(text);

                        dataNode = document.createElement("Study");
                        radiographyNode.appendChild(dataNode);

                        text = document.createTextNode(r.getStudy());
                        dataNode.appendChild(text);

                        dataNode = document.createElement("ControlMonitored");
                        radiographyNode.appendChild(dataNode);

                        text = document.createTextNode(r.getControlMonitored());
                        dataNode.appendChild(text);

                        dataNode = document.createElement("Inform");
                        radiographyNode.appendChild(dataNode);

                        text = document.createTextNode(r.getInform());
                        dataNode.appendChild(text);

                        dataNode = document.createElement("Performance");
                        radiographyNode.appendChild(dataNode);

                        text = document.createTextNode(r.getPerformance());
                        dataNode.appendChild(text);
                    }

                    pharmacotherapsNode = document.createElement("Pharmacotheraps");
                    historyNode.appendChild(pharmacotherapsNode);
                    for (Pharmacotherapy ph : p.getHistory().getPharmacotherapyList())
                    {
                        pharmacotherapyNode = document.createElement("Pharmacotherapy");
                        pharmacotherapsNode.appendChild(pharmacotherapyNode);

                        dataNode = document.createElement("IdPharmacotherapy");
                        pharmacotherapyNode.appendChild(dataNode);

                        text = document.createTextNode(String.valueOf(ph.getIdPh()));
                        dataNode.appendChild(text);

                        dataNode = document.createElement("IdPatient");
                        pharmacotherapyNode.appendChild(dataNode);

                        text = document.createTextNode(String.valueOf(ph.getIdPatient()));
                        dataNode.appendChild(text);

                        dataNode = document.createElement("StartDate");
                        pharmacotherapyNode.appendChild(dataNode);

                        text = document.createTextNode(df.format(ph.getStartDate()));
                        dataNode.appendChild(text);

                        dataNode = document.createElement("EndDate");
                        pharmacotherapyNode.appendChild(dataNode);

                        text = document.createTextNode(df.format(ph.getEndDate()));
                        dataNode.appendChild(text);

                        dataNode = document.createElement("TherapyType");
                        pharmacotherapyNode.appendChild(dataNode);

                        text = document.createTextNode(String.valueOf(ph.getTherapyType()));
                        dataNode.appendChild(text);

                        dataNode = document.createElement("Dosage");
                        pharmacotherapyNode.appendChild(dataNode);

                        text = document.createTextNode(ph.getDosage());
                        dataNode.appendChild(text);

                        dataNode = document.createElement("Description");
                        pharmacotherapyNode.appendChild(dataNode);

                        text = document.createTextNode(String.valueOf(ph.getDescription()));
                        dataNode.appendChild(text);

                        dataNode = document.createElement("InitialWeight");
                        pharmacotherapyNode.appendChild(dataNode);

                        text = document.createTextNode(String.valueOf(ph.getInitialWeight()));
                        dataNode.appendChild(text);

                        dataNode = document.createElement("FinalWeight");
                        pharmacotherapyNode.appendChild(dataNode);

                        text = document.createTextNode(String.valueOf(ph.getFinalWeight()));
                        dataNode.appendChild(text);

                        dataNode = document.createElement("Medicines");
                        pharmacotherapyNode.appendChild(dataNode);

                        text = document.createTextNode(String.valueOf(ph.getMedicines()));
                        dataNode.appendChild(text);
                    }
                }
            }

            TransformerFactory transformFactory = TransformerFactory.newInstance();
            Transformer transformer = transformFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(path));

            transformer.transform(source, result);
        }
        catch (ParserConfigurationException pce)
        {
            pce.printStackTrace();
        }
        catch (TransformerConfigurationException tce)
        {
            tce.printStackTrace();
        }
        catch (TransformerException te)
        {
            te.printStackTrace();
        }
    }

    /**
     * @param path
     *
     * Este método permite leer un XML e importarlo al programa
     */
    public void readXML(String path)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document = null;
        ArrayList<Medic> arrayMedics = new ArrayList<Medic>();

        try
        {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(new File(path));
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            document.getDocumentElement().normalize();

            NodeList medics = document.getElementsByTagName("Medic");
            for (int i = 0; i < medics.getLength(); i++)
            {
                Node nMedic = medics.item(i);

                if (nMedic.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eMedic = (Element) nMedic;

                    Medic m = new Medic();

                    m.setIdM(Integer.parseInt(eMedic.getElementsByTagName("IdMedic").item(0).getTextContent()));
                    m.setName(String.valueOf(eMedic.getElementsByTagName("Name").item(0).getTextContent()));
                    m.setSurname(String.valueOf(eMedic.getElementsByTagName("Surname").item(0).getTextContent()));
                    m.setMedicalSpeciality(String.valueOf(eMedic.getElementsByTagName("MedicalSpeciality").item(0).getTextContent()));
                    m.setCollegiateNumber(String.valueOf(eMedic.getElementsByTagName("CollegiateNumber").item(0).getTextContent()));
                    m.setAddress(String.valueOf(eMedic.getElementsByTagName("Address").item(0).getTextContent()));
                    m.setBirthDate(df.parse(eMedic.getElementsByTagName("BirthDate").item(0).getTextContent()));
                    m.setUser(String.valueOf(eMedic.getElementsByTagName("User").item(0).getTextContent()));
                    m.setPassword(String.valueOf(eMedic.getElementsByTagName("Password").item(0).getTextContent()));

                    arrayMedics.add(m);

                    NodeList nPatients = eMedic.getElementsByTagName("Patient");

                    int sw = 0;
                    for (int j = 0; j < nPatients.getLength(); j++)
                    {
                        Node nPatient = nPatients.item(j);


                        if (nPatient.getNodeType() == Node.ELEMENT_NODE)
                        {
                            Element ePatient = (Element) nPatient;

                            NodeList nHistory = ePatient.getElementsByTagName("History");

                            Element eHistory = null;

                            if (nHistory.getLength() > 0)
                                eHistory = (Element) nHistory.item(0);

                            Patient p = new Patient();
                            p.setIdP(Integer.parseInt(ePatient.getElementsByTagName("IdPatient").item(0).getTextContent()));
                            p.setIdMed(Integer.parseInt(ePatient.getElementsByTagName("IdMedic").item(0).getTextContent()));
                            p.setName(ePatient.getElementsByTagName("Name").item(0).getTextContent());
                            p.setSurname(ePatient.getElementsByTagName("Surname").item(0).getTextContent());
                            p.setBirthDate(df.parse(ePatient.getElementsByTagName("BirthDate").item(0).getTextContent()));
                            p.setAddress(ePatient.getElementsByTagName("Address").item(0).getTextContent());

                            History h = new History();
                            h.setCiasNumber(eHistory.getElementsByTagName("HistoryNumber").item(0).getTextContent());
                            h.setIdPatient(Integer.parseInt(eHistory.getElementsByTagName("IdPatient").item(0).getTextContent()));

                            p.setHistory(h);

                            arrayMedics.get(i).getPatientList().add(p);


                            NodeList nPharmacotheraps = eHistory.getElementsByTagName("Pharmacotherapy");
                            NodeList nRadiographs = eHistory.getElementsByTagName("Radiography");
                            NodeList nAnalysis = eHistory.getElementsByTagName("Analisys");
                            NodeList nEpisodes = eHistory.getElementsByTagName("Episode");


                            for (int k = 0; k < nEpisodes.getLength(); k++)
                            {

                                Node nEpisode = nEpisodes.item(k);

                                if(nEpisode.getNodeType() == Node.ELEMENT_NODE)
                                {
                                    Element eEpisode = (Element) nEpisode;
                                    if (eEpisode.getElementsByTagName("IdEpisode").item(0) == null)
                                        break;

                                    Episodes e = new Episodes();
                                    e.setIdE(Integer.parseInt(eEpisode.getElementsByTagName("IdEpisode").item(0).getTextContent()));
                                    e.setIdPatient(Integer.parseInt(eEpisode.getElementsByTagName("IdPatient").item(0).getTextContent()));
                                    e.setDescription(eEpisode.getElementsByTagName("Description").item(0).getTextContent());
                                    e.setObservation(eEpisode.getElementsByTagName("Observation").item(0).getTextContent());
                                    e.setEvolution(eEpisode.getElementsByTagName("Evolution").item(0).getTextContent());
                                    e.setStartDate(df.parse(eEpisode.getElementsByTagName("StartDate").item(0).getTextContent()));
                                    e.setEndDate(df.parse(eEpisode.getElementsByTagName("EndDate").item(0).getTextContent()));
                                    e.setNumberRepetitions(Integer.parseInt(eEpisode.getElementsByTagName("NumberRepetitions").item(0).getTextContent()));
                                    e.setCauseOffWork(Boolean.valueOf(eEpisode.getElementsByTagName("CauseOffWork").item(0).getTextContent()));
                                    e.setDiseases(eEpisode.getElementsByTagName("Disease").item(0).getTextContent());

                                    arrayMedics.get(i).getPatientList().get(j).getHistory().getEpisodeList().add(e);
                                }

                            }

                            sw = 0;

                            for(int k=0; k<nAnalysis.getLength(); k++) {

                                Node nAnalys = nAnalysis.item(k);

                                if (nAnalys.getNodeType() == Node.ELEMENT_NODE)
                                {
                                    Element eAnalys = (Element) nAnalys;

                                    /**
                                     * Este control esta incluido debido a que el elemento DOM con el que trabajamos XML, tiene una serie de fallos,
                                     * los cuales se desconece completamente la causa, no tienen una solucion inmediata. La única solucion disponible es
                                     * trabajar con otro tipo de elementos, los cuales a falta de tiempo no he incorporado.
                                     * El error que devuelve a veces es un null que no tiene mucho sentido (sólo aparece en algunos casos, y siempre cuando no hay nada guardado en
                                     * el nodo al que apunta)
                                     */
                                    if (eAnalys.getElementsByTagName("Report").item(0) == null)
                                        break;;
                                    Analysis a = new Analysis();
                                    a.setReport(eAnalys.getElementsByTagName("Report").item(0).getTextContent());
                                    a.setHospital(eAnalys.getElementsByTagName("Hospital").item(0).getTextContent());
                                    a.setReceptionDate(df.parse(eAnalys.getElementsByTagName("ReceptionDate").item(0).getTextContent()));
                                    a.setAnalysisDate(df.parse(eAnalys.getElementsByTagName("AnalysisDate").item(0).getTextContent()));
                                    a.setAnalysisType(eAnalys.getElementsByTagName("AnalysisType").item(0).getTextContent());
                                    a.setReportDate(df.parse(eAnalys.getElementsByTagName("ReportDate").item(0).getTextContent()));

                                    arrayMedics.get(i).getPatientList().get(j).getHistory().getAnalysisList().add(a);
                                }
                            }

                            for(int k=0; k< nRadiographs.getLength(); k++)
                            {

                                Node nRadiography = nRadiographs.item(k);

                                if(nRadiography.getNodeType() == Node.ELEMENT_NODE)
                                {
                                    Element eRadiography = (Element) nRadiography;

                                    if (eRadiography.getElementsByTagName("ReportDate").item(0) == null)
                                        break;

                                    Radiography r = new Radiography();
                                    r.setReportDate(df.parse(eRadiography.getElementsByTagName("ReportDate").item(0).getTextContent()));
                                    r.setReceptionDate(df.parse(eRadiography.getElementsByTagName("ReceptionDate").item(0).getTextContent()));
                                    r.setRadiographyDate(df.parse(eRadiography.getElementsByTagName("RadiographyDate").item(0).getTextContent()));
                                    r.setStudy(eRadiography.getElementsByTagName("Study").item(0).getTextContent());
                                    r.setControlMonitored(eRadiography.getElementsByTagName("ControlMonitored").item(0).getTextContent());
                                    r.setInform(eRadiography.getElementsByTagName("Inform").item(0).getTextContent());
                                    r.setPerformance(eRadiography.getElementsByTagName("Performance").item(0).getTextContent());

                                    arrayMedics.get(i).getPatientList().get(j).getHistory().getRadiographyList().add(r);
                                }
                            }

                            for(int k=0; k<nPharmacotheraps.getLength(); k++)
                            {

                                Node nPharmacotherapy = nPharmacotheraps.item(k);

                                if(nPharmacotherapy.getNodeType() == Node.ELEMENT_NODE)
                                {
                                    Element ePharmacotherapy = (Element) nPharmacotherapy;
                                    if (ePharmacotherapy.getElementsByTagName("StartDate").item(0) == null)
                                        break;

                                    Pharmacotherapy ph = new Pharmacotherapy();
                                    ph.setStartDate(df.parse(ePharmacotherapy.getElementsByTagName("StartDate").item(0).getTextContent()));
                                    ph.setEndDate(df.parse(ePharmacotherapy.getElementsByTagName("EndDate").item(0).getTextContent()));
                                    ph.setTherapyType(ePharmacotherapy.getElementsByTagName("TherapyType").item(0).getTextContent());
                                    ph.setDosage(ePharmacotherapy.getElementsByTagName("Dosage").item(0).getTextContent());
                                    ph.setDescription(ePharmacotherapy.getElementsByTagName("Description").item(0).getTextContent());
                                    ph.setInitialWeight(Float.parseFloat(ePharmacotherapy.getElementsByTagName("InitialWeight").item(0).getTextContent()));
                                    ph.setFinalWeight(Float.parseFloat(ePharmacotherapy.getElementsByTagName("FinalWeight").item(0).getTextContent()));
                                    ph.setMedicines(ePharmacotherapy.getElementsByTagName("Medicines").item(0).getTextContent());

                                    arrayMedics.get(i).getPatientList().get(j).getHistory().getPharmacotherapyList().add(ph);
                                }
                            }

                        }

                    }

                }
            }
            this.medicList = arrayMedics;
            writeFile();
        }
        catch (ParserConfigurationException e1)
        {
            e1.printStackTrace();
        }
        catch (SAXException e1)
        {
            e1.printStackTrace();
        }
        catch (ParseException e1)
        {
            e1.printStackTrace();
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
    }
}