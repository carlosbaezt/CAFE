package Modelo;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;


import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


public class Reporte {
    String titulo="";
    
    
    /**
     * 
     * @param titulo Titulo que va a tener el reporte
     */
    public Reporte(String titulo){
            this.titulo = titulo;
    
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
//Configuración de la conexión.
        String driver = "com.mysql.jdbc.Driver";
        String connectString = "jdbc:mysql://localhost/empleo";
        String user = "root";
        String password = "";
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(connectString, user, password);
        return conn;
    }

    public void imprimirReporte(String directorio,int nit,int cedula) {
//La Ruta de nuestro reporte
        String filejasper = directorio;
        try {
//cargamos parametros del reporte (si tiene).
            Map parameters = new HashMap();

//parameters.put("REPORT_LOCALE",new java.util.Locale("es","CL"));
            parameters.put("idd", directorio);
            parameters.put("nit", nit);
            parameters.put("cedula", cedula);
            
            JasperDesign jasperDesign = JRXmlLoader.load(filejasper);
//Compilar el Reporte.
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
//Preparacion del reporte (en esta etapa se inserta el valor del query en el reporte).
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, getConnection());
//Cargar reporte en el visor.
            JasperViewer jasperviewer = new JasperViewer(jasperPrint, false);
//Le ponemos un titulo personalizado al visor, y desplegamos el reporte.
            jasperviewer.setTitle(titulo);
            jasperviewer.show();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

}
