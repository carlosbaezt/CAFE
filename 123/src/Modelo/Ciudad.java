/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *Esta clase define los metodos necesarios para realizar las operacions basicas en sql de la tabla ciudad
 * @author Carlos Baez
 * 
 */
public class Ciudad {
    
    ConexionBD conexion = null;
    PreparedStatement statement  = null;
    String sql;
    
    /**
     *
     */
    public Ciudad(){
    conexion = ConexionBD.obtenerInstancia();    
    }
    
    /**
     *  Metodo que permite agregar o editar una ciudad
     * @param ciudad es el identificador unico de la ciudad (tamaño 5)
     * @param nombre el nombre de la ciudad
     * @param departamento el departamento al que pertenece la ciudad (tamaño 2)
     * @param opcion la opcion es un boolean, cuando es verdadero es agregar y cuando es false editar
     */
    
    public void insertaEditarCiudad(String ciudad,String nombre,String departamento,boolean opcion){
        if(opcion==true){
        sql = "insert into ciudad (nombre,departamento,ciudad) VALUES(?,?,?)";
        }
        else
        {
        sql = "UPDATE ciudad SET nombre = ?,departamento = ? WHERE  ciudad = ?";
        }
        
        try {            
            statement = conexion.getConexion().prepareStatement(sql);
            statement.setString(1, nombre);
            statement.setString(2, departamento);
            statement.setString(3, ciudad);
                                   
            
            if(opcion==true) statement.execute();
            else statement.executeUpdate();
            
            statement.close();
            JOptionPane.showMessageDialog(null, "Se ha añadido/modificado correctamente la ciudad");
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, ex);
        }
        
    
    }
    
    /**
     * Obtiene una tabla modelo con todas las ciudades registradas en el sistema
     * @return debe ser asignada a la tabla encargada de mostrar todas las ciudades
     */
    
    public DefaultTableModel obtenerCiudad(){
        
        ResultSet res=null;
        sql = "select * from ciudad";
         try {
             statement = (PreparedStatement) conexion.getConexion().prepareStatement(sql);
              res = statement.executeQuery();
              
              
             
         } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, ex);
         }
     
         return Utilidades.construirTabla(res);
    }
    
    /**
     * Obtiene una ciudad de acuerdo a su identificador unico
     * @param ciudad identificador unico de la ciudad
     * @return devuelve todos los campos de la ciudad, para que sean asigandos de nuevo a los campos en la vista
     */
    
    public ResultSet obtenerCiudad(String ciudad){
        sql = "select * from ciudad where ciudad = "+ ciudad ;
         ResultSet res=null;
         try {
             PreparedStatement pstm = (PreparedStatement) conexion.getConexion().prepareStatement(sql);
              res = pstm.executeQuery();
              
         } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, ex);
         }
     
         return res;
        
     }
     
    /**
     * Elimina una ciudad de acuerdo a su identificador unico
     * @param ciudad identificador de la ciudad a eliminar
     */
    
    public void eliminarCiudad(String ciudad){
    sql = "DELETE from ciudad WHERE ciudad = " + ciudad;
    
        try {
            statement = (PreparedStatement) conexion.getConexion().prepareStatement(sql);
            statement.execute();
            statement.close();
            JOptionPane.showMessageDialog(null, "Se ha eliminado correctamente la ciudad");
        } catch (SQLException ex) {
            Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }
    
    
}
