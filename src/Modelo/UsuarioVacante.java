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
 * Clase que implementa los metodos basico en sql para la tabla usuario vacante
 * @author Carlos Baez
 */
public class UsuarioVacante {
    
    ConexionBD conexion = null;
    PreparedStatement statement  = null;
    String sql;
    
    public UsuarioVacante(){
    conexion = ConexionBD.obtenerInstancia();    
    }
    
    
    /**
     * Metodo que permite agregar los usuarios a las vacantes existentes en el sistema
     * @param idVacante identificador de la vacante
     * @param cedula cedula del usuario 
     * @param idVacanteAnterior (opcional) en caso de actualizar,se debe colocar la vacante anterior a la que estaba inscrito
     * @param cedulaAnterior (opcional) en caso de actualizar,se debe colocar la cedula anterior del usuario
     * @param opcion la opcion es un boolean, cuando es verdadero es agregar y cuando es false editar
     */
    public void insertaEditarUsuarioVacante(int idVacante,int cedula,int idVacanteAnterior,int cedulaAnterior,boolean opcion){
        if(opcion==true){
        sql = "insert into usuariovacante (cedula,id_vacante) VALUES(?,?)";
        }
        else
        {
        sql = "update usuariovacante set cedula=?,id_vacante= ? where(cedula = ? and id_vacante=?)";
        }
        
        try {            
            statement = conexion.getConexion().prepareStatement(sql);
            statement.setInt(1, cedula);
            statement.setInt(2,idVacante);
            
                          
            if(opcion==true) statement.execute();
            else {
                statement.setInt(3,cedulaAnterior);
                statement.setInt(4, idVacanteAnterior);
                statement.executeUpdate();
            }
            
            statement.close();
            JOptionPane.showMessageDialog(null, "Se ha añadido correctamente/modificado el usuario registrado a la vacante");
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, ex);
        }
        
    
    }
    
    
 /**
     * Obtiene una tabla modelo con todos los usuarios registrados a las vacantes en el sistema
     * @return debe ser asignada a la tabla encargada de mostrar todas los usuarios
     */
    
    public DefaultTableModel obtenerUsuarioVacante(){
        
        ResultSet res=null;
        sql = "select * from usuariovacante";
         try {
             PreparedStatement pstm = (PreparedStatement) conexion.getConexion().prepareStatement(sql);
              res = pstm.executeQuery();
              
              
             
         } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, ex);
         }
     
         return Utilidades.construirTabla(res);
    }
    
    
    /**
     * Obtiene un usuario registrado a una vacante de acuerdo a sus identificadores unicos
     * @param idVacante identificador unico de la vacante
     * @param cedula identifador unico del usuario
     * @return  devuelve todos los campos del usuariovacante, para que sean asigandos de nuevo a los campos en la vista
     */
    public ResultSet obtenerUsuarioVacante(int idVacante,int cedula){
        sql = "select * from usuariovacante where id_vacante = "+ idVacante +" and cedula = "+cedula;
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
     * Metodo que elimina un usuario de una vacante 
     * @param idVacante identificador unico de  la vacante 
     * @param cedula identificador unico del usuario
     */
    public void eliminarUsuarioVacante(int idVacante,int cedula){
    sql = "DELETE from usuariovacante where id_vacante = "+ idVacante +" and cedula = "+cedula;
    
        try {
            statement = (PreparedStatement) conexion.getConexion().prepareStatement(sql);
            statement.execute();
            statement.close();
            JOptionPane.showMessageDialog(null, "Se ha eliminado correctamente el usuario de la vacante");
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioVacante.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }
    
    
}
