/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *Clase que permite la conexion con la base de datos empleo
 * @author Carlos Baez
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexionBD {

    private static ConexionBD conexionDB;
    private Connection conexion;

    /**
     * Constructor
     */
    ConexionBD() {
        try {
          Class.forName("com.mysql.jdbc.Driver");
           conectar();
           //JOptionPane.showMessageDialog(null, "Conexion Exitosa");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No fue posible cargar el controlador");
        }
    }

    
    /**
     * Metodo que devuelve la instancia de la conexion
     * @return objeto de tipo Conexion, llamar al metodo .getConexion() para hacer la conexion
     */
    public static ConexionBD obtenerInstancia() {
        
        //  if (conexionDB == null) {
            conexionDB = new ConexionBD();
        //} else {
            //JOptionPane.showMessageDialog(null, "No puede tener 2 conexiones a bases de datos abiertas al tiempo");
        //}
        return conexionDB;
    }

    private void conectar() {
        String URL = "jdbc:mysql://localhost:3306/empleo";
        String usuario = "root";
        String contraseña = "";
        try {
            conexion = DriverManager.getConnection(URL, usuario, contraseña);
            //JOptionPane.showMessageDialog(null, "Conectado correctamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "Error en ConexionDB",JOptionPane.ERROR_MESSAGE);
        }
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

}
