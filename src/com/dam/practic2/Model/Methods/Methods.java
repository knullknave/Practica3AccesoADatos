package com.dam.practic2.Model.Methods;

import com.dam.practic2.Controller.Controller;
import com.dam.practic2.Model.Objects.*;
import com.dam.practic2.View.JConnection;
import sun.util.calendar.Gregorian;

import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.io.*;
import java.sql.*;
import java.text.ParseException;
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
            }
            return Row;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Object[] selectPatient(int idP)
    {
        String sql = "SELECT P.cias, P.name, P.surname, P.birthDate, P.adress, V.visitDate, V.idVisit FROM patient P INNER JOIN visit V ON V.idPatient = P.cias WHERE P.cias = ?";
        Object[] Row = new Object[9];
        try
        {
            PreparedStatement sentencia = null;

            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, idP);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next())
            {
                int id = resultado.getInt("cias");
                String name = resultado.getString("name");
                String surname = resultado.getString("surname");
                java.util.Date birthDate = new Date(resultado.getDate("birthDate").getTime());
                String adress = resultado.getString("adress");
                java.util.Date visitDate = new Date(resultado.getDate("visitDate").getTime());
                int idVisit = resultado.getInt("idVisit");

                Row = new Object[] {id, name, surname, birthDate, adress, visitDate, idVisit};
            }
            return Row;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public  ArrayList<Object[]> selectAllEpisodes(int idP)
    {
        String sql = "SELECT id, descript, startDate, endDate, evolution FROM episodes E INNER JOIN visit V ON V.idEpisode = E.id AND V.idPatient = ?";
        ArrayList<Object[]> list = new ArrayList<>();
        Object[] Row;

        try
        {
            PreparedStatement sentencia = null;

            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, idP);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next())
            {
                int id = resultado.getInt("id");
                String descript = resultado.getString("descript");
                java.util.Date startDate = new Date(resultado.getDate("startDate").getTime());
                java.util.Date endDate = new Date(resultado.getDate("endDate").getTime());
                String evolution = resultado.getString("evolution");

                Row = new Object[] {id, descript, startDate, endDate, evolution};
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

    public boolean existsPatient(String name, String surname)
    {
        String query = "SELECT COUNT(*) as cuenta FROM patient WHERE name = ? AND surname = ?";

        try
        {
            PreparedStatement sentencia = null;
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1, name);
            sentencia.setString(2, surname);

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
        return true;
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
        String sql11 = "SELECT idPatient, idEpisode FROM visit WHERE idMedic = ?";
        String sql2 = "DELETE FROM visit WHERE idMedic = ?";
        String sql3 = "DELETE FROM patient WHERE cias = ?";
        String sql4 = "DELETE FROM episodes WHERE id = ?";
        PreparedStatement sentencia = null;
        try
        {
            conexion.setAutoCommit(false);
            sentencia = conexion.prepareStatement(sql11);
            sentencia.setInt(1, idM);
            ResultSet resultado = sentencia.executeQuery();
            int idP = 0;
            String idE = "";
            while (resultado.next())
            {
                idP = resultado.getInt("idPatient");
                idE = String.valueOf(resultado.getInt("idEpisode"));
            }

            if(!idE.equals(null))
            {
                sentencia = conexion.prepareStatement(sql4);
                sentencia.setInt(1, Integer.parseInt(idE));
                sentencia.executeUpdate();
            }

            sentencia = conexion.prepareStatement(sql3);
            sentencia.setInt(1, idP);
            sentencia.executeUpdate();

            sentencia = conexion.prepareStatement(sql2);
            sentencia.setInt(1, idM);
            sentencia.executeUpdate();

            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, idM);
            sentencia.executeUpdate();

            conexion.commit();
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

    public ArrayList<Object[]> selectAllPatient()
    {
        String sql = "SELECT P.cias, P.name, P.surname, P.birthDate, P.adress, V.visitDate, V.idVisit FROM patient P INNER JOIN visit V ON P.cias = V.idPatient INNER JOIN medic M ON M.collegiateNumber = V.idMedic WHERE M.collegiateNumber = ?";
        ArrayList<Object[]> list = new ArrayList<>();
        Object[] Row;

        try
        {
            PreparedStatement sentencia = null;

            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, medicConnected);

            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next())
            {
                int id = resultado.getInt("cias");
                String name = resultado.getString("name");
                String surname = resultado.getString("surname");
                java.util.Date birthDate = new Date(resultado.getDate("birthDate").getTime());
                String adress = resultado.getString("adress");
                java.util.Date visitDate = new Date(resultado.getDate("visitDate").getTime());
                int idVisit = resultado.getInt("idVisit");

                Row = new Object[] {id, name, surname, birthDate, adress, visitDate, idVisit};
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

    public void insertPatient(String name, String surname, java.sql.Date birth, String adress, java.sql.Date visitDate)
    {
        String sql = "INSERT INTO patient(name, surname, birthDate, adress) VALUES(?, ?, ?, ?)";
        String busqueda = "SELECT cias FROM patient WHERE name = ? AND surname = ?";
        String sql2 = "INSERT INTO visit(receptionDate, visitDate, medicalCentre, idAnalysis, idEpisode, idMedic, idPatient, idPharmacotherapy) VALUES (NULL, ?, NULL, NULL, NULL, ?, ?, NULL)";

        PreparedStatement sentenciaAltaPaciente = null;
        PreparedStatement sentenciaBusquedaIdP = null;
        PreparedStatement sentenciaAltaVisita = null;
        try
        {
            conexion.setAutoCommit(false);
            sentenciaAltaPaciente = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            sentenciaAltaPaciente.setString(1, name);
            sentenciaAltaPaciente.setString(2, surname);
            sentenciaAltaPaciente.setDate(3, birth);
            sentenciaAltaPaciente.setString(4, adress);
            sentenciaAltaPaciente.executeUpdate();

            sentenciaBusquedaIdP = conexion.prepareStatement(busqueda);
            sentenciaBusquedaIdP.setString(1, name);
            sentenciaBusquedaIdP.setString(2, surname);

            ResultSet resultado = sentenciaBusquedaIdP.executeQuery();
            int idP = 0;
            while (resultado.next())
            {
                idP = resultado.getInt("cias");
            }

            sentenciaAltaVisita = conexion.prepareStatement(sql2);
            sentenciaAltaVisita.setDate(1, visitDate);
            sentenciaAltaVisita.setInt(2, medicConnected);
            sentenciaAltaVisita.setInt(3, idP);
            sentenciaAltaVisita.executeUpdate();

            conexion.commit();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public int getCollegiateNumber(String user, String pass)
    {
        String sql = "SELECT collegiateNumber FROM medic WHERE userName = ? AND userPasword = ?";
        int idM = 0;
        try
        {
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, user);
            statement.setString(2, pass);

            ResultSet resultado = statement.executeQuery();
            while(resultado.next())
            {
                idM = resultado.getInt("collegiateNumber");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return idM;
    }

    public void updatePatient(int idP, String name, String surname, Date birth, String adress, Date visitDate)
    {
        String sql = "UPDATE patient SET name = ?, surname = ?, adress = ?, birthDate = ? WHERE cias = ?";
        String sql2 = "UPDATE visit SET visitDate = ?";
        PreparedStatement sentence = null;

        try
        {
            conexion.setAutoCommit(false);
            sentence = conexion.prepareStatement(sql);
            sentence.setString(1, name);
            sentence.setString(2, surname);
            sentence.setString(3, adress);
            sentence.setString(4, birth.toString());
            sentence.setInt(5, idP);
            sentence.executeUpdate();

            sentence = conexion.prepareStatement(sql2);
            sentence.setDate(1, visitDate);
            sentence.executeUpdate();

            conexion.commit();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void deletePatient(int idP)
    {
        String sql = "DELETE FROM patient WHERE cias = ?";
        String sql2 = "DELETE FROM visit WHERE idPatient = ?";
        String sql3 = "DELETE FROM episodes WHERE id = ?";
        String sql4 = "SELECT E.id FROM episodes E INNER JOIN visit V ON V.idEpisode = E.id AND V.idPatient = ?";

        PreparedStatement sentencia = null;
        PreparedStatement sentencia2 = null;
        PreparedStatement sentencia3 = null;
        PreparedStatement sentencia4 = null;
        try
        {
            conexion.setAutoCommit(false);
            sentencia4 = conexion.prepareStatement(sql4);
            sentencia4.setInt(1, idP);
            ResultSet resultado = sentencia4.executeQuery();
            int idE = 0;
            while (resultado.next())
            {
                idE = resultado.getInt("id");
            }

            sentencia3 = conexion.prepareStatement(sql3);
            sentencia3.setInt(1, idE);
            sentencia3.executeUpdate();

            sentencia2 = conexion.prepareStatement(sql2);
            sentencia2.setInt(1, idP);
            sentencia2.executeUpdate();

            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, idP);
            sentencia.executeUpdate();

            conexion.commit();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void insertEpisode(String d, java.sql.Date sD, java.sql.Date eD, String e, int idP)
    {
        String sql = "INSERT INTO episodes(descript, startDate, endDate, evolution) VALUES(?, ?, ?, ?)";
        String sql2 = "UPDATE visit SET idEpisode = ? WHERE idPatient = ?";
        String sql22 = "INSERT INTO visit(receptionDate, visitDate, medicalCentre, idAnalysis, idEpisode, idMedic, idPatient, idPharmacotherapy) VALUES (NULL, ?, NULL, NULL, ?, ?, ?, NULL) ON idPatient = ?";
        String sql3 = "SELECT idEpisode FROM visit WHERE idPatient = ?";

        PreparedStatement sentenciaAltaEpisode = null;
        PreparedStatement sentenciaAltaVisita = null;
        PreparedStatement sentenciaComprobacion = null;
        try
        {
            conexion.setAutoCommit(false);
            sentenciaAltaEpisode = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            sentenciaAltaEpisode.setString(1, d);
            sentenciaAltaEpisode.setDate(2, sD);
            sentenciaAltaEpisode.setDate(3, eD);
            sentenciaAltaEpisode.setString(4, e);
            sentenciaAltaEpisode.executeUpdate();


            ResultSet idsGenerados = sentenciaAltaEpisode.getGeneratedKeys();
            idsGenerados.next();
            int idE = idsGenerados.getInt(1);
            idsGenerados.close();
            sentenciaAltaEpisode.close();

            sentenciaComprobacion = conexion.prepareStatement(sql3);
            sentenciaComprobacion.setInt(1, idP);
            ResultSet resultado = sentenciaComprobacion.executeQuery();
            String idE2 = null;
            while (resultado.next())
            {
                idE2 = String.valueOf(resultado.getInt("idEpisode"));
                System.out.print(idE2);
            }

            if(idE2.equals("0"))
            {
                sentenciaAltaVisita = conexion.prepareStatement(sql2);
                sentenciaAltaVisita.setInt(1, idE);
                sentenciaAltaVisita.setInt(2, idP);
                sentenciaAltaVisita.executeUpdate();
            }
            else
            {
                sentenciaAltaVisita = conexion.prepareStatement(sql22);
                sentenciaAltaVisita.setInt(1, idP);

                Calendar calendario = GregorianCalendar.getInstance();
                java.util.Date fecha = calendario.getTime();
                SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
                String fechaActual = String.valueOf(fecha);

                Date fechaActualDate = null;
                try
                {
                    fechaActualDate = new Date(formatoDeFecha.parse(fechaActual).getTime());
                }
                catch (ParseException e1)
                {
                    e1.printStackTrace();
                }

                sentenciaAltaVisita.setDate(1, fechaActualDate);
                sentenciaAltaVisita.setInt(2, idE);
                sentenciaAltaVisita.setInt(3, medicConnected);
                sentenciaAltaVisita.setInt(4, idP);
                sentenciaAltaVisita.executeUpdate();
            }
            conexion.commit();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
    }

    public void deleteEpisode(int idE)
    {
        String sql = "DELETE FROM episodes WHERE id = ?";

        PreparedStatement sentencia = null;
        try
        {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, idE);
            sentencia.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public Object[] selectEpisode(int idE)
    {
        String sql = "SELECT descript, startDate, endDate, evolution FROM episodes WHERE id = ?";
        Object[] Row = new Object[4];
        try
        {
            PreparedStatement sentencia = null;

            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, idE);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next())
            {
                String desc = resultado.getString("descript");
                java.util.Date startDate = new Date(resultado.getDate("startDate").getTime());
                java.util.Date endDate = new Date(resultado.getDate("endDate").getTime());
                String evolution = resultado.getString("evolution");

                Row = new Object[] {desc, startDate, endDate, evolution};
            }
            return Row;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void updateEpisode(int idE, String desc, Date sD, Date eD, String evol)
    {
        String sql = "UPDATE episodes SET descript = ?, startDate = ?, endDate = ?, evolution = ?";

        PreparedStatement sentencia = null;

        try
        {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, desc);
            sentencia.setDate(2, sD);
            sentencia.setDate(3, eD);
            sentencia.setString(4, evol);
            sentencia.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
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