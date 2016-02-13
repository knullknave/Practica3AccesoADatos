package com.dam.practic2.View;

import com.dam.practic2.Controller.Controller;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * @author Daniel Cano Mainar
 * @version 1.0 10/11/2015
 *
 * Esta clase contiene el Main del programa y la ventana.
 *
 * Este programa permite suplir la falta de un entorno grafico para medicos donde ellos mismos puedan almacenar la información de sus
 * pacientes, modificara o incluso eliminarla si asi fuese necesario.
 *
 * El funcionamiento del programa es el siguiente:
 *
 * 1. Si existen medicos ya registrados, entonces podras logearte, sino tendras que registrar nuevos. Es decir, si en el jlist
 * que hay debajo de login no aparece nada, querra decir que no hay medicos.
 *
 * 2. Una vez logeado se habilitaran 2 pestañas: pestaña de pacientes y pestaña de historial
 *
 * 3. La pestaña de pacientes te permitira añadir, modificar, eliminar y ver pacientes. Estos pacientes solo seran incorporados al
 * medico que este logeado.
 *
 * 4. La pestaña de historial contiene los episodios, analisis, radiogragias y tratamientos farmacológicos para cada paciente.
 * A la izquierda aparecera un jlist con cada un de los pacientes, y a la deracha un jlist con cada una de las enfermedades,...
 *
 * A su vez, el programa incorpora un menu el la parte superior que permite guardar, guardar como, importar, exportar y cambiar el
 * archivo de configuracion.
 *
 * Por último, encima de cada jlist aparece una caja de texto que permite buscar por varios campos en el jlist que tengan debajo.
 */
public class Window
{
    public JTabbedPane tabbedPane1;
    public JPanel panel1;
    public JButton jbRegister;
    public JTextField tfUser;
    public JTextField tfPassword;
    public JButton jbEnter;
    public JTextField tfName;
    public JTextField tfSurname;
    public JTextField tfAddress;
    public JButton jbNewP;
    public JButton jbDelP;
    public JButton jbModP;
    public JButton jbSaveP;
    public JButton jbAddE;
    public JButton jbAddPh;
    public JButton jbModPh;
    public JButton jbDelPh;
    public JButton jbModE;
    public JButton jbDelE;
    public JButton jbAddA;
    public JButton jbModA;
    public JButton jbDelA;
    public JButton jbAddR;
    public JButton jbModR;
    public JButton jbDelR;
    public JDateChooser jdateChooserP;
    public JLabel jlMedic;
    public JButton jbDelM;
    public JButton jbModM;
    public JTextField tfSearchP;
    public JTabbedPane tabbedPaneHistory;
    public JTextField tfSearchP2;
    public JTextField tfSearchE;
    public JTextField tfSearchM;
    public JTextField tfSearchP3;
    public JTextField tfSearchP5;
    public JTextField tfSearchP4;
    public JTextField tfSearchA;
    public JTextField tfSearchR;
    public JTextField tfSearchPh;
    public JButton jbCancel;
    public JTable tableMedic;
    public JTable tablePatient1;
    public JTable tablePatient2;
    public JTable tableEpisodes;
    public JTable tablePatient3;
    public JTable tableAnalysis;
    public JTable tablePatient4;
    public JTable tableRadiography;
    public JTable tablePatient5;
    public JTable tablePharmacotherapy;
    public JTextField tfSearchFinal;
    public JList listSearchFinal;
    public JDateChooser jdateChooserP2;

    public static JMenuBar menuBar;
    public static JMenu menu;
    public static JMenuItem menuItem6, menuItem7, menuItem8;

    Window()
    {
        menuBar = new JMenuBar();
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription("Description for menu");
        menuBar.add(menu);

        menuItem6 = new JMenuItem("Exit", KeyEvent.VK_T);
        menuItem6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem6.getAccessibleContext().setAccessibleDescription("Description for sixth item");

        menuItem7 = new JMenuItem("Connect", KeyEvent.VK_T);
        menuItem7.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem7.getAccessibleContext().setAccessibleDescription("Description for seventh item");

        menuItem8 = new JMenuItem("Disconnect", KeyEvent.VK_T);
        menuItem8.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem8.getAccessibleContext().setAccessibleDescription("Description for eight item");

        menu.add(menuItem7);
        menuItem7.setMnemonic(KeyEvent.VK_D);
        menu.addSeparator();

        menu.add(menuItem8);
        menuItem8.setMnemonic(KeyEvent.VK_D);
        menu.addSeparator();

        menu.add(menuItem6);
        menuItem6.setMnemonic(KeyEvent.VK_D);

        Controller c = new Controller(Window.this);
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Window");
        frame.setContentPane(new Window().panel1);
        frame.setResizable(false);
        frame.setTitle("Project 1");
        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createUIComponents()
    {
        // TODO: place custom component creation code here
    }
}