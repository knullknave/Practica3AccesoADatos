package com.dam.practic2.View;

import com.dam.practic2.Controller.Controller;
import com.dam.practic2.Model.Methods.Methods;

import javax.swing.*;
import java.awt.event.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class JConnection extends JDialog
{
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JTextField textField2;
    public  Connection conexion;
    public Controller c;
    public Methods m;

    public JConnection(Controller c, Methods m)
    {
        this.c = c;
        this.m = m;
        setContentPane(contentPane);
        pack();
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(null);

        buttonOK.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK()
    {
        String servidor = textField1.getText();
        String usuario = textField2.getText();
        String contrasena = String.valueOf(passwordField1.getPassword());

        try
        {
			/*
			 * Carga el driver de conexión JDBC para conectar con MySQL
			 */
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://" + servidor + ":3306" + "/MedicDB", usuario, contrasena);

           // m.conexion = conexion;
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

        dispose();
    }

    private void onCancel()
    {
        dispose();
    }

    public void desconectar()
    {
        try
        {
            if(conexion == null)
            {
             //   m.conexion.close();
             //   m.conexion = null;
            }
            else
            {
                conexion.close();
                conexion = null;
            }

            JOptionPane.showMessageDialog(null, "Se ha desconectado de la Base de Datos");
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
    }

    public void mostrar()
    {
        setVisible(true);
    }
}