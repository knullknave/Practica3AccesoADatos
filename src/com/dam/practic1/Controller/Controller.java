package com.dam.practic1.Controller;

import com.dam.practic1.Model.Methods.Methods;
import com.dam.practic1.Model.Objects.*;
import com.dam.practic1.View.JConnection;
import com.dam.practic1.View.Window;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

/**
 * @author Daniel Cano Mainar
 * @version 1.0 10/11/2015
 * Esta clase controla lo que le pueda pasar a la ventana. Incorpora modelo MVC (Model, view, controller)
 * En esta clase se controlan todos los eventos del programa así como tratamiento con cada uno de los elementos de la ventana.
 */
public class Controller implements ActionListener, ListSelectionListener, KeyListener
{
    private Window window;
    private Methods methods;
    private DefaultListModel<Medic> listMedicModel;
    private DefaultListModel<Patient> listPatientModel;
    private DefaultListModel<Patient> listPatientModel2;
    private DefaultListModel<Patient> listPatientModel3;
    private DefaultListModel<Patient> listPatientModel4;
    private DefaultListModel<Patient> listPatientModel5;
    private DefaultListModel<Episodes> listEpisodesModel;
    private DefaultListModel<Analysis> listAnalysisModel;
    private DefaultListModel<Radiography> listRadiographyModel;
    private DefaultListModel<Pharmacotherapy> listPharmacotherapyModel;
    private int GlobalmedicPosition;
    private int medicPosition;
    private int patientPosition;
    private int patientPosition2;
    private int patientPosition3;
    private int patientPosition4;
    private int patientPosition5;
    private int episodePosition;
    private int analysisPosition;
    private int radiographyPosition;
    private int pharmacotherapyPosition;
    private JConnection c;

    /**
     * Este es el constructor de la clase. Aqui se implementan todos los listener de la ventana
     * @param w
     */
    public Controller(Window w)
    {
        this.window = w;
        this.methods = new Methods(Controller.this, GlobalmedicPosition);

        //TODO desactivar todas las pestañas hasta que login con med

        //TODO DESACTIVAR PESTAÑAS MENOS LA DE MEDICO
        //UNA VEZ ESTE LOGUEADO, ACTIVAR LAS DE PACIENTE( EN CASO DE NO EXISTIR NINGUNO, DESACTIVAR LA DE HISTORIAL)
        //EN EL MOMENTO QUE EXISTE MÍNIMO UN PACIENTE, ACTIVAR HISTORIAL
        //EN PACIENTE SE RELLENARAN LOS DATOS DE UN PACIENTE
        //EN HISTORIAL SE VISUALIZARAN TODOS SUS PROBLEMAS, Y SE PODRAN RELLENAR MODIFICAR Y BORRAR
        //ES MÁS, SE PODRAN VER DETALLADAMENTE CADA UNO DE ELLOS Y SI ASÍ LO DESEA, SE PODRA CREAR UN PDF CON ELLO

        //LISTENER BOTONES
        this.window.jbNewP.addActionListener(this);
        this.window.jbModP.addActionListener(this);
        this.window.jbSaveP.addActionListener(this);
        this.window.jbDelP.addActionListener(this);
        this.window.jbEnter.addActionListener(this);
        this.window.jbRegister.addActionListener(this);
        this.window.jbAddE.addActionListener(this);
        this.window.jbSeeE.addActionListener(this);
        this.window.jbModE.addActionListener(this);
        this.window.jbDelE.addActionListener(this);
        this.window.jbAddA.addActionListener(this);
        this.window.jbSeeA.addActionListener(this);
        this.window.jbModA.addActionListener(this);
        this.window.jbDelA.addActionListener(this);
        this.window.jbAddR.addActionListener(this);
        this.window.jbSeeR.addActionListener(this);
        this.window.jbModR.addActionListener(this);
        this.window.jbDelR.addActionListener(this);
        this.window.jbAddPh.addActionListener(this);
        this.window.jbSeePh.addActionListener(this);
        this.window.jbModPh.addActionListener(this);
        this.window.jbDelPh.addActionListener(this);
        this.window.jbModM.addActionListener(this);
        this.window.jbDelM.addActionListener(this);
        this.window.jbCancel.addActionListener(this);

        this.window.menuItem.addActionListener(this);
        this.window.menuItem2.addActionListener(this);
        this.window.menuItem3.addActionListener(this);
        this.window.menuItem4.addActionListener(this);
        this.window.menuItem5.addActionListener(this);
        this.window.menuItem6.addActionListener(this);
        this.window.menuItem7.addActionListener(this);
        this.window.menuItem8.addActionListener(this);

        this.window.listMed.addListSelectionListener(this);

        this.listMedicModel = new DefaultListModel<Medic>();
        this.window.listMed.setModel(this.listMedicModel);

        this.listPatientModel = new DefaultListModel<Patient>();
        this.window.listPatients1.setModel(this.listPatientModel);
        this.listPatientModel2 = new DefaultListModel<Patient>();
        this.window.listPatients2.setModel(this.listPatientModel2);
        this.listPatientModel3 = new DefaultListModel<Patient>();
        this.window.listPatients3.setModel(this.listPatientModel3);
        this.listPatientModel4 = new DefaultListModel<Patient>();
        this.window.listPatients4.setModel(this.listPatientModel4);
        this.listPatientModel5 = new DefaultListModel<Patient>();
        this.window.listPatients5.setModel(this.listPatientModel5);

        this.window.listPatients1.addListSelectionListener(this);
        this.window.listPatients2.addListSelectionListener(this);
        this.window.listPatients3.addListSelectionListener(this);
        this.window.listPatients4.addListSelectionListener(this);
        this.window.listPatients5.addListSelectionListener(this);

        this.listEpisodesModel = new DefaultListModel<Episodes>();
        this.listAnalysisModel = new DefaultListModel<Analysis>();
        this.listRadiographyModel = new DefaultListModel<Radiography>();
        this.listPharmacotherapyModel = new DefaultListModel<Pharmacotherapy>();

        this.window.listEpisodes.setModel(this.listEpisodesModel);
        this.window.listAnalysis.setModel(this.listAnalysisModel);
        this.window.listRadiography.setModel(this.listRadiographyModel);
        this.window.listPharmacotherapy.setModel(this.listPharmacotherapyModel);

        this.window.listEpisodes.addListSelectionListener(this);
        this.window.listAnalysis.addListSelectionListener(this);
        this.window.listRadiography.addListSelectionListener(this);
        this.window.listPharmacotherapy.addListSelectionListener(this);

        this.window.tfSearchM.addKeyListener(this);
        this.window.tfSearchP.addKeyListener(this);
        this.window.tfSearchP2.addKeyListener(this);
        this.window.tfSearchP3.addKeyListener(this);
        this.window.tfSearchP4.addKeyListener(this);
        this.window.tfSearchP5.addKeyListener(this);
        this.window.tfSearchE.addKeyListener(this);
        this.window.tfSearchA.addKeyListener(this);
        this.window.tfSearchR.addKeyListener(this);
        this.window.tfSearchPh.addKeyListener(this);

        this.GlobalmedicPosition = 0;

        loadMedics();

        disableTabbedPane();
        disablePatientButtons();
        disableMedicButtons();
        disableAnalysisButtons();
        disableEpisodesButtons();
        disablePharmacotherapyButtons();
        disableRadiographyButtons();

        dishableALL();
    }

    /**
     *
     * @param e
     *
     * Este método se encarga de controlar los eventos de boton y menu.
     * Estos eventos van desde añadir paciente o eliminarlo, hasta Logearse con un medico en cuestion.
     *
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();

        if(source.getClass() == JButton.class)
        {
            String actionCommand = ((JButton) e.getSource()).getActionCommand();
            switch (actionCommand)
            {
                case "New Patient":
                    if(window.tfName.getText().equals("") || window.tfSurname.getText().equals("") || window.tfAddress.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(null, "Please, fill each field", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    {
                        methods.registerPatient(window.tfName.getText(), window.tfSurname.getText(), window.jdateChooserP.getDate(), window.tfAddress.getText(), this.GlobalmedicPosition);
                        JOptionPane.showMessageDialog(null, "Correct", "Correct", JOptionPane.INFORMATION_MESSAGE);
                        this.methods.writeFile();
                        loadPatients();
                        loadPatients2();
                        loadPatients3();
                        loadPatients4();
                        loadPatients5();
                        clean();
                    }
                    break;
                case "Modify Patient":
                    this.window.jbSaveP.setEnabled(true);
                    break;
                case "Cancel Modification":
                    this.window.listPatients1.clearSelection();
                    clean();
                    disablePatientButtons();
                    break;
                case "Save Patient":
                    if(window.tfName.getText().equals("") || window.tfSurname.getText().equals("") || window.tfAddress.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(null, "Please, fill each field", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    {
                        methods.updatePatient(window.tfName.getText(), window.tfSurname.getText(), window.jdateChooserP.getDate(), window.tfAddress.getText(), this.GlobalmedicPosition, this.patientPosition);
                        JOptionPane.showMessageDialog(null, "Correct", "Correct", JOptionPane.INFORMATION_MESSAGE);
                        this.methods.writeFile();
                        loadPatients();
                        loadPatients();
                        loadPatients2();
                        loadPatients3();
                        loadPatients4();
                        loadPatients5();
                        clean();
                    }

                    this.window.jbSaveP.setEnabled(false);
                    loadPatients();
                    loadPatients();
                    loadPatients2();
                    loadPatients3();
                    loadPatients4();
                    loadPatients5();
                    disablePatientButtons();
                    break;
                case "Delete Patient":
                    int row = this.window.listPatients1.getSelectedIndex();
                    if(row != -1)
                    {
                        int selection = JOptionPane.showConfirmDialog(null, "Are you sure?", "Delete", JOptionPane.YES_NO_OPTION);
                        this.window.listPatients1.clearSelection();

                        if(selection == 0)
                        {
                            this.methods.removePatient(this.GlobalmedicPosition, row);
                            this.methods.writeFile();
                            loadPatients();
                            loadPatients2();
                            loadPatients3();
                            loadPatients4();
                            loadPatients5();
                            clean();
                        }
                    }
                    disablePatientButtons();
                    break;
                case "Modify Medic":
                    JTextField fieldUser = new JTextField();
                    JTextField fieldPassword = new JTextField();
                    Object[] check = {"User", fieldUser, "Password", fieldPassword};
                    int op = JOptionPane.showConfirmDialog(null, check, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);
                    int sw1 = 0;

                    if(op == JOptionPane.CANCEL_OPTION || op == JOptionPane.CLOSED_OPTION)
                    {
                        sw1 = 1;
                    }
                    else
                    {
                        if (fieldUser.getText().equals("") || fieldPassword.getText().equals("") || (!fieldUser.getText().equals(this.methods.medicList.get(this.medicPosition).getUser()) || !fieldPassword.getText().equals(this.methods.medicList.get(this.medicPosition).getPassword())))
                        {
                            JOptionPane.showMessageDialog(null, "You can't modify this user", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Successul", "Successful", JOptionPane.INFORMATION_MESSAGE);
                            sw1 = 1;
                            System.out.println(this.medicPosition);
                            JTextField field1 = new JTextField();
                            field1.setText(this.methods.medicList.get(this.medicPosition).getName());
                            JTextField field2 = new JTextField();
                            field2.setText(this.methods.medicList.get(this.medicPosition).getSurname());
                            JTextField field3 = new JTextField();
                            field3.setText(this.methods.medicList.get(this.medicPosition).getMedicalSpeciality());
                            JTextField field4 = new JTextField();
                            field4.setText(this.methods.medicList.get(this.medicPosition).getCollegiateNumber());
                            JTextField field5 = new JTextField();
                            field5.setText(this.methods.medicList.get(this.medicPosition).getAddress());
                            JDateChooser field6 = new JDateChooser();
                            field6.setDate(this.methods.medicList.get(this.medicPosition).getBirthDate());
                            JTextField field7 = new JTextField();
                            field7.setText(this.methods.medicList.get(this.medicPosition).getUser());
                            JTextField field8 = new JTextField();
                            field8.setText(this.methods.medicList.get(this.medicPosition).getPassword());

                            int sw = 0;
                            int option = 0;

                            while (sw == 0)
                            {
                                Object[] message = {"Name", field1, "Surname", field2, "Medical Speciality", field3, "Collegiate Number", field4,
                                        "Address", field5, "Birthday", field6, "User", field7, "Password", field8};
                                option = JOptionPane.showConfirmDialog(null, message, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);

                                if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION)
                                {
                                    sw = 1;
                                }
                                else
                                {
                                    if (field1.getText().equals("") || field2.getText().equals("") || field3.getText().equals("") || field4.getText().equals("")
                                            || field5.getText().equals("") || field7.getText().equals("") || field8.getText().equals(""))
                                    {
                                        JOptionPane.showMessageDialog(null, "Please, fill each field", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                    else
                                    {
                                        JOptionPane.showMessageDialog(null, "Successul", "Successful", JOptionPane.INFORMATION_MESSAGE);
                                        sw = 1;

                                        methods.updateMedic(field1.getText(), field2.getText(), field3.getText(), field4.getText(), field5.getText(), field6.getDate(), field7.getText(), field8.getText(), medicPosition);
                                        this.methods.writeFile();
                                        loadMedics();
                                        disableMedicButtons();
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "Delete Medic":
                    fieldUser = new JTextField();
                    fieldPassword = new JTextField();
                    Object[] check2 = {"User", fieldUser, "Password", fieldPassword};
                    int sw = 0;

                    op = JOptionPane.showConfirmDialog(null, check2, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);

                    if (op == JOptionPane.CANCEL_OPTION || op == JOptionPane.CLOSED_OPTION)
                    {
                        sw = 1;
                    }
                    else
                    {
                        if(fieldUser.getText().equals("") || fieldPassword.getText().equals(""))
                        {
                            JOptionPane.showMessageDialog(null, "Please, fill each field", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else
                        {
                            int j = 0;
                            for (j = 0; j < methods.medicList.size(); j++)
                            {
                                String user = this.methods.medicList.get(j).getUser();
                                String password = this.methods.medicList.get(j).getPassword();

                                if (!user.equals(fieldUser.getText()) || !password.equals(fieldPassword.getText()))
                                {
                                    sw = 1;
                                }
                                else
                                {
                                    sw = 0;
                                    break;
                                }
                            }
                            if (sw == 0)
                            {
                                sw = 1;
                                row = this.window.listMed.getSelectedIndex();
                                if (row != -1)
                                {
                                    int selection = JOptionPane.showConfirmDialog(null, "Are you sure?", "Delete", JOptionPane.YES_NO_OPTION);
                                    this.window.listMed.clearSelection();

                                    if (selection == 0)
                                    {
                                        JOptionPane.showMessageDialog(null, "Successful", "Successful", JOptionPane.INFORMATION_MESSAGE);
                                        this.methods.removeMedic(row);
                                        this.methods.writeFile();

                                        loadMedics();
                                    }

                                    disableMedicButtons();
                                }
                            }
                            else
                                JOptionPane.showMessageDialog(null, "the User or Password is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    loadMedics();
                    if(this.methods.medicList.size() == 0)
                    {
                        disableTabbedPane();
                        this.window.jlMedic.setText("You need to log in");
                        this.window.tfUser.setText("");
                        this.window.tfPassword.setText("");
                    }

                    break;
                case "ENTER":
                    loadMedics();
                    if(window.tfUser.getText().equals("") || window.tfPassword.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(null, "Please, fill each field", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    {
                        if(methods.medicList.size() == 0)
                        {
                            JOptionPane.showMessageDialog(null, "There is no medic registered yet", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else
                        {
                            sw = 0;
                            int j = 0;
                            for (j = 0; j < methods.medicList.size(); j++)
                            {
                                String user = this.methods.medicList.get(j).getUser();
                                String password = this.methods.medicList.get(j).getPassword();

                                if (!user.equals(window.tfUser.getText()) || !password.equals(window.tfPassword.getText()))
                                    sw = 1;
                                else
                                {
                                    sw = 0;
                                    break;
                                }
                            }

                            if (sw == 0)
                            {
                                this.GlobalmedicPosition = j;

                                JOptionPane.showMessageDialog(null, "Correct", "Correct", JOptionPane.INFORMATION_MESSAGE);
                                window.jlMedic.setText("Collegiate Number: " + methods.medicList.get(this.GlobalmedicPosition).getCollegiateNumber());

                                enableTabbedPane();
                                loadPatients();
                                loadPatients2();
                                loadPatients3();
                                loadPatients4();
                                loadPatients5();

                                listEpisodesModel.removeAllElements();
                                listAnalysisModel.removeAllElements();
                                listPharmacotherapyModel.removeAllElements();
                                listRadiographyModel.removeAllElements();

                                if(this.listEpisodesModel.size() > 0)
                                    this.listEpisodesModel.removeAllElements();
                                if(this.listAnalysisModel.size() > 0)
                                    this.listAnalysisModel.removeAllElements();
                                if(this.listRadiographyModel.size() > 0)
                                    this.listRadiographyModel.removeAllElements();
                                if(this.listPharmacotherapyModel.size() > 0)
                                    this.listPharmacotherapyModel.removeAllElements();
                            }
                            else
                                JOptionPane.showMessageDialog(null, "the User or Password is incorrect", "Error", JOptionPane.ERROR_MESSAGE);

                            this.window.tfUser.setText("");
                            this.window.tfPassword.setText("");
                        }
                    }
                    break;
                case "REGISTER":
                    JTextField field1 = new JTextField();
                    JTextField field2 = new JTextField();
                    JTextField field3 = new JTextField();
                    JTextField field4 = new JTextField();
                    field4.setText(this.methods.RandomCias());
                    JTextField field5 = new JTextField();
                    JDateChooser field6 = new JDateChooser();
                    JTextField field7 = new JTextField();
                    JTextField field8 = new JTextField();

                    sw = 0;
                    int option = 0;

                    while (sw == 0)
                    {
                        Object[] message = {"Name", field1, "Surname", field2, "Medical Speciality", field3, "Collegiate Number", field4,
                                "Address", field5, "Birthday", field6, "User", field7, "Password", field8};
                        option = JOptionPane.showConfirmDialog(null, message, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);

                        if(option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION)
                        {
                            sw = 1;
                        }
                        else
                        {
                            if (field1.getText().equals("") || field2.getText().equals("") || field3.getText().equals("") || field4.getText().equals("")
                                    || field5.getText().equals("") || field7.getText().equals("") || field8.getText().equals(""))
                            {
                                JOptionPane.showMessageDialog(null, "Please, fill each field", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            else
                            {
                                int swm = 0;
                                for(int h=0; h<this.methods.medicList.size(); h++)
                                {
                                    if(field7.getText().equals(this.methods.medicList.get(h).getUser()))
                                    {
                                        swm = 1;
                                        break;
                                    }
                                }
                                if(swm == 1)
                                {
                                    sw = 0;
                                    JOptionPane.showMessageDialog(null, "This user already exists", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(null, "Successul", "Successful", JOptionPane.INFORMATION_MESSAGE);
                                    sw = 1;

                                    methods.registerMedic(field1.getText(), field2.getText(), field3.getText(), field4.getText(), field5.getText(), field6.getDate(), field7.getText(), field8.getText());
                                    this.methods.writeFile();
                                    loadMedics();
                                }
                            }
                        }
                    }
                    break;
                case "See Episodes":
                    JLabel f1 = new JLabel();
                    f1.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition2).getHistory().getEpisodeList().get(this.episodePosition).getDescription());
                    JLabel f2 = new JLabel();
                    f2.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition2).getHistory().getEpisodeList().get(this.episodePosition).getObservation());
                    JLabel f3 = new JLabel();
                    f3.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition2).getHistory().getEpisodeList().get(this.episodePosition).getEvolution());
                    JLabel f4 = new JLabel();
                    f4.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition2).getHistory().getEpisodeList().get(this.episodePosition).getStartDate().toString());
                    JLabel f5 = new JLabel();
                    f5.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition2).getHistory().getEpisodeList().get(this.episodePosition).getEndDate().toString());
                    JLabel f6 = new JLabel();
                    f6.setText(String.valueOf(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition2).getHistory().getEpisodeList().get(this.episodePosition).getNumberRepetitions()));
                    JLabel f7 = new JLabel();
                    f7.setText(String.valueOf(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition2).getHistory().getEpisodeList().get(this.episodePosition).isCauseOffWork()));
                    JLabel f8 = new JLabel();
                    f8.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition2).getHistory().getEpisodeList().get(this.episodePosition).getDiseases());
                    Object[] episodes = {"Description", f1, "Observation", f2, "Evolution", f3, "Start Date", f4,
                            "End Date", f5, "Number of Repetitions", f6, "Cause Off Work", f7, "Diseases", f8};
                    int see = JOptionPane.showConfirmDialog(null, episodes, "Episodes from: " + this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition2).getHistory().getCiasNumber(), JOptionPane.OK_OPTION);

                    loadEpisodes();
                    loadPatients2();
                    break;
                case "Add Episodes":
                    field1 = new JTextField();
                    field2 = new JTextField();
                    field3 = new JTextField();
                    JDateChooser fieldd4 = new JDateChooser();
                    JDateChooser fieldd5 = new JDateChooser();
                    String[] dates = {"Yes", "No"};
                    JTextField fieldd6 = new JTextField();
                    JComboBox fieldd7 = new JComboBox(dates);
                    field8 = new JTextField();

                    sw = 0;
                    option = 0;

                    while (sw == 0)
                    {
                        Object[] message = {"Description", field1, "Observation", field2, "Evolution", field3, "Start Date", fieldd4,
                                "End Date", fieldd5, "Number of Repetitions", fieldd6, "Cause Off Work", fieldd7, "Diseases", field8};
                        option = JOptionPane.showConfirmDialog(null, message, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);

                        if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION)
                        {
                            sw = 1;
                        }
                        else
                        {
                            if (field1.getText().equals("") || field2.getText().equals("") || field3.getText().equals("") || fieldd4.getDate().equals("")
                                    || fieldd5.getDate().equals("") || fieldd6.getText().equals("") || field8.getText().equals(""))
                            {
                                JOptionPane.showMessageDialog(null, "Please, fill each field", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            else
                            {
                                if(this.methods.checkNumber(fieldd6.getText().toString()) == false)
                                {
                                    JOptionPane.showMessageDialog(null, "Please, introduce a number in Number of Repetitions", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(null, "Successul", "Successful", JOptionPane.INFORMATION_MESSAGE);
                                    sw = 1;

                                    methods.registerEpisode(field1.getText(), field2.getText(), field3.getText(), fieldd4.getDate(), fieldd5.getDate(), Integer.parseInt(fieldd6.getText().toString()), fieldd7.getSelectedItem().toString(), field8.getText(), this.GlobalmedicPosition, this.patientPosition2);
                                    this.methods.writeFile();
                                    loadEpisodes();
                                    loadPatients();
                                }
                            }
                        }
                    }

                    loadPatients2();
                    loadEpisodes();
                    break;
                case "Modify Episodes":
                    field1 = new JTextField();
                    field1.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition2).getHistory().getEpisodeList().get(this.episodePosition).getDescription());
                    field2 = new JTextField();
                    field2.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition2).getHistory().getEpisodeList().get(this.episodePosition).getObservation());
                    field3 = new JTextField();
                    field3.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition2).getHistory().getEpisodeList().get(this.episodePosition).getEvolution());
                    fieldd4 = new JDateChooser();
                    fieldd4.setDate(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition2).getHistory().getEpisodeList().get(this.episodePosition).getStartDate());
                    fieldd5 = new JDateChooser();
                    fieldd5.setDate(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition2).getHistory().getEpisodeList().get(this.episodePosition).getEndDate());
                    String[] dattes = {"Yes", "No"};
                    fieldd6 = new JTextField();
                    fieldd6.setText(String.valueOf(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition2).getHistory().getEpisodeList().get(this.episodePosition).getNumberRepetitions()));
                    fieldd7 = new JComboBox(dattes);
                    if(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition2).getHistory().getEpisodeList().get(this.episodePosition).isCauseOffWork() == true)
                        fieldd7.setSelectedIndex(0);
                    else
                        fieldd7.setSelectedIndex(1);
                    field8 = new JTextField();
                    field8.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition2).getHistory().getEpisodeList().get(this.episodePosition).getDiseases());

                    sw = 0;
                    option = 0;

                    Object[] message = {"Description", field1, "Observation", field2, "Evolution", field3, "Start Date", fieldd4,
                            "End Date", fieldd5, "Number of Repetitions", fieldd6, "Cause Off Work", fieldd7, "Diseases", field8};
                    option = JOptionPane.showConfirmDialog(null, message, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION)
                    {
                        sw = 1;
                    }
                    else
                    {
                        if (field1.getText().equals("") || field2.getText().equals("") || field3.getText().equals("") || fieldd4.getDate().equals("")
                                || fieldd5.getDate().equals("") || fieldd6.getText().equals("") || field8.getText().equals(""))
                        {
                            JOptionPane.showMessageDialog(null, "Please, fill each field", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else
                        {
                            if (this.methods.checkNumber(fieldd6.getText().toString()) == false)
                            {
                                JOptionPane.showMessageDialog(null, "Please, introduce a number in Number of Repetitions", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Successul", "Successful", JOptionPane.INFORMATION_MESSAGE);
                                this.methods.updateEpisode(field1.getText(), field2.getText(), field3.getText(), fieldd4.getDate(), fieldd5.getDate(), Integer.parseInt(fieldd6.getText()), fieldd7.getSelectedItem().toString(), field8.getText(), this.GlobalmedicPosition, this.patientPosition2, this.episodePosition);
                                this.methods.writeFile();
                                sw = 1;
                            }
                        }
                    }

                    loadPatients2();
                    loadEpisodes();
                    break;
                case "Delete Episodes":
                    op = JOptionPane.showConfirmDialog(null, "Are you sure?", "Enter all your values", JOptionPane.OK_CANCEL_OPTION);

                    if (op == JOptionPane.OK_OPTION)
                    {
                        JOptionPane.showMessageDialog(null, "Successul", "Successful", JOptionPane.INFORMATION_MESSAGE);
                        this.methods.removeEpisode(this.GlobalmedicPosition, this.patientPosition2, this.episodePosition);
                        this.methods.writeFile();
                    }

                    loadEpisodes();
                    loadPatients2();
                    break;
                case "Add Analysis":
                    field1 = new JTextField();
                    field2 = new JTextField();
                    JDateChooser fieldd3 = new JDateChooser();
                    fieldd4 = new JDateChooser();
                    field5 = new JTextField();
                    field6 = new JDateChooser();

                    sw = 0;
                    option = 0;

                    while (sw == 0)
                    {
                        Object[] analysisMessage = {"Inform", field1, "Hospital", field2, "Reception Date", fieldd3, "Analysis Date", fieldd4,
                                "Analysis Type", field5, "Inform Date", field6};
                        option = JOptionPane.showConfirmDialog(null, analysisMessage, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);

                        if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION)
                        {
                            sw = 1;
                        }
                        else
                        {
                            if (field1.getText().equals("") || field2.getText().equals("") || fieldd3.getDate().equals("") || fieldd4.getDate().equals("")
                                    || field5.getText().equals("") || field6.getDate().equals(""))
                            {
                                JOptionPane.showMessageDialog(null, "Please, fill each field", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Successul", "Successful", JOptionPane.INFORMATION_MESSAGE);
                                this.methods.registerAnalysis(field1.getText(), field2.getText(), fieldd3.getDate(), fieldd4.getDate(), field5.getText(), field6.getDate(), this.GlobalmedicPosition, this.patientPosition3);
                                this.methods.writeFile();
                                sw = 1;
                            }
                        }
                    }

                    loadAnalysis();
                    loadPatients3();
                    break;
                case "See Analysis":
                    f1 = new JLabel();
                    f1.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition3).getHistory().getAnalysisList().get(this.analysisPosition).getReport());
                    f2 = new JLabel();
                    f2.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition3).getHistory().getAnalysisList().get(this.analysisPosition).getHospital());
                    f3 = new JLabel();
                    f3.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition3).getHistory().getAnalysisList().get(this.analysisPosition).getReceptionDate().toString());
                    f4 = new JLabel();
                    f4.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition3).getHistory().getAnalysisList().get(this.analysisPosition).getAnalysisDate().toString());
                    f5 = new JLabel();
                    f5.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition3).getHistory().getAnalysisList().get(this.analysisPosition).getAnalysisType());
                    f6 = new JLabel();
                    f6.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition3).getHistory().getAnalysisList().get(this.analysisPosition).getReportDate().toString());

                    Object[] analysis = {"Inform", f1, "Hospital", f2, "Reception Date", f3, "Analysis Date", f4,
                            "Analysis Type", f5, "Inform Date", f6};
                    int see2 = JOptionPane.showConfirmDialog(null, analysis, "Analysis from: " + this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition3).getHistory().getCiasNumber(), JOptionPane.OK_OPTION);

                    loadAnalysis();
                    loadPatients3();
                    break;
                case "Modify Analysis":
                    field1 = new JTextField();
                    field1.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition3).getHistory().getAnalysisList().get(this.analysisPosition).getReport());
                    field2 = new JTextField();
                    field2.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition3).getHistory().getAnalysisList().get(this.analysisPosition).getHospital());
                    fieldd3 = new JDateChooser();
                    fieldd3.setDate(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition3).getHistory().getAnalysisList().get(this.analysisPosition).getReceptionDate());
                    fieldd4 = new JDateChooser();
                    fieldd4.setDate(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition3).getHistory().getAnalysisList().get(this.analysisPosition).getAnalysisDate());
                    field5 = new JTextField();
                    field5.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition3).getHistory().getAnalysisList().get(this.analysisPosition).getAnalysisType());
                    field6 = new JDateChooser();
                    field6.setDate(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition3).getHistory().getAnalysisList().get(this.analysisPosition).getReceptionDate());

                    sw = 0;
                    option = 0;

                    while (sw == 0)
                    {
                        Object[] analysisMessage = {"Inform", field1, "Hospital", field2, "Reception Date", fieldd3, "Analysis Date", fieldd4,
                                "Analysis Type", field5, "Inform Date", field6};
                        option = JOptionPane.showConfirmDialog(null, analysisMessage, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);

                        if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION)
                        {
                            sw = 1;
                        }
                        else
                        {
                            if (field1.getText().equals("") || field2.getText().equals("") || fieldd3.getDate().equals("") || fieldd4.getDate().equals("")
                                    || field5.getText().equals("") || field6.getDate().equals(""))
                            {
                                JOptionPane.showMessageDialog(null, "Please, fill each field", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Successul", "Successful", JOptionPane.INFORMATION_MESSAGE);
                                this.methods.updateAnalysis(field1.getText(), field2.getText(), fieldd3.getDate(), fieldd4.getDate(), field5.getText(), field6.getDate(), this.GlobalmedicPosition, this.patientPosition3, this.analysisPosition);
                                this.methods.writeFile();
                                sw = 1;
                            }
                        }
                    }

                    loadAnalysis();
                    loadPatients3();
                    break;
                case "Delete Analysis":
                    op = JOptionPane.showConfirmDialog(null, "Are you sure?", "Enter all your values", JOptionPane.OK_CANCEL_OPTION);

                    if (op == JOptionPane.OK_OPTION)
                    {
                        JOptionPane.showMessageDialog(null, "Successul", "Successful", JOptionPane.INFORMATION_MESSAGE);
                        this.methods.removeAnalysis(this.GlobalmedicPosition, this.patientPosition3, this.analysisPosition);
                        this.methods.writeFile();
                    }

                    loadPatients3();
                    loadAnalysis();
                    break;
                case "Add Radiography":
                    JDateChooser fieldd1 = new JDateChooser();
                    JDateChooser fieldd2 = new JDateChooser();
                    fieldd3 = new JDateChooser();
                    field4 = new JTextField();
                    field5 = new JTextField();
                    fieldd6 = new JTextField();
                    field7 = new JTextField();
                    sw = 0;
                    option = 0;

                    while (sw == 0)
                    {
                        Object[] radiographyMessage = {"Report Date", fieldd1, "Reception Date", fieldd2, "Radiography Date", fieldd3, "Study", field4,
                                "Control Monitored", field5, "Report", fieldd6, "Performance", field7};
                        option = JOptionPane.showConfirmDialog(null, radiographyMessage, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);

                        if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION)
                        {
                            sw = 1;
                        }
                        else
                        {
                            if (field4.getText().equals("") || field5.getText().equals("") || fieldd6.getText().equals("") || field7.getText().equals(""))
                            {
                                JOptionPane.showMessageDialog(null, "Please, fill each field", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Successul", "Successful", JOptionPane.INFORMATION_MESSAGE);
                                this.methods.registerRadiography(fieldd1.getDate(), fieldd2.getDate(), fieldd3.getDate(), field4.getText(), field5.getText(), fieldd6.getText(), field7.getText(), this.GlobalmedicPosition, this.patientPosition4);
                                this.methods.writeFile();
                                sw = 1;
                            }
                        }
                    }

                    loadRadiography();
                    break;
                case "See Radiography":
                    f1 = new JLabel();
                    f1.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition4).getHistory().getRadiographyList().get(this.radiographyPosition).getReportDate().toString());
                    f2 = new JLabel();
                    f2.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition4).getHistory().getRadiographyList().get(this.radiographyPosition).getReceptionDate().toString());
                    f3 = new JLabel();
                    f3.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition4).getHistory().getRadiographyList().get(this.radiographyPosition).getRadiographyDate().toString());
                    f4 = new JLabel();
                    f4.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition4).getHistory().getRadiographyList().get(this.radiographyPosition).getStudy());
                    f5 = new JLabel();
                    f5.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition4).getHistory().getRadiographyList().get(this.radiographyPosition).getControlMonitored());
                    f6 = new JLabel();
                    f6.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition4).getHistory().getRadiographyList().get(this.radiographyPosition).getInform());
                    f7 = new JLabel();
                    f7.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition4).getHistory().getRadiographyList().get(this.radiographyPosition).getPerformance());

                    Object[] radiographyMessage = {"Report Date", f1, "Reception Date", f2, "Radiography Date", f3, "Study", f4,
                            "Control Monitored", f5, "Report", f6, "Performance", f7};
                    option = JOptionPane.showConfirmDialog(null, radiographyMessage, "Enter all your values", JOptionPane.OK_OPTION);

                    loadRadiography();
                    loadPatients4();
                    break;
                case "Modify Radiography":
                    fieldd1 = new JDateChooser();
                    fieldd1.setDate(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition4).getHistory().getRadiographyList().get(this.radiographyPosition).getReportDate());
                    fieldd2 = new JDateChooser();
                    fieldd2.setDate(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition4).getHistory().getRadiographyList().get(this.radiographyPosition).getReceptionDate());
                    fieldd3 = new JDateChooser();
                    fieldd3.setDate(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition4).getHistory().getRadiographyList().get(this.radiographyPosition).getRadiographyDate());
                    field4 = new JTextField();
                    field4.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition4).getHistory().getRadiographyList().get(this.radiographyPosition).getStudy());
                    field5 = new JTextField();
                    field5.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition4).getHistory().getRadiographyList().get(this.radiographyPosition).getControlMonitored());
                    fieldd6 = new JTextField();
                    fieldd6.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition4).getHistory().getRadiographyList().get(this.radiographyPosition).getInform());
                    field7 = new JTextField();
                    field7.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition4).getHistory().getRadiographyList().get(this.radiographyPosition).getPerformance());
                    sw = 0;
                    option = 0;

                    while (sw == 0)
                    {
                        Object[] radioMessage = {"Report Date", fieldd1, "Reception Date", fieldd2, "Radiography Date", fieldd3, "Study", field4,
                                "Control Monitored", field5, "Report", fieldd6, "Performance", field7};
                        option = JOptionPane.showConfirmDialog(null, radioMessage, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);

                        if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION)
                        {
                            sw = 1;
                        }
                        else
                        {
                            if (field4.getText().equals("")
                                    || field5.getText().equals("") || fieldd6.getText().equals("") || field7.getText().equals(""))
                            {
                                JOptionPane.showMessageDialog(null, "Please, fill each field", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Successul", "Successful", JOptionPane.INFORMATION_MESSAGE);
                                this.methods.updateRadiography(fieldd1.getDate(), fieldd2.getDate(), fieldd3.getDate(), field4.getText(), field5.getText(), fieldd6.getText(), field7.getText(), this.GlobalmedicPosition, this.patientPosition4, this.radiographyPosition);
                                this.methods.writeFile();
                                sw = 1;
                            }
                        }
                    }

                    loadRadiography();
                    break;
                case "Delete Radiography":
                    op = JOptionPane.showConfirmDialog(null, "Are you sure?", "Enter all your values", JOptionPane.OK_CANCEL_OPTION);

                    if (op == JOptionPane.OK_OPTION)
                    {
                        JOptionPane.showMessageDialog(null, "Successul", "Successful", JOptionPane.INFORMATION_MESSAGE);
                        this.methods.removeRadiography(this.GlobalmedicPosition, this.patientPosition4, this.radiographyPosition);
                        this.methods.writeFile();
                    }

                    loadPatients4();
                    loadRadiography();
                    break;
                case "Add Pharmacotherapy":
                    fieldd1 = new JDateChooser();
                    fieldd2 = new JDateChooser();
                    field3 = new JTextField();
                    field4 = new JTextField();
                    field5 = new JTextField();
                    //CHECK If IS NUMBER OR NOT
                    fieldd6 = new JTextField();
                    field7 = new JTextField();
                    field8 = new JTextField();
                    sw = 0;
                    option = 0;

                    while (sw == 0)
                    {
                        Object[] pharmacotherapyMessage = {"Start Date", fieldd1, "End Date", fieldd2, "Therapy Type", field3, "Dosage", field4,
                                "Description", field5, "Initial Weight", fieldd6, "Final Weight", field7, "Medicines", field8};
                        option = JOptionPane.showConfirmDialog(null, pharmacotherapyMessage, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);

                        if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION)
                        {
                            sw = 1;
                        }
                        else
                        {
                            if (field3.getText().equals("") || field4.getText().equals("") || field5.getText().equals("") || fieldd6.getText().equals("") || field7.getText().equals("") || field8.getText().equals(""))
                            {
                                JOptionPane.showMessageDialog(null, "Please, fill each field", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            else
                            {
                                if(this.methods.checkNumber(fieldd6.getText()) == false || this.methods.checkNumber(field7.getText()) == false)
                                {
                                    JOptionPane.showMessageDialog(null, "Please, introduce a number in Initial Weight and Final Weight", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(null, "Successul", "Successful", JOptionPane.INFORMATION_MESSAGE);
                                    this.methods.registerPharmacotherapy(fieldd1.getDate(), fieldd2.getDate(), field3.getText(), field4.getText(), field5.getText(), Float.parseFloat(fieldd6.getText().toString()), Float.parseFloat(field7.getText().toString()), field8.getText(), this.GlobalmedicPosition, this.patientPosition5);
                                    this.methods.writeFile();
                                    sw = 1;
                                }
                            }
                        }
                    }

                    loadPharmacotherapy();
                    break;
                case "See Pharmacotherapy":
                    f1 = new JLabel();
                    f1.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition5).getHistory().getPharmacotherapyList().get(this.pharmacotherapyPosition).getStartDate().toString());
                    f2 = new JLabel();
                    f2.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition5).getHistory().getPharmacotherapyList().get(this.pharmacotherapyPosition).getEndDate().toString());
                    f3 = new JLabel();
                    f3.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition5).getHistory().getPharmacotherapyList().get(this.pharmacotherapyPosition).getTherapyType());
                    f4 = new JLabel();
                    f4.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition5).getHistory().getPharmacotherapyList().get(this.pharmacotherapyPosition).getDosage());
                    f5 = new JLabel();
                    f5.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition5).getHistory().getPharmacotherapyList().get(this.pharmacotherapyPosition).getDescription());
                    f6 = new JLabel();
                    f6.setText(String.valueOf(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition5).getHistory().getPharmacotherapyList().get(this.pharmacotherapyPosition).getInitialWeight()));
                    f7 = new JLabel();
                    f7.setText(String.valueOf(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition5).getHistory().getPharmacotherapyList().get(this.pharmacotherapyPosition).getFinalWeight()));
                    f8 = new JLabel();
                    f8.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition5).getHistory().getPharmacotherapyList().get(this.pharmacotherapyPosition).getMedicines());

                    Object[] phMessage = {"Start Date", f1, "End Date", f2, "Therapy Type", f3, "Dosage", f4,
                            "Description", f5, "Initial Weight", f6, "Final Weight", f7, "Medicines", f8};
                    option = JOptionPane.showConfirmDialog(null, phMessage, "Enter all your values", JOptionPane.OK_OPTION);

                    loadPharmacotherapy();
                    loadPatients5();
                    break;
                case "Modify Pharmacotherapy":
                    fieldd1 = new JDateChooser();
                    fieldd1.setDate(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition5).getHistory().getPharmacotherapyList().get(this.pharmacotherapyPosition).getStartDate());
                    fieldd2 = new JDateChooser();
                    fieldd2.setDate(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition5).getHistory().getPharmacotherapyList().get(this.pharmacotherapyPosition).getEndDate());
                    field3 = new JTextField();
                    field3.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition5).getHistory().getPharmacotherapyList().get(this.pharmacotherapyPosition).getTherapyType());
                    field4 = new JTextField();
                    field4.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition5).getHistory().getPharmacotherapyList().get(this.pharmacotherapyPosition).getDosage());
                    field5 = new JTextField();
                    field5.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition5).getHistory().getPharmacotherapyList().get(this.pharmacotherapyPosition).getDescription());
                    fieldd6 = new JTextField();
                    fieldd6.setText(String.valueOf(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition5).getHistory().getPharmacotherapyList().get(this.pharmacotherapyPosition).getInitialWeight()));
                    field7 = new JTextField();
                    field7.setText(String.valueOf(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition5).getHistory().getPharmacotherapyList().get(this.pharmacotherapyPosition).getFinalWeight()));
                    field8 = new JTextField();
                    field8.setText(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition5).getHistory().getPharmacotherapyList().get(this.pharmacotherapyPosition).getMedicines());

                    sw = 0;
                    option = 0;

                    while (sw == 0)
                    {
                        Object[] pharmMessage = {"Start Date", fieldd1, "End Date", fieldd2, "Therapy Type", field3, "Dosage", field4,
                                "Description", field5, "Initial Weight", fieldd6, "Final Weight", field7, "Medicines", field8};
                        option = JOptionPane.showConfirmDialog(null, pharmMessage, "Enter all your values", JOptionPane.OK_OPTION);

                        if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION)
                        {
                            sw = 1;
                        }
                        else
                        {
                            if (field4.getText().equals("")
                                    || field5.getText().equals("") || fieldd6.getText().equals("") || field7.getText().equals(""))
                            {
                                JOptionPane.showMessageDialog(null, "Please, fill each field", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Successul", "Successful", JOptionPane.INFORMATION_MESSAGE);
                                this.methods.updatePharmacotherapy(fieldd1.getDate(), fieldd2.getDate(), field3.getText(), field4.getText(), field5.getText(), Float.parseFloat(fieldd6.getText().toString()), Float.parseFloat(field7.getText().toString()), field8.getText(), this.GlobalmedicPosition, this.patientPosition5, this.pharmacotherapyPosition);
                                this.methods.writeFile();
                                sw = 1;
                            }
                        }
                    }

                    loadPharmacotherapy();
                    break;
                case "Delete Pharmacotherapy":
                    op = JOptionPane.showConfirmDialog(null, "Are you sure?", "Enter all your values", JOptionPane.OK_CANCEL_OPTION);

                    if (op == JOptionPane.OK_OPTION)
                    {
                        JOptionPane.showMessageDialog(null, "Successul", "Successful", JOptionPane.INFORMATION_MESSAGE);
                        this.methods.removePharmacotherapy(this.GlobalmedicPosition, this.patientPosition5, this.pharmacotherapyPosition);
                        this.methods.writeFile();
                    }

                    loadPharmacotherapy();
                    loadPatients5();
                    break;
                default:
                    break;
            }
        }
        else
        {
            String actionCommand2 = ((JMenuItem) e.getSource()).getActionCommand();

            switch (actionCommand2)
            {
                case "Save":
                    this.methods.writeFile();
                    break;
                case "Save as":
                    JFileChooser filec = new JFileChooser();

                    filec.setCurrentDirectory(new File(System.getProperty("user.home")));
                    int result = filec.showOpenDialog(this.window.frame);
                    if(result == JFileChooser.APPROVE_OPTION)
                    {
                        File selectedFile = filec.getSelectedFile();
                         if(selectedFile.getAbsolutePath().endsWith(".dat"))
                            this.methods.writeFile(selectedFile.getAbsolutePath());
                        else
                            this.methods.writeFile(selectedFile.getAbsolutePath() + ".dat");
                    }
                    break;
                case "Import":
                    JFileChooser fc = new JFileChooser();

                    fc.setCurrentDirectory(new File(System.getProperty("user.home")));
                    result = fc.showOpenDialog(this.window.frame);
                    if(result == JFileChooser.APPROVE_OPTION)
                    {
                        File selectedFile = fc.getSelectedFile();
                        if (!selectedFile.equals(""))
                        {
                            if (selectedFile.getAbsolutePath().endsWith(".xml"))
                                this.methods.readXML(selectedFile.getAbsolutePath());
                            else
                                this.methods.readXML(selectedFile.getAbsolutePath() + ".xml");
                        }
                    }
                    loadMedics();

                    break;
                case "Export":
                    fc = new JFileChooser();

                    fc.setCurrentDirectory(new File(System.getProperty("user.home")));
                    result = fc.showOpenDialog(this.window.frame);
                    if(result == JFileChooser.APPROVE_OPTION)
                    {
                        File selectedFile = fc.getSelectedFile();
                        if(!selectedFile.equals(""))
                        {
                            if (selectedFile.getAbsolutePath().endsWith(".xml"))
                                this.methods.writeXML(selectedFile.getAbsolutePath());
                            else
                                this.methods.writeXML(selectedFile.getAbsolutePath() + ".xml");
                        }
                    }

                    break;
                case "Change File Path":
                    fc = new JFileChooser();

                    fc.setCurrentDirectory(new File(System.getProperty("user.home")));
                    result = fc.showOpenDialog(this.window.frame);
                    if(result == JFileChooser.APPROVE_OPTION)
                    {
                        File selectedFile = fc.getSelectedFile();
                        if(!selectedFile.equals(""))
                        {
                            System.out.print(selectedFile.getAbsolutePath());
                            if (selectedFile.getAbsolutePath().endsWith(".props"))
                            {
                                this.methods.writeConfigureFile(selectedFile.getAbsolutePath());
                            }
                            else
                            {
                                this.methods.writeConfigureFile(selectedFile.getAbsolutePath() + ".props");
                            }

                            this.methods.writeFile();
                        }
                    }
                    break;
                case "Exit":
                    System.exit(0);
                    break;
                case "Connect":
                    c = new JConnection(this);
                    c.mostrar();
                    break;
                case "Disconnect":
                    if(c != null)
                    {
                        c.desconectar();
                        dishableALL();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void dishableALL()
    {
        window.tabbedPane1.setEnabled(false);
        window.jbRegister.setEnabled(false);
        window.jbEnter.setEnabled(false);
        window.jbModM.setEnabled(false);
        window.jbDelM.setEnabled(false);
        window.tfUser.setEnabled(false);
        window.tfPassword.setEnabled(false);
        window.tfSearchM.setEnabled(false);
        window.listMed.setEnabled(false);
    }

    public void enableAll()
    {
        window.tabbedPane1.setEnabled(true);
        window.jbRegister.setEnabled(true);
        window.jbEnter.setEnabled(true);
        window.jbModM.setEnabled(true);
        window.jbDelM.setEnabled(true);
        window.tfUser.setEnabled(true);
        window.tfPassword.setEnabled(true);
        window.tfSearchM.setEnabled(true);
        window.listMed.setEnabled(true);
    }

    /**
     * Este metodo permite recargar todos los jlist que empleen el modelo listMedicModel
     */
    public void loadMedics()
    {
        this.window.listPatients1.clearSelection();
        this.window.listPatients2.clearSelection();
        this.window.listPatients3.clearSelection();
        this.window.listPatients4.clearSelection();
        this.window.listPatients5.clearSelection();
        this.window.listEpisodes.clearSelection();
        this.window.listAnalysis.clearSelection();
        this.window.listRadiography.clearSelection();
        this.window.listPharmacotherapy.clearSelection();

        this.listMedicModel.removeAllElements();
        for (Medic m : methods.medicList)
        {
            listMedicModel.addElement(m);
        }
    }

    /**
     * Este metodo permite recargar todos los jlist que empleen el modelo listMedicModel
     * Se emplea para filtar datos, y poder hacer asi el buscar
     */
    public void loadMedics(String search)
    {
        search = search.toLowerCase();

        this.listMedicModel.removeAllElements();
        for (Medic m : methods.medicList)
        {
            if (m.getName().contains(search) || m.getSurname().contains(search) || m.getCollegiateNumber().contains(search) || m.getMedicalSpeciality().contains(search))
                listMedicModel.addElement(m);
        }
    }

    /**
     * Este metodo permite recargar todos los jlist que empleen el modelo listPatientModel
     */
    public void loadPatients()
    {
        this.window.listMed.clearSelection();
        this.window.listPatients2.clearSelection();
        this.window.listPatients3.clearSelection();
        this.window.listPatients4.clearSelection();
        this.window.listPatients5.clearSelection();
        this.window.listEpisodes.clearSelection();
        this.window.listAnalysis.clearSelection();
        this.window.listRadiography.clearSelection();
        this.window.listPharmacotherapy.clearSelection();

        if(this.methods.medicList.size()>0)
        {
            this.listPatientModel.removeAllElements();

            for (Patient p : methods.medicList.get(this.GlobalmedicPosition).getPatientList())
            {
                listPatientModel.addElement(p);
            }
        }
    }

    /**
     * Este metodo permite recargar todos los jlist que empleen el modelo listPatientModel
     * Se emplea para filtar datos, y poder hacer asi el buscar
     */
    public void loadPatients(String search)
    {
        search = search.toLowerCase();

        if(this.methods.medicList.size()>0)
        {
            this.listPatientModel.removeAllElements();

            for (Patient p : methods.medicList.get(this.GlobalmedicPosition).getPatientList())
            {
                if(p.getName().contains(search) || p.getSurname().contains(search) || p.getHistory().getCiasNumber().contains(search))
                    listPatientModel.addElement(p);
            }
        }
    }

    /**
     * Este metodo permite recargar todos los jlist que empleen el modelo listPatientModel2
     */
    public void loadPatients2()
    {
        this.window.listPatients1.clearSelection();
        this.window.listMed.clearSelection();
        this.window.listPatients3.clearSelection();
        this.window.listPatients4.clearSelection();
        this.window.listPatients5.clearSelection();
        this.window.listEpisodes.clearSelection();
        this.window.listAnalysis.clearSelection();
        this.window.listRadiography.clearSelection();
        this.window.listPharmacotherapy.clearSelection();

        if(this.methods.medicList.size()>0)
        {
            this.listPatientModel2.removeAllElements();

            for (Patient p : methods.medicList.get(this.GlobalmedicPosition).getPatientList())
            {
                listPatientModel2.addElement(p);
            }
        }
    }

    /**
     * Este metodo permite recargar todos los jlist que empleen el modelo listPatientModel2
     * Se emplea para filtar datos, y poder hacer asi el buscar
     */
    public void loadPatients2(String search)
    {
        search = search.toLowerCase();

        if(this.methods.medicList.size()>0)
        {
            this.listPatientModel2.removeAllElements();

            for (Patient p : methods.medicList.get(this.GlobalmedicPosition).getPatientList())
            {
                if(p.getName().contains(search) || p.getSurname().contains(search) || p.getHistory().getCiasNumber().contains(search))
                listPatientModel2.addElement(p);
            }
        }
    }

    /**
     * Este metodo permite recargar todos los jlist que empleen el modelo listPatientModel3
     */
    public void loadPatients3()
    {
        this.window.listPatients1.clearSelection();
        this.window.listPatients2.clearSelection();
        this.window.listMed.clearSelection();
        this.window.listPatients4.clearSelection();
        this.window.listPatients5.clearSelection();
        this.window.listEpisodes.clearSelection();
        this.window.listAnalysis.clearSelection();
        this.window.listRadiography.clearSelection();
        this.window.listPharmacotherapy.clearSelection();

        if(this.methods.medicList.size()>0)
        {
            this.listPatientModel3.removeAllElements();

            for (Patient p : methods.medicList.get(this.GlobalmedicPosition).getPatientList())
            {
                listPatientModel3.addElement(p);
            }
        }
    }

    /**
     * Este metodo permite recargar todos los jlist que empleen el modelo listPatientModel3
     * Se emplea para filtar datos, y poder hacer asi el buscar
     */
    public void loadPatients3(String search)
    {
        search = search.toLowerCase();

        if(this.methods.medicList.size()>0)
        {
            this.listPatientModel3.removeAllElements();

            for (Patient p : methods.medicList.get(this.GlobalmedicPosition).getPatientList())
            {
                if(p.getName().contains(search) || p.getSurname().contains(search) || p.getHistory().getCiasNumber().contains(search))
                    listPatientModel3.addElement(p);
            }
        }
    }

    /**
     * Este metodo permite recargar todos los jlist que empleen el modelo listPatientModel4
     */
    public void loadPatients4()
    {
        this.window.listPatients1.clearSelection();
        this.window.listPatients2.clearSelection();
        this.window.listPatients3.clearSelection();
        this.window.listMed.clearSelection();
        this.window.listPatients5.clearSelection();
        this.window.listEpisodes.clearSelection();
        this.window.listAnalysis.clearSelection();
        this.window.listRadiography.clearSelection();
        this.window.listPharmacotherapy.clearSelection();

        if(this.methods.medicList.size()>0)
        {
            this.listPatientModel4.removeAllElements();

            for (Patient p : methods.medicList.get(this.GlobalmedicPosition).getPatientList())
            {
                listPatientModel4.addElement(p);
            }
        }
    }

    /**
     * Este metodo permite recargar todos los jlist que empleen el modelo listPatientModel4
     * Se emplea para filtar datos, y poder hacer asi el buscar
     */
    public void loadPatients4(String search)
    {
        search = search.toLowerCase();

        if(this.methods.medicList.size()>0)
        {
            this.listPatientModel4.removeAllElements();

            for (Patient p : methods.medicList.get(this.GlobalmedicPosition).getPatientList())
            {
                if(p.getName().contains(search) || p.getSurname().contains(search) || p.getHistory().getCiasNumber().contains(search))
                    listPatientModel4.addElement(p);
            }
        }
    }

    /**
     * Este metodo permite recargar todos los jlist que empleen el modelo listPatientModel5
     */
    public void loadPatients5()
    {
        this.window.listPatients1.clearSelection();
        this.window.listPatients2.clearSelection();
        this.window.listPatients3.clearSelection();
        this.window.listPatients4.clearSelection();
        this.window.listMed.clearSelection();
        this.window.listEpisodes.clearSelection();
        this.window.listAnalysis.clearSelection();
        this.window.listRadiography.clearSelection();
        this.window.listPharmacotherapy.clearSelection();

        if(this.methods.medicList.size()>0)
        {
            this.listPatientModel5.removeAllElements();

            for (Patient p : methods.medicList.get(this.GlobalmedicPosition).getPatientList())
            {
                listPatientModel5.addElement(p);
            }
        }
    }

    /**
     * Este metodo permite recargar todos los jlist que empleen el modelo listPatientModel5
     * Se emplea para filtar datos, y poder hacer asi el buscar
     */
    public void loadPatients5(String search)
    {
        search = search.toLowerCase();

        if(this.methods.medicList.size()>0)
        {
            this.listPatientModel5.removeAllElements();

            for (Patient p : methods.medicList.get(this.GlobalmedicPosition).getPatientList())
            {
                if(p.getName().contains(search) || p.getSurname().contains(search) || p.getHistory().getCiasNumber().contains(search))
                    listPatientModel5.addElement(p);
            }
        }
    }

    /**
     * Este metodo permite recargar todos los jlist que empleen el modelo listEpisodesModel
     */
    public void loadEpisodes()
    {
        this.window.listPatients1.clearSelection();
        this.window.listPatients2.clearSelection();
        this.window.listPatients3.clearSelection();
        this.window.listPatients4.clearSelection();
        this.window.listPatients5.clearSelection();
        this.window.listMed.clearSelection();
        this.window.listAnalysis.clearSelection();
        this.window.listRadiography.clearSelection();
        this.window.listPharmacotherapy.clearSelection();

        if(this.methods.medicList.size()>0 && this.methods.medicList.get(GlobalmedicPosition).getPatientList().size()>0)
        {
            this.listEpisodesModel.removeAllElements();
            for (Episodes e : methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition2).getHistory().getEpisodeList())
            {
                listEpisodesModel.addElement(e);
            }

            this.window.jbSeeE.setEnabled(false);
            this.window.jbModE.setEnabled(false);
            this.window.jbDelE.setEnabled(false);
        }
    }

    /**
     * Este metodo permite recargar todos los jlist que empleen el modelo listEpisodesModel
     * Se emplea para filtar datos, y poder hacer asi el buscar
     */
    public void loadEpisodes(String search)
    {
        search = search.toLowerCase();

        this.window.listPatients2.clearSelection();
        if(this.methods.medicList.size()>0 && this.methods.medicList.get(GlobalmedicPosition).getPatientList().size()>0) {

            if (this.listEpisodesModel.size() > 0)
                this.listEpisodesModel.removeAllElements();
            for (Episodes e : methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition2).getHistory().getEpisodeList())
            {
                if(e.getDescription().contains(search) || e.getEvolution().contains(search) || e.getObservation().contains(search))
                    listEpisodesModel.addElement(e);
            }

            this.window.jbSeeE.setEnabled(false);
            this.window.jbModE.setEnabled(false);
            this.window.jbDelE.setEnabled(false);
        }
    }

    /**
     * Este metodo permite recargar todos los jlist que empleen el modelo listAnalysisModel
     */
    public void loadAnalysis()
    {
        this.window.listPatients1.clearSelection();
        this.window.listPatients2.clearSelection();
        this.window.listPatients3.clearSelection();
        this.window.listPatients4.clearSelection();
        this.window.listPatients5.clearSelection();
        this.window.listEpisodes.clearSelection();
        this.window.listMed.clearSelection();
        this.window.listRadiography.clearSelection();
        this.window.listPharmacotherapy.clearSelection();

        if(this.methods.medicList.size()>0 && this.methods.medicList.get(GlobalmedicPosition).getPatientList().size()>0)
        {
            if(this.listAnalysisModel.size() > 0)
                this.listAnalysisModel.removeAllElements();
            for (Analysis a : methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition3).getHistory().getAnalysisList())
            {
                listAnalysisModel.addElement(a);
            }

            this.window.jbSeeA.setEnabled(false);
            this.window.jbModA.setEnabled(false);
            this.window.jbDelA.setEnabled(false);
        }
    }

    /**
     * Este metodo permite recargar todos los jlist que empleen el modelo listAnalysisModel
     * Se emplea para filtar datos, y poder hacer asi el buscar
     */
    public void loadAnalysis(String search)
    {
        search = search.toLowerCase();

        this.window.listPatients3.clearSelection();
        if(this.methods.medicList.size()>0 && this.methods.medicList.get(GlobalmedicPosition).getPatientList().size()>0)
        {
            if(this.listAnalysisModel.size() > 0)
                this.listAnalysisModel.removeAllElements();
            for (Analysis a : methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition3).getHistory().getAnalysisList())
            {
                if(a.getHospital().contains(search) || a.getAnalysisType().contains(search) || a.getReport().contains(search))
                    listAnalysisModel.addElement(a);
            }

            this.window.jbSeeA.setEnabled(false);
            this.window.jbModA.setEnabled(false);
            this.window.jbDelA.setEnabled(false);
        }
    }

    /**
     * Este metodo permite recargar todos los jlist que empleen el modelo listRadiographyModel
     */
    public void loadRadiography()
    {
        this.window.listPatients1.clearSelection();
        this.window.listPatients2.clearSelection();
        this.window.listPatients3.clearSelection();
        this.window.listPatients4.clearSelection();
        this.window.listPatients5.clearSelection();
        this.window.listEpisodes.clearSelection();
        this.window.listAnalysis.clearSelection();
        this.window.listMed.clearSelection();
        this.window.listPharmacotherapy.clearSelection();

        if(this.methods.medicList.size()>0 && this.methods.medicList.get(GlobalmedicPosition).getPatientList().size()>0)
        {
            if(this.listRadiographyModel.size() > 0)
                this.listRadiographyModel.removeAllElements();
            for (Radiography r : methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition4).getHistory().getRadiographyList())
            {
                listRadiographyModel.addElement(r);
            }

            this.window.jbSeeR.setEnabled(false);
            this.window.jbModR.setEnabled(false);
            this.window.jbDelR.setEnabled(false);
        }
    }

    /**
     * Este metodo permite recargar todos los jlist que empleen el modelo listRadiographyModel
     * Se emplea para filtar datos, y poder hacer asi el buscar
     */
    public void loadRadiography(String search)
    {
        search = search.toLowerCase();

        this.window.listPatients4.clearSelection();
        if(this.methods.medicList.size()>0 && this.methods.medicList.get(GlobalmedicPosition).getPatientList().size()>0)
        {
            if(this.listRadiographyModel.size() > 0)
                this.listRadiographyModel.removeAllElements();
            for (Radiography r : methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition4).getHistory().getRadiographyList())
            {
                if (r.getControlMonitored().contains(search) || r.getStudy().contains(search) || r.getInform().contains(search) || r.getPerformance().contains(search))
                    listRadiographyModel.addElement(r);
            }

            this.window.jbSeeR.setEnabled(false);
            this.window.jbModR.setEnabled(false);
            this.window.jbDelR.setEnabled(false);
        }
    }

    /**
     * Este metodo permite recargar todos los jlist que empleen el modelo listPharmacotherapyModel
     */
    public void loadPharmacotherapy()
    {
        this.window.listPatients1.clearSelection();
        this.window.listPatients2.clearSelection();
        this.window.listPatients3.clearSelection();
        this.window.listPatients4.clearSelection();
        this.window.listPatients5.clearSelection();
        this.window.listEpisodes.clearSelection();
        this.window.listAnalysis.clearSelection();
        this.window.listRadiography.clearSelection();
        this.window.listMed.clearSelection();

        if(this.methods.medicList.size()>0 && this.methods.medicList.get(GlobalmedicPosition).getPatientList().size()>0)
        {
            if(this.listPharmacotherapyModel.size() > 0)
                this.listPharmacotherapyModel.removeAllElements();
            for (Pharmacotherapy p : methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition5).getHistory().getPharmacotherapyList())
            {
                listPharmacotherapyModel.addElement(p);
            }

            this.window.jbDelPh.setEnabled(false);
            this.window.jbSeePh.setEnabled(false);
            this.window.jbModPh.setEnabled(false);
        }
    }

    /**
     * Este metodo permite recargar todos los jlist que empleen el modelo listPharmacotherapyModel
     * Se emplea para filtar datos, y poder hacer asi el buscar
     */
    public void loadPharmacotherapy(String search)
    {
        search = search.toLowerCase();

        this.window.listPatients5.clearSelection();
        if(this.methods.medicList.size()>0 && this.methods.medicList.get(GlobalmedicPosition).getPatientList().size()>0)
        {
            if(this.listPharmacotherapyModel.size() > 0)
                this.listPharmacotherapyModel.removeAllElements();
            for (Pharmacotherapy p : methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition5).getHistory().getPharmacotherapyList())
            {
                if(p.getDescription().contains(search) || p.getDosage().contains(search) || p.getTherapyType().contains(search) || p.getMedicines().contains(search))
                listPharmacotherapyModel.addElement(p);
            }

            this.window.jbDelPh.setEnabled(false);
            this.window.jbDelPh.setEnabled(false);
            this.window.jbDelPh.setEnabled(false);
        }
    }

    /**
     * rellena las cajas de texto con los datos de un paciente
     * @param p
     */
    public void selectionedPatient(Patient p)
    {
        this.window.tfName.setText(p.getName());
        this.window.tfSurname.setText(p.getSurname());
        this.window.tfAddress.setText(p.getAddress());
        this.window.jdateChooserP.setDate(p.getBirthDate());
    }

    /**
     * Habilita las pestañas
     */
    public void enableTabbedPane()
    {
        this.window.tabbedPane1.setEnabledAt(0, true);
        this.window.tabbedPane1.setEnabledAt(1, true);
        this.window.tabbedPane1.setEnabledAt(2, true);
    }

    /**
     * Deshabilita las pestañas
     */
    public void disableTabbedPane()
    {
        this.window.tabbedPane1.setEnabledAt(0, true);
        this.window.tabbedPane1.setEnabledAt(1, false);
        this.window.tabbedPane1.setEnabledAt(2, false);
    }

    /**
     * Deshabilita los botones
     */
    public void disablePatientButtons()
    {
        this.window.jbModP.setEnabled(false);
        this.window.jbCancel.setEnabled(false);
        this.window.jbDelP.setEnabled(false);
        this.window.jbSaveP.setEnabled(false);
    }

    /**
     * Deshabilita los botones
     */
    public void disableMedicButtons()
    {
        this.window.jbModM.setEnabled(false);
        this.window.jbDelM.setEnabled(false);
    }

    public void enablePatientButtons()
    {
        this.window.jbModP.setEnabled(true);
        this.window.jbCancel.setEnabled(true);
        this.window.jbDelP.setEnabled(true);
    }

    /**
     * Habilita los botones
     */
    public void enableMedicButtons()
    {
        this.window.jbModM.setEnabled(true);
        this.window.jbDelM.setEnabled(true);
    }

    /**
     * Deshabilita los botones
     */
    public void disableEpisodesButtons()
    {
        this.window.jbAddE.setEnabled(false);
        this.window.jbSeeE.setEnabled(false);
        this.window.jbModE.setEnabled(false);
        this.window.jbDelE.setEnabled(false);
    }

    /**
     * Deshabilita los botones
     */
    public void disableAnalysisButtons()
    {
        this.window.jbSeeA.setEnabled(false);
        this.window.jbAddA.setEnabled(false);
        this.window.jbModA.setEnabled(false);
        this.window.jbDelA.setEnabled(false);
    }

    /**
     * Deshabilita los botones
     */
    public void disableRadiographyButtons()
    {
        this.window.jbSeeR.setEnabled(false);
        this.window.jbAddR.setEnabled(false);
        this.window.jbModR.setEnabled(false);
        this.window.jbDelR.setEnabled(false);
    }

    /**
     * Deshabilita los botones
     */
    public void disablePharmacotherapyButtons()
    {
        this.window.jbSeePh.setEnabled(false);
        this.window.jbAddPh.setEnabled(false);
        this.window.jbModPh.setEnabled(false);
        this.window.jbDelPh.setEnabled(false);
    }

    /**
     * Habilita los botones
     */
    public void enableEpisodesButtons()
    {
        //this.window.jbSeeE.setEnabled(true);
        this.window.jbAddE.setEnabled(true);
        //this.window.jbModE.setEnabled(true);
        //this.window.jbDelE.setEnabled(true);
    }

    /**
     * Habilita los botones
     */
    public void enableAnalysisButtons()
    {
        //this.window.jbSeeA.setEnabled(true);
        this.window.jbAddA.setEnabled(true);
        //this.window.jbModA.setEnabled(true);
        //this.window.jbDelA.setEnabled(true);
    }

    /**
     * Habilita los botones
     */
    public void enableRadiographyButtons()
    {
        //this.window.jbSeeR.setEnabled(true);
        this.window.jbAddR.setEnabled(true);
        //this.window.jbModR.setEnabled(true);
        //this.window.jbDelR.setEnabled(true);
    }

    /**
     * Habilita los botones
     */
    public void enablePharmacotherapyButtons()
    {
        //this.window.jbDelPh.setEnabled(true);
        //this.window.jbModPh.setEnabled(true);
        this.window.jbAddPh.setEnabled(true);
        //this.window.jbSeePh.setEnabled(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    /**
     * Este metodo recoge el evento de cuando dejas de pulsar una tecla, lo cual permite que la busqueda sea efectiva
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
        String search1 = this.window.tfSearchM.getText();
        String search2 = this.window.tfSearchP.getText();
        String search3 = this.window.tfSearchP2.getText();
        String search4 = this.window.tfSearchE.getText();
        String search5 = this.window.tfSearchP3.getText();
        String search6 = this.window.tfSearchA.getText();
        String search7 = this.window.tfSearchP4.getText();
        String search8 = this.window.tfSearchR.getText();
        String search9 = this.window.tfSearchP5.getText();
        String search10 = this.window.tfSearchPh.getText();

        if(search1.equals(""))
            loadMedics();
        else
            loadMedics(search1);
        if(search2.equals(""))
            loadPatients();
        else
            loadPatients(search2);
        if(search3.equals(""))
            loadPatients2();
        else
            loadPatients2(search3);
        if(search4.equals(""))
            loadEpisodes();
        else
            loadEpisodes(search4);
        if(search5.equals(""))
            loadPatients3();
        else
            loadPatients3(search5);
        if(search6.equals(""))
            loadAnalysis();
        else
            loadAnalysis(search6);
        if(search7.equals(""))
            loadPatients4();
        else
            loadPatients4(search7);
        if(search8.equals(""))
            loadRadiography();
        else
            loadRadiography(search8);
        if(search9.equals(""))
            loadPatients5();
        else
            loadPatients5(search9);
        if(search10.equals(""))
            loadPharmacotherapy();
        else
            loadPharmacotherapy(search10);
    }

    /**
     * Este método controla los eventos de click en los jlist
     * @param e
     */
    @Override
    public void valueChanged(ListSelectionEvent e)
    {
        if(!window.listMed.isSelectionEmpty())
        {
            Medic m = window.listMed.getSelectedValue();
            if(m == null)
                return;
            this.medicPosition = methods.medicList.indexOf(m);
            enableMedicButtons();
        }
        if(!window.listPatients1.isSelectionEmpty())
        {
            Patient p = window.listPatients1.getSelectedValue();
            if (p == null)
            {
                return;
            }
            this.patientPosition = methods.medicList.get(this.GlobalmedicPosition).getPatientList().indexOf(p);
            selectionedPatient(p);
            enablePatientButtons();
        }
        if(!window.listPatients2.isSelectionEmpty())
        {
            this.window.listEpisodes.clearSelection();
            if(window.listEpisodes.isSelectionEmpty())
            {
                Patient p = window.listPatients2.getSelectedValue();
                if (p == null)
                    return;

                if(this.methods.medicList.size() > 0)
                    if(this.patientPosition2 == this.methods.medicList.size())
                        patientPosition2 = patientPosition2 - 1;

                this.patientPosition2 = methods.medicList.get(this.GlobalmedicPosition).getPatientList().indexOf(p);
                if(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition2).getHistory().getEpisodeList().size() > 0)
                    loadEpisodes();
                else
                {
                    this.listEpisodesModel.removeAllElements();
                    this.window.listEpisodes.setModel(this.listEpisodesModel);
                }
                enableEpisodesButtons();
            }
        }
        if(!window.listPatients3.isSelectionEmpty())
        {
            this.window.listAnalysis.clearSelection();
            if(window.listAnalysis.isSelectionEmpty())
            {
                Patient p = window.listPatients3.getSelectedValue();
                if (p == null)
                    return;
                this.patientPosition3 = methods.medicList.get(this.GlobalmedicPosition).getPatientList().indexOf(p);
                if(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition3).getHistory().getAnalysisList().size() > 0)
                {
                    loadAnalysis();
                }
                else
                {
                    this.listAnalysisModel.removeAllElements();
                    this.window.listAnalysis.setModel(this.listAnalysisModel);
                }
                enableAnalysisButtons();
            }
        }
        if(!window.listPatients4.isSelectionEmpty())
        {
            this.window.listRadiography.clearSelection();
            if(window.listRadiography.isSelectionEmpty())
            {
                Patient p = window.listPatients4.getSelectedValue();
                if (p == null)
                    return;
                this.patientPosition4 = methods.medicList.get(this.GlobalmedicPosition).getPatientList().indexOf(p);
                if(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition4).getHistory().getRadiographyList().size() > 0)
                {
                    loadRadiography();
                }
                else
                {
                    this.listRadiographyModel.removeAllElements();
                    this.window.listRadiography.setModel(this.listRadiographyModel);
                }
                enableRadiographyButtons();
            }
        }
        if(!window.listPatients5.isSelectionEmpty())
        {
            this.window.listPharmacotherapy.clearSelection();
            if(window.listPharmacotherapy.isSelectionEmpty())
            {
                Patient p = window.listPatients5.getSelectedValue();
                if(p == null)
                    return;
                this.patientPosition5 = methods.medicList.get(this.GlobalmedicPosition).getPatientList().indexOf(p);
                if(this.methods.medicList.get(this.GlobalmedicPosition).getPatientList().get(this.patientPosition5).getHistory().getPharmacotherapyList().size() > 0)
                {
                    loadPharmacotherapy();
                }
                else
                {
                    this.listPharmacotherapyModel.removeAllElements();
                    this.window.listPharmacotherapy.setModel(this.listPharmacotherapyModel);
                }
                enablePharmacotherapyButtons();
            }
        }
        if(!window.listEpisodes.isSelectionEmpty())
        {
            this.window.listPatients2.clearSelection();
            Episodes ep = this.window.listEpisodes.getSelectedValue();
            if(ep == null)
            {
                return;
            }
            this.window.jbSeeE.setEnabled(true);
            this.window.jbAddE.setEnabled(true);
            this.window.jbModE.setEnabled(true);
            this.window.jbDelE.setEnabled(true);

            this.episodePosition = this.methods.medicList.get(GlobalmedicPosition).getPatientList().get(this.patientPosition2).getHistory().getEpisodeList().indexOf(ep);
        }
        if(!window.listAnalysis.isSelectionEmpty())
        {
            this.window.listPatients3.clearSelection();
            Analysis a = this.window.listAnalysis.getSelectedValue();
            if(a == null)
            {
                return;
            }
            this.window.jbSeeA.setEnabled(true);
            this.window.jbModA.setEnabled(true);
            this.window.jbDelA.setEnabled(true);
            this.window.jbAddA.setEnabled(true);

            this.analysisPosition = this.methods.medicList.get(GlobalmedicPosition).getPatientList().get(this.patientPosition3).getHistory().getAnalysisList().indexOf(a);
        }
        if(!window.listRadiography.isSelectionEmpty())
        {
            this.window.listPatients4.clearSelection();
            Radiography r = this.window.listRadiography.getSelectedValue();
            if(r == null)
            {
                return;
            }
            this.window.jbSeeR.setEnabled(true);
            this.window.jbModR.setEnabled(true);
            this.window.jbDelR.setEnabled(true);
            this.window.jbAddR.setEnabled(true);

            this.radiographyPosition = this.methods.medicList.get(GlobalmedicPosition).getPatientList().get(this.patientPosition4).getHistory().getRadiographyList().indexOf(r);
        }
        if(!window.listPharmacotherapy.isSelectionEmpty())
        {
            this.window.listPatients5.clearSelection();
            Pharmacotherapy p = this.window.listPharmacotherapy.getSelectedValue();
            if(p == null)
            {
                return;
            }
            this.window.jbSeePh.setEnabled(true);
            this.window.jbModPh.setEnabled(true);
            this.window.jbDelPh.setEnabled(true);
            this.window.jbAddPh.setEnabled(true);

            this.pharmacotherapyPosition = this.methods.medicList.get(GlobalmedicPosition).getPatientList().get(this.patientPosition5).getHistory().getPharmacotherapyList().indexOf(p);
        }
    }

    /**
     * Este método limpia las cajas
     */
    public void clean()
    {
        this.window.tfName.setText("");
        this.window.tfAddress.setText("");
        this.window.tfSurname.setText("");
        this.window.jdateChooserP.setDate(null);
    }
}