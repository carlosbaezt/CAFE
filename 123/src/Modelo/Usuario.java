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
 * Esta clase define los metodos necesarios para realizar las operacions basicas en sql de la tabla usuarios
 * @author Carlos Baez
 */
public class Usuario {
    
    ConexionBD conexion = null;
    PreparedStatement statement  = null;
    String sql;
    
    public Usuario(){
    conexion = ConexionBD.obtenerInstancia();    
    }
    
    /**
     * Metodo que permite agregar o editar un usuario
     * @param cedula identificador unico de los usuarios
     * @param nombres nombre del usuario
     * @param apellidos apellidos  del usuario
     * @param direccion lugar de residencia
     * @param telefono forma de contacto 
     * @param fechaNacimiento fecha de nacimiento del usuario
     * @param experienciaLaboral breve descripcion de la experiencia laboral  
     * @param nivelAcademico breve descripcion de la vida academica
     * @param ciudad identificador de la ciudad donde vive el usuario
     * @param opcion la opcion es un boolean, cuando es verdadero es agregar y cuando es false editar
     */
    
    public void insertaEditarUsuario(int cedula,String nombres,String apellidos, String direccion,String telefono,Date fechaNacimiento,String experienciaLaboral,String nivelAcademico,String ciudad,boolean opcion){
        if(opcion==true){
        sql = "insert into usuario (nombres,apellidos,direccion,telefono,fecha_nacimiento,experiencia_laboral,nivel_academico,ciudad,cedula) VALUES (?,?,?,?,?,?,?,?,?)";
        }
        else
        {
        sql = "UPDATE usuario SET nombres = ?,apellidos = ?,direccion = ?, telefono = ?, fecha_nacimiento = ?, experiencia_laboral = ?,nivel_academico = ?,ciudad = ? WHERE  cedula = ?";
        }
        
        try {            
            statement = conexion.getConexion().prepareStatement(sql);
            statement.setString(1, nombres);
            statement.setString(2, apellidos);
            statement.setString(3, direccion);
            statement.setString(4, telefono);           
            statement.setDate(5, Utilidades.convertirFecha(fechaNacimiento));
            statement.setString(6, experienciaLaboral);
            statement.setString(7, nivelAcademico);
            statement.setString(8, ciudad);
            statement.setInt(9, cedula);
                        
            
            if(opcion==true) statement.execute();
            else statement.executeUpdate();
            
            statement.close();
            JOptionPane.showMessageDialog(null, "Se ha añadido/modificado correctamente el usuario");
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, ex);
        }
        
    
    }
    
    
    /**
     * Obtiene una tabla modelo con todos los usuarios registradas en el sistema
     * @return debe ser asignada a la tabla encargada de mostrar todas los usuarios
     */
    
    public DefaultTableModel obtenerUsuario(){
        
        ResultSet res=null;
        sql = "select * from usuario";
         try {
             PreparedStatement pstm = (PreparedStatement) conexion.getConexion().prepareStatement(sql);
              res = pstm.executeQuery();
              
              
             
         } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, ex);
         }
     
         return Utilidades.construirTabla(res);
    }
    
     /**
     * Obtiene un usuario de acuerdo a su identificador unico
     * @param cedula identificador unico del usuario (tamaño 2)
     * @return devuelve todos los campos del usuario, para que sean asigandos de nuevo a los campos en la vista
     */
    
    public ResultSet obtenerUsuario(int cedula){
        sql = "select * from usuario where cedula = "+ cedula ;
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
     * Elimina un usuario de acuerdo a su identificado unico 
     * @param cedula identificador unico del usuario (tamaño 12)
     */
    public void eliminarUsuario(int cedula){
    sql = "DELETE from usuario WHERE cedula = " + cedula;
    
        try {
            statement = (PreparedStatement) conexion.getConexion().prepareStatement(sql);
            statement.execute();
            statement.close();
            JOptionPane.showMessageDialog(null, "Se ha eliminado correctamente el usuario");
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }
    
    
}
