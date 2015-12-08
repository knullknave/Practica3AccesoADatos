package com.dam.practic2.Model.Methods;

import com.dam.practic2.Controller.Controller;
import com.dam.practic2.Model.Objects.*;
import com.dam.practic2.View.JConnection;

import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Properties;
import java.util.Random;

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
    public Connection conexion;
    public int medicConnected;

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

        if(new File("Conf.props").exists())
        {
            String[] datos = readConfigureFile();
            try
            {
			/*
			 * Carga el driver de conexión JDBC para conectar con MySQL
			 */
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                conexion = DriverManager.getConnection("jdbc:mysql://" + datos[0] + ":3306" + "/MedicDB", datos[1], datos[2]);
            }
            catch (ClassNotFoundException cnfe)
            {
                JOptionPane.showMessageDialog(null, "No se ha podido cargar el driver de la Base de Datos");
            }
            catch (SQLException sqle)
            {
                JOptionPane.showMessageDialog(null, "No se ha podido conectar con la Base de Datos");
                sqle.printStackTrace();
            }
            catch (InstantiationException ie)
            {
                ie.printStackTrace();
            }
            catch (IllegalAccessException iae)
            {
                iae.printStackTrace();
            }
        }
        else
        {
            JConnection jc = new JConnection(c, this);
            jc.mostrar();
        }
    }

    public ArrayList<Object[]> SelectAllMedic()
    {
        String sql = "SELECT * FROM medic";
        ArrayList<Object[]> list = new ArrayList<>();
        Object[] Row;

        try
        {
            PreparedStatement sentencia = null;

            sentencia = conexion.prepareStatement(sql);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next())
            {
                int id = resultado.getInt("collegiateNumber");
                String name = resultado.getString("name");
                String surname = resultado.getString("surname");
                String adress = resultado.getString("adress");
                String medicalCentre = resultado.getString("medicalCentre");
                String email = resultado.getString("email");
                String medicalSpeciality = resultado.getString("medicalSpeciality");
                String telephone = resultado.getString("telephone");
                Date birthDate = resultado.getDate("birthDate");

                Row = new Object[] {id, name, surname, adress, medicalCentre, email, medicalSpeciality, telephone, birthDate};
                list.add(Row);
            }
            return list;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Object[] selectMedic(int idM)
    {
        String sql = "SELECT collegiateNumber, name, surname, adress, medicalCentre, email, medicalSpeciality, telephone, birthDate FROM medic WHERE collegiateNumber = ?";
        ArrayList<Object[]> list = new ArrayList<>();
        Object[] Row = new Object[9];
        try
        {
            PreparedStatement sentencia = null;

            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, idM);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next())
            {
                int id = resultado.getInt("collegiateNumber");
                String name = resultado.getString("name");
                String surname = resultado.getString("surname");
                String adress = resultado.getString("adress");
                String medicalCentre = resultado.getString("medicalCentre");
                String email = resultado.getString("email");
                String medicalSpeciality = resultado.getString("medicalSpeciality");
                String telephone = resultado.getString("telephone");
                Date birthDate = resultado.getDate("birthDate");

                Row = new Object[] {id, name, surname, adress, medicalCentre, email, medicalSpeciality, telephone, birthDate};
                list.add(Row);
            }
            return Row;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public boolean existsMedic(String u, String p)
    {
        String query = "SELECT COUNT(*) as cuenta FROM medic WHERE userName = ? AND userPasword = ?";

        try
        {
            PreparedStatement sentencia = null;
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1, u);
            sentencia.setString(2, p);

            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next())
            {
                int id = resultado.getInt("cuenta");

                if(id == 0)
                    return false;
                else
                    return true;
            }
        }
        catch (SQLException e)
        {

            e.printStackTrace();
        }
        return false;
    }

    public boolean existsMedic2(String u, String p, int idM)
    {
        String query = "SELECT COUNT(*) as cuenta FROM medic WHERE userName = ? AND userPasword = ? AND collegiateNumber = ?";

        try
        {
            PreparedStatement sentencia = null;
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1, u);
            sentencia.setString(2, p);
            sentencia.setInt(3, idM);

            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next())
            {
                int id = resultado.getInt("cuenta");

                if(id == 0)
                    return false;
                else
                    return true;
            }
        }
        catch (SQLException e)
        {

            e.printStackTrace();
        }
        return false;
    }

    public boolean existsMedic3(int idM)
    {
        String query = "SELECT COUNT(*) as cuenta FROM medic WHERE collegiateNumber = ?";

        try
        {
            PreparedStatement sentencia = null;
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1, idM);

            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next())
            {
                int id = resultado.getInt("cuenta");

                if(id == 0)
                    return false;
                else
                    return true;
            }
        }
        catch (SQLException e)
        {

            e.printStackTrace();
        }
        return false;
    }

    public void updateMedic(String name, String surname, String adress, String medCentre, String email, String medSpeciality, String telephone, java.sql.Date birth, int idM)
    {
        String sql = "UPDATE medic SET name = ?, surname = ?, adress = ?, medicalCentre = ?, email = ?, medicalSpeciality = ?, telephone = ?, birthDate = ? WHERE collegiateNumber = ?";
        PreparedStatement sentence = null;

        try
        {
            sentence = conexion.prepareStatement(sql);
            sentence.setString(1, name);
            sentence.setString(2, surname);
            sentence.setString(3, adress);
            sentence.setString(4, medCentre);
            sentence.setString(5, email);
            sentence.setString(6, medSpeciality);
            sentence.setString(7, telephone);
            sentence.setDate(8, birth);
            sentence.setInt(9, idM);
            sentence.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void deleteMedic(int idM)
    {
        String sql = "DELETE FROM medic WHERE collegiateNumber = ?";
        PreparedStatement sentencia = null;
        try
        {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, idM);
            sentencia.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void insertMedic(String uName, String uPass, String name, String surname, String adress, String medCentre, String email, String medSpeciality, String telephone, java.sql.Date birth)
    {
        String sql = "INSERT INTO medic(userName, userPasword, name, surname, adress, medicalCentre, email, medicalSpeciality, telephone, birthDate) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement sentencia = null;
        try
        {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, uName);
            sentencia.setString(2, uPass);
            sentencia.setString(3, name);
            sentencia.setString(4, surname);
            sentencia.setString(5, adress);
            sentencia.setString(6, medCentre);
            sentencia.setString(7, email);
            sentencia.setString(8, medSpeciality);
            sentencia.setString(9, telephone);
            sentencia.setDate(10, birth);
            sentencia.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //TODO NO FUNCIONA?
    public ArrayList<Object[]> selectAllPatient(String user, String pass)
    {
        String sql = "SELECT P.cias, P.name, P.surname, P.sex FROM patient P INNER JOIN visit V ON P.cias = V.idPatient INNER JOIN medic M ON M.collegiateNumber = V.idMedic WHERE M.userName = ? and M.userPasword = ?";
        ArrayList<Object[]> list = new ArrayList<>();
        Object[] Row;

        try
        {
            PreparedStatement sentencia = null;

            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, user);
            sentencia.setString(2, pass);

            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next())
            {
                int id = resultado.getInt("cias");
                String name = resultado.getString("name");
                String surname = resultado.getString("surname");
                String sex = resultado.getString("sex");

                Row = new Object[] {id, name, surname, sex};
                list.add(Row);
            }
            return list;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Object[] selectPatient(int idP)
    {
        return null;
    }

    public void SelectAllEpisode(int idP)
    {

    }

    public Object[] selectEpisode(int idP, int idE)
    {
        return null;
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

        m.setIdM(0);
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
     *
     * Este método permite registrar pacientes. También guardo la ultima posicion que ocupa para añadirla al próximo
     * paciente
     */
    public void registerPatient(String v1, String v2, Date v3, String v4, int v5)
    {
        Patient p = new Patient();

        p.setIdP(0);
        p.setName(v1);
        p.setSurname(v2);
        p.setBirthDate(v3);
        p.setAddress(v4);
        p.getHistory();
        p.getHistory().setIdPatient(0);
        p.setIdMed(v5);
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

        e.setIdE(0);
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

    }

    /**
     * @param medPos
     *
     * Este método permite elimiar un médico
     */
    public void removeMedic(int medPos)
    {

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
    public void registerAnalysis(String v1, String v2, Date v3, Date v4, String v5, Date v6, int v7, int v8)
    {
        Analysis a = new Analysis();

        a.setIdA(0);
        a.setReport(v1);
        a.setHospital(v2);
        a.setReceptionDate(v3);
        a.setAnalysisDate(v4);
        a.setAnalysisType(v5);
        a.setReportDate(v6);
        a.setIdPatient(v8);
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
    public void updateAnalysis(String v1, String v2, Date v3, Date v4, String v5, Date v6, int v7, int v8, int v9)
    {

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
    public void registerRadiography(Date v1, Date v2, Date v3, String v4, String v5, String v6, String v7, int v8, int v9)
    {
        Radiography r = new Radiography();

        r.setIdR(0);
        r.setReportDate(v1);
        r.setReceptionDate(v2);
        r.setRadiographyDate(v3);
        r.setStudy(v4);
        r.setControlMonitored(v5);
        r.setInform(v6);
        r.setPerformance(v7);
        r.setIdPatient(v9);
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
    public void updateRadiography(Date v1, Date v2, Date v3, String v4, String v5, String v6, String v7, int v8, int v9, int v10)
    {

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
    public void registerPharmacotherapy(Date v1, Date v2, String v3, String v4, String v5, float v6, float v7, String v8, int v9, int v10)
    {
        Pharmacotherapy ph = new Pharmacotherapy();

        ph.setIdPh(0);
        ph.setStartDate(v1);
        ph.setEndDate(v2);
        ph.setTherapyType(v3);
        ph.setDosage(v4);
        ph.setDescription(v5);
        ph.setInitialWeight(v6);
        ph.setFinalWeight(v7);
        ph.setMedicines(v8);
        ph.setIdPatient(v10);
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
    public void updatePharmacotherapy(Date v1, Date v2, String v3, String v4, String v5, Float v6, Float v7, String v8, int v9, int v10, int v11)
    {

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

    }

    /**
     * Este método permite escribir un archivo de configuracion, el cual almacena el PATH de los datos del vector medicList
     */
    public void writeConfigureFile(String server, String user, String pass)
    {
        Properties p = new Properties();
        p.setProperty("Server", server);
        p.setProperty("User", user);
        p.setProperty("Password", pass);

        try
        {
            p.store(new FileOutputStream("Conf.props"), "First Line");
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @return
     * Este función devuelve la localización del archivo que almacena los datos de medicList, leyendo un archivo de configuración
     */
    public String[] readConfigureFile()
    {
        Properties p = new Properties();
        String[] datos = new String[3];
        try
        {
            p.load(new FileInputStream("Conf.props"));
            datos[0] = p.getProperty("Server");
            datos[1] = p.getProperty("User");
            datos[2] = p.getProperty("Password");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return datos;
    }
}