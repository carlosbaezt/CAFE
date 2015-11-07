/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos Baez
 */
public class Utilidades {

    static java.sql.Date sqlDate;
/**
 * Permite la conversion de objetos util.Date a sql.Date (Para almacenar en la bd objetos tipo Date)
 * @param fecha fecha que se desea convertir a sql.Date
 * @return fecha lista para ser almacenada en sql
 */
   
    public static java.sql.Date convertirFecha(java.util.Date fecha) {
        sqlDate = new java.sql.Date(fecha.getTime());
        return sqlDate;
    }
    
    /**
     * Metodo que permite pasar de un objeto resultset obtenido de sql  a un tabla modelo
     * @param rs resulset obtenido despues de una consulta sql
     * @return tabla modelo que debe ser asignada a la tabla de la vista
     */

    public static DefaultTableModel construirTabla(ResultSet rs){
    Vector<Vector<Object>> data=null;
    Vector<String> columnNames=null;
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            
            // names of columns
            columnNames = new Vector<String>();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                columnNames.add(metaData.getColumnName(column));
            }

            // data of the table
            data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }
            
           
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Eror al cargar la tabla");
        }
        return new DefaultTableModel(data, columnNames);
    }

}
