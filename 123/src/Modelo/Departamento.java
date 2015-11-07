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
 *Esta clase define los metodos necesarios para realizar las operacions basicas en sql de la tabla departamento
 * @author Carlos Baez
 */
public class Departamento {
    
    ConexionBD conexion = null;
    PreparedStatement statement  = null;
    String sql;
    
    public Departamento(){
        
    conexion = ConexionBD.obtenerInstancia();    
    }
    
    /**
     * Metodo que permite agregar o editar un departamento
     * @param nombre nombre del departamento
     * @param departamento identificador unico del departamento (tamaño 2)
     * @param opcion  la opcion es un boolean, cuando es verdadero es agregar y cuando es false editar
     */
    
    public void insertaEditarDepartamento(String nombre,String departamento,boolean opcion){
        if(opcion==true){
        sql = "insert into departamento (nombre,departamento) VALUES(?,?)";
        }
        else
        {
        sql = "UPDATE departamento SET nombre = ? WHERE  departamento = ?";
        }
        
        try {            
            statement = conexion.getConexion().prepareStatement(sql);
            statement.setString(1, nombre);
            statement.setString(2, departamento);
          
                                   
            
            if(opcion==true) statement.execute();
            else statement.executeUpdate();
            
            statement.close();
            JOptionPane.showMessageDialog(null, "Se ha añadido/modificado correctamente el departamento");
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, ex);
        }
        
    
    }
    
    /**
     * Obtiene una tabla modelo con todos los departamentos registradas en el sistema
     * @return debe ser asignada a la tabla encargada de mostrar todas los departamentos
     */
    
    public DefaultTableModel obtenerDepartamento(){
        
        ResultSet res=null;
        sql = "select * from departamento";
         try {
             PreparedStatement pstm = (PreparedStatement) conexion.getConexion().prepareStatement(sql);
              res = pstm.executeQuery();
              
              
             
         } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, ex);
         }
     
         return Utilidades.construirTabla(res);
    }
    
    /**
     * Obtiene un departamento de acuerdo a su identificador unico
     * @param departamento identificador unico del departamento (tamaño 2)
     * @return devuelve todos los campos del departamento, para que sean asigandos de nuevo a los campos en la vista
     */
    
    
    public ResultSet obtenerDepartamento(String departamento){
        sql = "select * from departamento where departamento = "+ departamento ;
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
     * Elimina un departamento de acuerdo a su identificador unico
     * @param departamento identificador unico del departamento (tamaño 2)
     */
    public void eliminarDepartamento(String departamento){
    sql = "DELETE from departamento WHERE departamento = " + departamento;
    
        try {
            statement = (PreparedStatement) conexion.getConexion().prepareStatement(sql);
            statement.execute();
            statement.close();
            JOptionPane.showMessageDialog(null, "Se ha eliminado correctamente el departamento");
        } catch (SQLException ex) {
            Logger.getLogger(Departamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }
    
    
}
