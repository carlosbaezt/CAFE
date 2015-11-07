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
 * Esta clase define los metodos necesarios para realizar las operacions basicas en sql de la tabla empresa
 * @author Carlos Baez
 */
public class Empresa {
    
    ConexionBD conexion = null;
    PreparedStatement statement  = null;
    String sql;
    
    public Empresa(){
    conexion = ConexionBD.obtenerInstancia();    
    }
    
    /**
     *  Metodo que permite agregar o editar una empresa
     * @param nit identificador unico de la empresa, entero
     * @param nombre nombre de la empresa
     * @param direccion lugar donde se encuentra ubicado
     * @param ciudad ciudad donde se localiza la empresa
     * @param telefono forma de contacto 
     * @param correo forma de contacto 
     * @param opcion la opcion es un boolean, cuando es verdadero es agregar y cuando es false editar
     */
    
    public void insertaEditarEmpresa(int nit,String nombre,String direccion,String ciudad,String telefono,String correo,boolean opcion){
        if(opcion==true){
        sql = "insert into empresa (nombre,direccion,ciudad,telefono,correo,nit) VALUES(?,?,?,?,?,?)";
        }
        else
        {
        sql = "UPDATE empresa SET nombre = ? , direccion = ?, ciudad = ?, telefono = ?, correo = ? WHERE  nit = ?";
        }
        
        try {            
            statement = conexion.getConexion().prepareStatement(sql);
            statement.setString(1,nombre);
            statement.setString(2, direccion);
            statement.setString(3, ciudad);        
            statement.setString(4, telefono);
            statement.setString(5, correo);
            statement.setInt(6, nit);
            
                          
            if(opcion==true) statement.execute();
            else statement.executeUpdate();
            
            statement.close();
            JOptionPane.showMessageDialog(null, "Se ha añadido correctamente/modificado la empresa");
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, ex);
        }
        
    
    }
    
    /**
     * Obtiene una tabla modelo con todas las empresas registradas en el sistema
     * @return debe ser asignada a la tabla encargada de mostrar todas las empresas
     */
    public DefaultTableModel obtenerEmpresa(){
        
        ResultSet res=null;
        sql = "select * from empresa";
         try {
             PreparedStatement pstm = (PreparedStatement) conexion.getConexion().prepareStatement(sql);
              res = pstm.executeQuery();
              
              
             
         } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, ex);
         }
     
         return Utilidades.construirTabla(res);
    }
    
    
    /**
     * Obtiene una empresa de acuerdo a su identificador unico (nit)
     * @param nit identificador unico de la empresa
     * @return devuelve todos los campos de la empresa, para que sean asigandos de nuevo a los campos en la vista
     */
    
    public ResultSet obtenerEmpresa(int nit){
        sql = "select * from empresa where nit = "+ nit ;
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
     * elimina una empresa de acuerdo a su identificador unico (nit)
     * @param nit identificador unico de la empresa
     */
     
    public void eliminarEmpresa(int nit){
    sql = "DELETE from empresa WHERE nit = " + nit;
    
        try {
            statement = (PreparedStatement) conexion.getConexion().prepareStatement(sql);
            statement.execute();
            statement.close();
            JOptionPane.showMessageDialog(null, "Se ha eliminado correctamente la empresa");
        } catch (SQLException ex) {
            Logger.getLogger(Empresa.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }
    
       public ResultSet obtenerTodo() {
        sql = "SELECT * FROM empresa";
        ResultSet rs=null;
        
        try {
            PreparedStatement pstm = (PreparedStatement) conexion.getConexion().prepareStatement(sql);
            rs = pstm.executeQuery();
        } catch (Throwable ex) {
            Logger.getLogger(Departamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    
}
