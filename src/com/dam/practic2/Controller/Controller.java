package com.dam.practic2.Controller;

import com.dam.practic2.Model.Methods.Methods;
import com.dam.practic2.View.JConnection;
import com.dam.practic2.View.Window;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.sql.Date;

/**
 * @author Daniel Cano Mainar
 * @version 1.0 10/11/2015
 * Esta clase controla lo que le pueda pasar a la ventana. Incorpora modelo MVC (Model, view, controller)
 * En esta clase se controlan todos los eventos del programa así como tratamiento con cada uno de los elementos de la ventana.
 */
public class Controller extends SwingWorker<Void, Void> implements ActionListener, KeyListener
{
    private Window window;
    private Methods methods;
    private JConnection c;
    private DefaultTableModel ColumnMedicTable;
    private DefaultTableModel ColumnPatientTable;
    private DefaultTableModel ColumnPatient2Table;
    private DefaultTableModel ColumnEpisodeTable;
    private DefaultTableModel ColumnAnalysisTable;
    private DefaultTableModel ColumnRadiographyTable;
    private DefaultTableModel ColumnPharmacotherapyTable;
    public int idMedic;
    public int idPatient;
    public int idPatient2;
    public int idEpisode;
    public int idVisit;
    public boolean pause;
    //TODO CREAR EL RESTO DE ID'S

    /**
     * Este es el constructor de la clase. Aqui se implementan todos los listener de la ventana
     * @param w
     */
    public Controller(Window w)
    {
        this.window = w;
        this.methods = new Methods(Controller.this);

        this.window.jbNewP.addActionListener(this);
        this.window.jbModP.addActionListener(this);
        this.window.jbSaveP.addActionListener(this);
        this.window.jbDelP.addActionListener(this);
        this.window.jbEnter.addActionListener(this);
        this.window.jbRegister.addActionListener(this);
        this.window.jbAddE.addActionListener(this);
        this.window.jbModE.addActionListener(this);
        this.window.jbDelE.addActionListener(this);
        this.window.jbAddA.addActionListener(this);
        this.window.jbModA.addActionListener(this);
        this.window.jbDelA.addActionListener(this);
        this.window.jbAddR.addActionListener(this);
        this.window.jbModR.addActionListener(this);
        this.window.jbDelR.addActionListener(this);
        this.window.jbAddPh.addActionListener(this);
        this.window.jbModPh.addActionListener(this);
        this.window.jbDelPh.addActionListener(this);
        this.window.jbModM.addActionListener(this);
        this.window.jbDelM.addActionListener(this);
        this.window.jbCancel.addActionListener(this);

        this.window.menuItem6.addActionListener(this);
        this.window.menuItem7.addActionListener(this);
        this.window.menuItem8.addActionListener(this);

        this.window.tfSearchM.addKeyListener(this);
        this.window.tfSearchP.addKeyListener(this);
        this.window.tfSearchP2.addKeyListener(this);
        this.window.tfSearchE.addKeyListener(this);

        ColumnMedicTable = new DefaultTableModel();
        window.tableMedic.setModel(ColumnMedicTable);
        ColumnPatientTable = new DefaultTableModel();
        window.tablePatient1.setModel(ColumnPatientTable);
        ColumnPatient2Table = new DefaultTableModel();
        window.tablePatient2.setModel(ColumnPatient2Table);
        window.tablePatient3.setModel(ColumnPatientTable);
        window.tablePatient4.setModel(ColumnPatientTable);
        window.tablePatient5.setModel(ColumnPatientTable);
        ColumnEpisodeTable = new DefaultTableModel();
        window.tableEpisodes.setModel(ColumnEpisodeTable);
        ColumnAnalysisTable = new DefaultTableModel();
        window.tableAnalysis.setModel(ColumnAnalysisTable);
        ColumnRadiographyTable = new DefaultTableModel();
        window.tableRadiography.setModel(ColumnRadiographyTable);
        ColumnPharmacotherapyTable = new DefaultTableModel();
        window.tablePharmacotherapy.setModel(ColumnPharmacotherapyTable);

        ColumnNames();
        loadMedic();
        disconect();
        dishableMedic();
        pause = false;
        this.execute();

        window.tableMedic.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                window.tablePatient2.clearSelection();
                window.tablePatient1.clearSelection();
                if (window.tableMedic.isRowSelected(window.tableMedic.getSelectedRow())) {
                    idMedic = Integer.parseInt(window.tableMedic.getValueAt(window.tableMedic.getSelectedRow(), 0).toString());
                    enableMedic();
                }
            }
        });

        window.tablePatient1.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                window.tablePatient2.clearSelection();
                window.tableMedic.clearSelection();
                if (window.tablePatient1.isRowSelected(window.tablePatient1.getSelectedRow()))
                {
                    idPatient = Integer.parseInt(window.tablePatient1.getValueAt(window.tablePatient1.getSelectedRow(), 0).toString());
                    idVisit = Integer.parseInt(window.tablePatient1.getValueAt(window.tablePatient1.getSelectedRow(), 6).toString());

                    Object[] datos = methods.selectPatient(idPatient);
                    window.tfName.setText(String.valueOf(datos[1]));
                    window.tfSurname.setText(String.valueOf(datos[2]));
                    window.jdateChooserP.setDate(Date.valueOf(datos[3].toString()));
                    window.tfAddress.setText(String.valueOf(datos[4]));
                    window.jdateChooserP2.setDate(Date.valueOf(datos[5].toString()));

                    window.jbModP.setEnabled(true);
                    window.jbDelP.setEnabled(true);
                    window.jbCancel.setEnabled(true);

                    window.tfName.setEnabled(true);
                    window.tfSurname.setEnabled(true);
                    window.jdateChooserP.setEnabled(true);
                    window.tfAddress.setEnabled(true);
                    window.jdateChooserP2.setEnabled(true);
                }
            }
        });

        window.tablePatient2.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                window.tablePatient1.clearSelection();
                window.tableMedic.clearSelection();
                if (window.tablePatient2.isRowSelected(window.tablePatient2.getSelectedRow()))
                {
                    idPatient2 = Integer.parseInt(window.tablePatient2.getValueAt(window.tablePatient2.getSelectedRow(), 0).toString());
                    window.jbAddE.setEnabled(true);
                    window.jbModE.setEnabled(false);
                    window.jbDelE.setEnabled(false);
                    loadEpisodes();
                }
            }
        });

        window.tableEpisodes.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if (window.tableEpisodes.isRowSelected(window.tableEpisodes.getSelectedRow()))
                {
                    idEpisode = Integer.parseInt(window.tableEpisodes.getValueAt(window.tableEpisodes.getSelectedRow(), 0).toString());
                }
                window.jbModE.setEnabled(true);
                window.jbDelE.setEnabled(true);
            }
        });
    }

    public void ColumnNames()
    {
        String[] M = {"Id", "Name", "Surname", "Address", "Med. Centre", "Email", "Medical Speciality", "Telephone", "Birth Date"};
        for(int i=0; i<M.length; i++)
        {
            ColumnMedicTable.addColumn(M[i]);
        }
        String[] P = {"Id Patient", "Name", "Surname", "birthDate", "Address", "V. Date", "V. Id"};
        for(int i=0; i<P.length; i++)
        {
            ColumnPatientTable.addColumn(P[i]);
            ColumnPatient2Table.addColumn(P[i]);
        }
        String[] E = {"Id", "Description", "Start Date", "End Date", "Evolution"};
        for(int i=0; i<E.length; i++)
        {
            ColumnEpisodeTable.addColumn(E[i]);
        }
        String[] A = {"Id", "Analysis Date", "Analysis Type", "Report", "Report Date"};
        for(int i=0; i<A.length; i++)
        {
            ColumnAnalysisTable.addColumn(A[i]);
        }
        String[] R = {"Id", "Report Date", "Rpt. Date", "Rad. Date", "Study", "Report", "Ctrl. Done"};
        for(int i=0; i<R.length; i++)
        {
            ColumnRadiographyTable.addColumn(R[i]);
        }
        String[] Ph = {"Id", "Descript.", "Dosage", "Start Date", "End Date", "In. Weight", "Fin. Weight"};
        for(int i=0; i<Ph.length; i++)
        {
            ColumnPharmacotherapyTable.addColumn(Ph[i]);
        }
    }

    public void loadMedic()
    {
        ArrayList<Object[]> list = methods.SelectAllMedic();

        if (list != null)
        {
            ColumnMedicTable.setNumRows(0);
            for (int i = 0;i < list.size(); i++)
            {
                ColumnMedicTable.addRow(list.get(i));
            }
        }
    }

    public void loadPatient()
    {
        ArrayList<Object[]> list = methods.selectAllPatient();

        if (list != null)
        {
            ColumnPatientTable.setNumRows(0);
            for (int i = 0;i < list.size(); i++)
            {
                ColumnPatientTable.addRow(list.get(i));
            }
        }
    }

    public void loadPatient2()
    {
        ArrayList<Object[]> list = methods.selectAllPatient();

        if (list != null)
        {
            ColumnPatient2Table.setNumRows(0);
            for (int i = 0;i < list.size(); i++)
            {
                ColumnPatient2Table.addRow(list.get(i));
            }
        }
    }

    public void loadEpisodes()
    {
        ArrayList<Object[]> list = methods.selectAllEpisodes(idPatient2);

        if (list != null)
        {
            ColumnEpisodeTable.setNumRows(0);
            for (int i = 0;i < list.size(); i++)
            {
                ColumnEpisodeTable.addRow(list.get(i));
            }
        }
    }

    public void loadMedic(String search)
    {
        ArrayList<Object[]> list = methods.SelectAllMedic();

        if (list != null)
        {
            ColumnMedicTable.setNumRows(0);
            for (int i = 0;i < list.size(); i++)
            {
                if (String.valueOf(list.get(i)[0]).contains(search) || String.valueOf(list.get(i)[1]).contains(search) || String.valueOf(list.get(i)[2]).contains(search) || String.valueOf(list.get(i)[3]).contains(search) || String.valueOf(list.get(i)[4]).contains(search) || String.valueOf(list.get(i)[5]).contains(search) || String.valueOf(list.get(i)[6]).contains(search) || String.valueOf(list.get(i)[7]).contains(search))
                    ColumnMedicTable.addRow(list.get(i));
            }
        }
    }

    public void loadPatient(String search)
    {
        ArrayList<Object[]> list = methods.selectAllPatient();

        if (list != null)
        {
            ColumnPatientTable.setNumRows(0);
            for (int i = 0;i < list.size(); i++)
            {
                if (String.valueOf(list.get(i)[0]).contains(search) || String.valueOf(list.get(i)[1]).contains(search) || String.valueOf(list.get(i)[2]).contains(search) || String.valueOf(list.get(i)[4]).contains(search) || String.valueOf(list.get(i)[6]).contains(search))
                    ColumnPatientTable.addRow(list.get(i));
            }
        }
    }

    public void loadPatient2(String search)
    {
        ArrayList<Object[]> list = methods.selectAllPatient();

        if (list != null)
        {
            ColumnPatient2Table.setNumRows(0);
            for (int i = 0;i < list.size(); i++)
            {
                if (String.valueOf(list.get(i)[0]).contains(search) || String.valueOf(list.get(i)[1]).contains(search) || String.valueOf(list.get(i)[2]).contains(search) || String.valueOf(list.get(i)[4]).contains(search) || String.valueOf(list.get(i)[6]).contains(search))
                    ColumnPatient2Table.addRow(list.get(i));
            }
        }
    }

    public void loadEpisodes(String search)
    {
        ArrayList<Object[]> list = methods.selectAllEpisodes(idPatient2);

        if (list != null)
        {
            ColumnEpisodeTable.setNumRows(0);
            for (int i = 0;i < list.size(); i++)
            {
                if (String.valueOf(list.get(i)[0]).contains(search) || String.valueOf(list.get(i)[1]).contains(search) || String.valueOf(list.get(i)[4]).contains(search))
                    ColumnEpisodeTable.addRow(list.get(i));
            }
        }
    }

    public void connect()
    {
        window.tabbedPane1.setEnabledAt(0, true);
        window.tabbedPane1.setEnabledAt(1, true);
        window.tabbedPane1.setEnabledAt(2, true);
    }

    public void  disconect()
    {
        window.tabbedPane1.setEnabledAt(0, false);
        window.tabbedPane1.setEnabledAt(1, false);
        window.tabbedPane1.setEnabledAt(2, false);

        window.tabbedPaneHistory.setEnabled(false);

        window.tfName.setEnabled(false);
        window.tfSurname.setEnabled(false);
        window.tfAddress.setEnabled(false);
        window.jdateChooserP.setEnabled(false);
        window.jdateChooserP2.setEnabled(false);
        ColumnMedicTable.setNumRows(0);

        window.jbModP.setEnabled(false);
        window.jbDelP.setEnabled(false);
        window.jbSaveP.setEnabled(false);
        window.jbCancel.setEnabled(false);

        window.jbAddE.setEnabled(false);
        window.jbModE.setEnabled(false);
        window.jbDelE.setEnabled(false);
    }

    public void newPatient()
    {
        window.tfName.setEnabled(true);
        window.tfSurname.setEnabled(true);
        window.tfAddress.setEnabled(true);
        window.jdateChooserP.setEnabled(true);
        window.jdateChooserP2.setEnabled(true);
        window.jbSaveP.setEnabled(true);

        window.tfName.setText("");
        window.tfSurname.setText("");
        window.tfAddress.setText("");
        window.jdateChooserP.setDate(null);
        window.jdateChooserP2.setDate(null);

        window.jbNewP.setEnabled(false);
        window.jbSaveP.setEnabled(true);
        window.jbCancel.setEnabled(true);
    }

    public void endNewPatient()
    {
        window.tablePatient1.clearSelection();
        window.jbDelP.setEnabled(false);
        window.jbModP.setEnabled(false);

        window.tfName.setEnabled(false);
        window.tfSurname.setEnabled(false);
        window.tfAddress.setEnabled(false);
        window.jdateChooserP.setEnabled(false);
        window.jdateChooserP2.setEnabled(false);

        window.tfName.setText("");
        window.tfSurname.setText("");
        window.tfAddress.setText("");
        window.jdateChooserP.setDate(null);
        window.jdateChooserP2.setDate(null);

        window.jbNewP.setEnabled(true);
        window.jbSaveP.setEnabled(false);
        window.jbCancel.setEnabled(false);
    }

    public void dishableMedic()
    {
        idMedic = 0;
        window.jbModM.setEnabled(false);
        window.jbDelM.setEnabled(false);
    }

    public void enableMedic()
    {
        window.jbModM.setEnabled(true);
        window.jbDelM.setEnabled(true);
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
                    window.jbModP.setEnabled(false);
                    window.jbDelP.setEnabled(false);
                    window.tablePatient1.clearSelection();
                    newPatient();
                    break;
                case "Modify Patient":
                    if(String.valueOf(methods.getModificaciones()[1]).equals("0"))
                    {
                        if(window.tfName.getText().equals("") || window.tfSurname.getText().equals("") || window.tfAddress.getText().equals(""))
                        {
                            JOptionPane.showMessageDialog(null, "Please, fill each field", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else
                        {
                            methods.updatePatient(idPatient, window.tfName.getText(), window.tfSurname.getText(), new Date(window.jdateChooserP.getDate().getTime()), window.tfAddress.getText(), new Date(window.jdateChooserP2.getDate().getTime()));
                            loadPatient();
                            loadPatient2();
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "You can not do that now", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    window.jbModP.setEnabled(false);
                    window.jbDelP.setEnabled(false);
                    window.jbCancel.setEnabled(false);
                    window.tfName.setEnabled(false);
                    window.tfName.setText("");
                    window.tfSurname.setEnabled(false);
                    window.tfSurname.setText("");
                    window.jdateChooserP.setEnabled(false);
                    window.jdateChooserP2.setEnabled(false);
                    window.jdateChooserP.setDate(null);
                    window.jdateChooserP2.setDate(null);
                    window.tfAddress.setEnabled(false);
                    window.tfAddress.setText("");
                    window.tablePatient1.clearSelection();
                    break;
                case "Cancel Modification":
                    endNewPatient();
                    break;
                case "Save Patient":
                    if(window.tfName.getText().equals("") || window.tfSurname.getText().equals("") || window.tfAddress.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(null, "Please, fill each field", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    {
                        if(methods.existsPatient(window.tfName.getText(), window.tfSurname.getText())==true)
                        {
                            JOptionPane.showMessageDialog(null, "This patient already exists", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else
                        {
                            methods.insertPatient(window.tfName.getText(), window.tfSurname.getText(), new Date(window.jdateChooserP.getDate().getTime()), window.tfAddress.getText(), new Date(window.jdateChooserP2.getDate().getTime()));
                            loadPatient();
                            loadPatient2();
                        }
                    }
                    endNewPatient();
                    break;
                case "Delete Patient":
                    if(String.valueOf(methods.getModificaciones()[1]).equals("0"))
                    {
                        int resutl = JOptionPane.showConfirmDialog(null, "Do you want to delete it?", "Are you sure?", JOptionPane.YES_NO_CANCEL_OPTION);
                        if(resutl == JOptionPane.YES_OPTION)
                        {
                            methods.deletePatient(idPatient);
                            loadPatient();
                            loadPatient2();
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "You can not do that now", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    window.jbModP.setEnabled(false);
                    window.jbDelP.setEnabled(false);
                    window.jbCancel.setEnabled(false);
                    window.tfName.setEnabled(false);
                    window.tfName.setText("");
                    window.tfSurname.setEnabled(false);
                    window.tfSurname.setText("");
                    window.jdateChooserP.setEnabled(false);
                    window.jdateChooserP2.setEnabled(false);
                    window.jdateChooserP.setDate(null);
                    window.jdateChooserP2.setDate(null);
                    window.tfAddress.setEnabled(false);
                    window.tfAddress.setText("");
                    window.tablePatient1.clearSelection();
                    break;
                case "Modify Medic":
                    if(String.valueOf(methods.getModificaciones()[0]).equals("0"))
                    {
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
                            if (fieldUser.getText().equals("") || fieldPassword.getText().equals(""))
                            {

                                JOptionPane.showMessageDialog(null, "Please, fill each field", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            else
                            {
                                if(methods.existsMedic2(fieldUser.getText(), fieldPassword.getText(), idMedic) == false)
                                {
                                    JOptionPane.showMessageDialog(null, "You can't modify this user", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(null, "Successul", "Successful", JOptionPane.INFORMATION_MESSAGE);
                                    sw1 = 1;

                                    Object[] datos = methods.selectMedic(idMedic);

                                    JTextField field2 = new JTextField();
                                    field2.setText(datos[1].toString());
                                    JTextField field3 = new JTextField();
                                    field3.setText(datos[2].toString());
                                    JTextField field4 = new JTextField();
                                    field4.setText(datos[3].toString());
                                    JTextField field5 = new JTextField();
                                    field5.setText(datos[4].toString());
                                    JTextField field6 = new JTextField();
                                    field6.setText(datos[5].toString());
                                    JTextField field7 = new JTextField();
                                    field7.setText(datos[6].toString());
                                    JTextField field8 = new JTextField();
                                    field8.setText(datos[7].toString());
                                    JDateChooser field9 = new JDateChooser();
                                    field9.setDate((Date)datos[8]);


                                    int sw = 0;
                                    int option = 0;

                                    while (sw == 0)
                                    {
                                        Object[] message = {"Name", field2, "Surname", field3, "Address", field4,
                                                "Medical Centre", field5, "Email", field6, "Medical Speciality", field7, "Telephone", field8, "BirthDate", field9};
                                        option = JOptionPane.showConfirmDialog(null, message, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);

                                        if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION)
                                        {
                                            sw = 1;
                                        }
                                        else
                                        {
                                            if (field2.getText().equals("") || field3.getText().equals("") || field4.getText().equals("")
                                                    || field5.getText().equals("") || field7.getText().equals("") || field8.getText().equals(""))
                                            {
                                                JOptionPane.showMessageDialog(null, "Please, fill each field", "Error", JOptionPane.ERROR_MESSAGE);
                                            }
                                            else
                                            {
                                                JOptionPane.showMessageDialog(null, "Successul", "Successful", JOptionPane.INFORMATION_MESSAGE);
                                                methods.updateMedic(field2.getText(), field3.getText(), field4.getText(), field5.getText(), field6.getText(), field7.getText(), field8.getText(), new Date(field9.getDate().getTime()), idMedic);
                                                sw = 1;
                                                loadMedic();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "You can not do that now", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case "Delete Medic":
                    if(String.valueOf(methods.getModificaciones()[0]).equals("0"))
                    {
                        JTextField fieldUser = new JTextField();
                        JTextField fieldPassword = new JTextField();
                        Object[] check2 = {"User", fieldUser, "Password", fieldPassword};
                        int op = JOptionPane.showConfirmDialog(null, check2, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);
                        int sw1 = 0;

                        if(op == JOptionPane.CANCEL_OPTION || op == JOptionPane.CLOSED_OPTION)
                        {
                            sw1 = 1;
                        }
                        else
                        {
                            if (fieldUser.getText().equals("") || fieldPassword.getText().equals(""))
                            {

                                JOptionPane.showMessageDialog(null, "Please, fill each field", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            else
                            {
                                if (methods.existsMedic2(fieldUser.getText(), fieldPassword.getText(), idMedic) == false)
                                {
                                    JOptionPane.showMessageDialog(null, "You can't modify this user", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(null, "Successul", "Successful", JOptionPane.INFORMATION_MESSAGE);
                                    methods.deleteMedic(idMedic);
                                    loadMedic();
                                    loadPatient();
                                    loadPatient2();

                                    if(methods.existsMedic3(idMedic) == false)
                                    {
                                        disconect();
                                        window.jlMedic.setText("You need to log in");
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "You can not do that now", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case "ENTER":
                    if(!window.tfUser.getText().equals("") || !window.tfPassword.getText().equals(""))
                    {
                        if(methods.existsMedic(window.tfUser.getText(), window.tfPassword.getText()) == true) {
                            connect();
                            JOptionPane.showMessageDialog(null, "Correct", "Correct", JOptionPane.INFORMATION_MESSAGE);
                            window.jlMedic.setText(window.tfUser.getText());
                            methods.medicConnected = methods.getCollegiateNumber(window.tfUser.getText(), window.tfPassword.getText());
                            loadPatient();
                            loadPatient2();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "There is no medic with that user and password", "Error", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Please, fill each field", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                case "REGISTER":
                    JTextField field1 = new JTextField();
                    JTextField field2 = new JTextField();
                    JTextField field3 = new JTextField();
                    JTextField field4 = new JTextField();
                    JTextField field5 = new JTextField();
                    JTextField field6 = new JTextField();
                    JTextField field7 = new JTextField();
                    JTextField field8 = new JTextField();
                    JTextField field9 = new JTextField();
                    JDateChooser field10 = new JDateChooser();

                    int sw = 0;
                    int option = 0;

                    while (sw == 0)
                    {
                        Object[] message = {"User Name", field1, "User Password", field2, "Name", field3, "Surname", field4,
                                "Address", field5, "Medical Centre", field6, "Email", field7, "Medical Speciality", field8, "Telephone", field9, "Birth Date", field10};
                        option = JOptionPane.showConfirmDialog(null, message, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);

                        if(option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION)
                        {
                            sw = 1;
                        }
                        else
                        {
                            if (field1.getText().equals("") || field2.getText().equals("") || field3.getText().equals("") || field4.getText().equals("") || field5.getText().equals("") || field6.getText().equals("") || field7.getText().equals("") || field8.getText().equals("") || field9.getText().equals(""))
                            {
                                JOptionPane.showMessageDialog(null, "Please, fill each field", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            else
                            {
                                if(methods.existsMedic(field1.getText(), field2.getText()))
                                {
                                    sw = 0;
                                    JOptionPane.showMessageDialog(null, "This user already exists", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(null, "Successul", "Successful", JOptionPane.INFORMATION_MESSAGE);
                                    sw = 1;
                                    methods.insertMedic(field1.getText(), field2.getText(), field3.getText(), field4.getText(), field5.getText(), field6.getText(), field7.getText(), field8.getText(), field9.getText(), new Date(field10.getDate().getTime()));
                                    loadMedic();
                                }
                            }
                        }
                    }
                    break;
                case "Add Episodes":
                    window.tableEpisodes.clearSelection();
                    field1 = new JTextField();
                    JDateChooser fieldd2 = new JDateChooser();
                    JDateChooser fieldd3 = new JDateChooser();
                    JTextField fieldd4 = new JTextField();

                    sw = 0;
                    option = 0;

                    while (sw == 0) {
                        Object[] message = {"Description", field1, "Start Date", fieldd2, "End Date", fieldd3, "Evolution", fieldd4};

                        option = JOptionPane.showConfirmDialog(null, message, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);

                        if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
                            sw = 1;
                        } else {
                            if (field1.getText().equals("") || fieldd4.getText().equals("")) {
                                JOptionPane.showMessageDialog(null, "Please, fill each field", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                sw = 1;
                                methods.insertEpisode(field1.getText(), new Date(fieldd2.getDate().getTime()), new Date(fieldd3.getDate().getTime()), fieldd4.getText(), idPatient2);
                                loadEpisodes();
                                loadPatient();
                                loadPatient2();
                            }
                        }
                    }

                    window.tableEpisodes.clearSelection();
                    window.tablePatient2.clearSelection();
                    window.jbAddE.setEnabled(false);
                    window.jbModE.setEnabled(false);
                    window.jbDelE.setEnabled(false);
                    break;
                case "Modify Episodes":
                    if(String.valueOf(methods.getModificaciones()[3]).equals("0"))
                    {
                        window.jbModE.setEnabled(false);
                        window.jbDelE.setEnabled(false);
                        window.tableEpisodes.clearSelection();

                        Object[] datos = methods.selectEpisode(idEpisode);

                        field1 = new JTextField();
                        field1.setText(datos[0].toString());
                        fieldd2 = new JDateChooser();
                        fieldd2.setDate(Date.valueOf(datos[1].toString()));
                        fieldd3 = new JDateChooser();
                        fieldd3.setDate(Date.valueOf(datos[2].toString()));
                        field4 = new JTextField();
                        field4.setText(datos[3].toString());

                        sw = 0;
                        option = 0;

                        Object[] message = {"Description", field1, "Start Date", fieldd2, "End Date", fieldd3, "Evolution", field4,};
                        option = JOptionPane.showConfirmDialog(null, message, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);
                        if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION)
                        {
                            sw = 1;
                        }
                        else
                        {
                            if (field1.getText().equals("") || field4.getText().equals(""))
                            {
                                JOptionPane.showMessageDialog(null, "Please, fill each field", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Successul", "Successful", JOptionPane.INFORMATION_MESSAGE);
                                methods.updateEpisode(idEpisode, field1.getText(), new Date(fieldd2.getDate().getTime()), new Date(fieldd3.getDate().getTime()), field4.getText());
                                sw = 1;
                                loadEpisodes();
                                loadPatient();
                                loadPatient2();
                            }
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "You can not do that now", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case "Delete Episodes":
                    if(String.valueOf(methods.getModificaciones()[3]).equals("0"))
                    {
                        int resutl = JOptionPane.showConfirmDialog(null, "Do you want to delete it?", "Are you sure?", JOptionPane.YES_NO_CANCEL_OPTION);
                        if(resutl == JOptionPane.YES_OPTION)
                        {
                            methods.deleteEpisode(idEpisode);
                            loadEpisodes();
                            loadPatient();
                            loadPatient2();
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "You can not do that now", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    window.jbModE.setEnabled(false);
                    window.jbDelE.setEnabled(false);
                    window.tableEpisodes.clearSelection();
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
                case "Exit":
                    System.exit(0);
                    break;
                case "Connect":
                    c = new JConnection(this, this.methods);
                    c.mostrar();
                    break;
                case "Disconnect":
                    JConnection c = new JConnection(this, this.methods);
                    c.desconectar();
                    disconect();
                    break;
                default:
                    break;
            }
        }
    }
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

        if(search1.equals(""))
            loadMedic();
        else
            loadMedic(search1);
        if(search2.equals(""))
            loadPatient();
        else
            loadPatient(search2);
        if(search3.equals(""))
            loadPatient2();
        else
            loadPatient2(search3);
        if(search4.equals(""))
            loadEpisodes();
        else
            loadEpisodes(search4);
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {

    }

    @Override
    protected Void doInBackground() throws Exception
    {
        while(pause == false)
        {
            loadMedic();
            loadEpisodes();
            loadPatient2();
            loadPatient();
            Thread.sleep(5000);
        }
        return null;
    }
}