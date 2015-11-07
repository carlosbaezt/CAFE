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
 *
 * @author Carlos Baez
 */
public class Vacante {

    ConexionBD conexion = null;
    PreparedStatement statement = null;
    String sql;

    public Vacante() {
        conexion = ConexionBD.obtenerInstancia();
    }

    /**
     * Metodo que permite guardar o editar una vacante
     *
     * @param id identificador unico de la vacante
     * @param nit nit de la empresa que genera la vacante
     * @param cargo cargo que se solicita
     * @param ciudad ciudad donde se necesita
     * @param salario sueldo que tiene dicha vacante
     * @param opcion la opcion es un boolean, cuando es verdadero es agregar y
     * cuando es false editar
     */
    public void insertaEditarVacante(int id, int nit, String cargo, String ciudad, double salario, boolean opcion) {
        if (opcion == true) {
            sql = "insert into vacante (nit,cargo,ciudad,salario,id) VALUES(?,?,?,?,?)";
        } else {
            sql = "UPDATE vacante SET nit = ? , cargo = ?, ciudad = ?, salario = ? WHERE  id = ?";
        }

        try {
            statement = conexion.getConexion().prepareStatement(sql);
            statement.setInt(1, nit);
            statement.setString(2, cargo);
            statement.setString(3, ciudad);
            statement.setDouble(4, salario);
            statement.setInt(5, id);

            if (opcion == true) {
                statement.execute();
            } else {
                statement.executeUpdate();
            }

            statement.close();
            JOptionPane.showMessageDialog(null, "Se ha añadido correctamente/modificado la vacante");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    /**
     * Obtiene una tabla modelo con todos las vacantes registradas en el sistema
     *
     * @return debe ser asignada a la tabla encargada de mostrar todas las
     * vacabntes
     */
    public DefaultTableModel obtenerVacante() {

        ResultSet res = null;
        sql = "select * from vacante";
        try {
            PreparedStatement pstm = (PreparedStatement) conexion.getConexion().prepareStatement(sql);
            res = pstm.executeQuery();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

        return Utilidades.construirTabla(res);
    }

    /**
     * Obtiene una vacante de acuerdo a su identificador unico
     *
     * @param id identificador unico del vacante (tamaño 5)
     * @return devuelve todos los campos de la vacante, para que sean asigandos
     * de nuevo a los campos en la vista
     */
    public ResultSet obtenerVacante(int id) {
        sql = "select * from vacante where id = " + id;
        ResultSet res = null;
        try {
            PreparedStatement pstm = (PreparedStatement) conexion.getConexion().prepareStatement(sql);
            res = pstm.executeQuery();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

        return res;

    }

    /**
     * Elimina una vacante de acuerdo a su identificado unico
     *
     * @param id identificador unico del usuario (tamaño 5)
     */
    public void eliminarVacante(int id) {
        sql = "DELETE from vacante WHERE id = " + id;

        try {
            statement = (PreparedStatement) conexion.getConexion().prepareStatement(sql);
            statement.execute();
            statement.close();
            JOptionPane.showMessageDialog(null, "Se ha eliminado correctamente la vacante");
        } catch (SQLException ex) {
            Logger.getLogger(Vacante.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ResultSet obtenerTodo() {
        sql = "SELECT * FROM vacante";
        ResultSet rs = null;

        try {
            PreparedStatement pstm = (PreparedStatement) conexion.getConexion().prepareStatement(sql);
            rs = pstm.executeQuery();
        } catch (Throwable ex) {
            Logger.getLogger(Departamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

}
