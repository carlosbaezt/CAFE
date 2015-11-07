package Control;

/**
 *
 * @author FranciscoJavier
 */
import Modelo.*;
import Vista.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class Control {
    /* Declaracion de variabes */
    private Modelo.UsuarioVacante usvac = new UsuarioVacante();
    private DefaultComboBoxModel dcbm;
    private DefaultComboBoxModel dcbm2;
    private Modelo.Vacante vacante = new Modelo.Vacante();
    private Modelo.Departamento dpto = new Modelo.Departamento();
    private Modelo.Ciudad ciudad = new Modelo.Ciudad();
    private Modelo.Empresa empresa = new Empresa();
    private Modelo.Usuario user = new Modelo.Usuario();
    private Reporte reporte;
    private Principal vistaP = new Principal();
    private PrincipalEmpresas vistaPEmp = new PrincipalEmpresas();
    private OperacionesEmpresas opsEmpresas = new OperacionesEmpresas();
    private Vacantes vacantes = new Vacantes();
    private PrincipalUsuarios usuarios = new PrincipalUsuarios();
    private OperacionesUsuarios opsUs = new OperacionesUsuarios();
    private PrincipalReportes reportes = new PrincipalReportes();
    private Ciudades ciudades = new Ciudades();
    private Departamentos departamentos = new Departamentos();

    /* Declaracion de constructores*/
    public Control() {
        /*Adicion de listener a la vista principal*/
        cargarListeners();
    }

    /* Declaracion de metodos*/
    private void cargarListeners() {
        this.listenersVistaP();
        this.listenersVistaPEmp();
        this.listenersOpsEmpresas();
        this.listenersVacantes();
        this.listenersUsuarios();
        this.listenersOpsUs();
        this.listenerReportes();
        this.listenersCiudades();
        this.listenersDepartamentos();
    }

    /**
     * Este metodo inicia el programa general
     *
     * @param bandera True si se desea iniciar el programa
     */
    public void iniciar() {        
            vistaP.setVisible(true);        
    }

    /**
     * Se encarga de controlar todos los eventos que aparecen en la vista
     * principal
     */
    private void eventosVistaP(ActionEvent e) {
        
        if(e.getSource()==vistaP.getBtnNumeroCiudades()){
        JOptionPane.showMessageDialog(null, "El numero de ciudades es: "+ciudad.obtenerNumeroCiudades());
        }
        if (e.getSource() == vistaP.getBtnSalir()) {
       System.exit(0);
        } else if (e.getSource() == vistaP.getBtnEmpresas()) {
            vistaPEmp.setVisible(true);
            vistaP.setVisible(false);
        } else if (e.getSource() == vistaP.getBtnUsuarios()) {
            vistaP.setVisible(false);
            usuarios.setVisible(true);
        } else if (e.getSource() == vistaP.getBtnReportes()) {
            vistaP.setVisible(false);
            reportes.setVisible(true);
        } else if (e.getSource() == vistaP.getMnCiudades()) {
            vistaP.setVisible(false);
            ciudades.setVisible(true);
        } else if (e.getSource() == vistaP.getMnDepartamentos()) {
            vistaP.setVisible(false);
            departamentos.setVisible(true);
        }
    }

    /**
     * Se encarga de controlar todos los eventos que aparecen en la vista
     * principal de las empresas
     */
    private void eventosVistaPEmp(ActionEvent e) {
        if (e.getSource() == vistaPEmp.getBtnRegresar()) {
            vistaPEmp.setVisible(false);
            vistaP.setVisible(true);
        } else if (e.getSource() == vistaPEmp.getBtnOperaciones()) {
            opsEmpresas.setVisible(true);
            vistaPEmp.setVisible(false);
        } else if (e.getSource() == vistaPEmp.getBtnVacantes()) {
            vacantes.setVisible(true);
            vistaPEmp.setVisible(false);
        }
    }

    /**
     * Se encarga de controlar todos los eventos que aparecen en la vista de
     * operaciones con empresas
     */
    private void eventosOpsEmpresas(WindowEvent e) {
        opsEmpresas.getTblListadoEmpresas().setModel(empresa.obtenerEmpresa());
        String datos[] = datosCBOCiudad();
        int rows = ciudad.obtenerCiudad().getRowCount();
        dcbm2 = new DefaultComboBoxModel();
        for (int i = 0; i < rows; i++) {
            dcbm2.addElement(datos[i]);
        }
        opsEmpresas.getCboCiudades().setModel(dcbm2);
    }

    private void eventosOpsEmpresas(MouseEvent e) {
        int r = opsEmpresas.getTblListadoEmpresas().getSelectedRow();
        String res1 = opsEmpresas.getTblListadoEmpresas().getModel().getValueAt(r, 0).toString();
        String res2 = opsEmpresas.getTblListadoEmpresas().getModel().getValueAt(r, 1).toString();
        String res3 = opsEmpresas.getTblListadoEmpresas().getModel().getValueAt(r, 2).toString();
        String res4 = opsEmpresas.getTblListadoEmpresas().getModel().getValueAt(r, 3).toString();
        String res5 = opsEmpresas.getTblListadoEmpresas().getModel().getValueAt(r, 4).toString();
        String res6 = opsEmpresas.getTblListadoEmpresas().getModel().getValueAt(r, 5).toString();
        opsEmpresas.getTxtCorreoE().setEnabled(true);
        opsEmpresas.getTxtDireccion().setEnabled(true);
        opsEmpresas.getTxtNit().setEnabled(true);
        opsEmpresas.getTxtNombre().setEnabled(true);
        opsEmpresas.getTxtTelefono().setEnabled(true);
        opsEmpresas.getCboCiudades().setEnabled(true);
        opsEmpresas.getBtnGuardar().setEnabled(true);
        opsEmpresas.getBtnModificar().setEnabled(true);
        opsEmpresas.getBtnBorrar().setEnabled(true);
        opsEmpresas.getTxtNit().setText(res1);
        opsEmpresas.getTxtNombre().setText(res2);
        opsEmpresas.getTxtDireccion().setText(res3);
        opsEmpresas.getTxtTelefono().setText(res5);
        opsEmpresas.getTxtCorreoE().setText(res6);
    }

    private void eventosOpsEmpresas(ActionEvent e) {
        String nombre, direccion, telefono, idCiudad, correo;
        int nit;
        if (e.getSource() == opsEmpresas.getBtnRegresar()) {
            opsEmpresas.setVisible(false);
            vistaPEmp.setVisible(true);
        } else if (e.getSource() == opsEmpresas.getBtnNuevo()) {
            opsEmpresas.getTxtCorreoE().setEnabled(true);
            opsEmpresas.getTxtDireccion().setEnabled(true);
            opsEmpresas.getTxtNit().setEnabled(true);
            opsEmpresas.getTxtNombre().setEnabled(true);
            opsEmpresas.getTxtTelefono().setEnabled(true);
            opsEmpresas.getCboCiudades().setEnabled(true);
            opsEmpresas.getBtnGuardar().setEnabled(true);
        } else if (e.getSource() == opsEmpresas.getBtnCancelar()) {
            opsEmpresas.getTxtNit().setText("");
            opsEmpresas.getTxtNombre().setText("");
            opsEmpresas.getTxtDireccion().setText("");
            opsEmpresas.getTxtTelefono().setText("");
            opsEmpresas.getTxtCorreoE().setText("");
            opsEmpresas.getTxtCorreoE().setEnabled(false);
            opsEmpresas.getTxtDireccion().setEnabled(false);
            opsEmpresas.getTxtNit().setEnabled(false);
            opsEmpresas.getTxtNombre().setEnabled(false);
            opsEmpresas.getTxtTelefono().setEnabled(false);
            opsEmpresas.getCboCiudades().setEnabled(false);
            opsEmpresas.getBtnGuardar().setEnabled(false);
            opsEmpresas.getBtnModificar().setEnabled(false);
            opsEmpresas.getBtnBorrar().setEnabled(false);
        } else if (e.getSource() == opsEmpresas.getBtnGuardar()) {
            nombre = opsEmpresas.getTxtNombre().getText();
            direccion = opsEmpresas.getTxtDireccion().getText();
            telefono = opsEmpresas.getTxtTelefono().getText();
            int rows = ciudad.obtenerCiudad().getRowCount();
            ResultSet rs = ciudad.obtenerTodo();
            String dato[] = new String[rows];
            try {
                int x = 0;
                while (rs.next()) {
                    dato[x] = rs.getString("ciudad");
                    x++;
                }
            } catch (SQLException ex) {
                Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
            }
            idCiudad = dato[opsEmpresas.getCboCiudades().getSelectedIndex()];
            correo = opsEmpresas.getTxtCorreoE().getText();
            try {
                nit = Integer.parseInt(opsEmpresas.getTxtNit().getText());
                empresa.insertaEditarEmpresa(nit, nombre, direccion, idCiudad, telefono, correo, true);
                opsEmpresas.getTblListadoEmpresas().setModel(empresa.obtenerEmpresa());
            } catch (NumberFormatException x) {
                System.out.print(e);

            }
            opsEmpresas.getTblListadoEmpresas().setModel(empresa.obtenerEmpresa());
            opsEmpresas.getTxtNit().setText("");
            opsEmpresas.getTxtNombre().setText("");
            opsEmpresas.getTxtDireccion().setText("");
            opsEmpresas.getTxtTelefono().setText("");
            opsEmpresas.getTxtCorreoE().setText("");
            opsEmpresas.getTxtCorreoE().setEnabled(false);
            opsEmpresas.getTxtDireccion().setEnabled(false);
            opsEmpresas.getTxtNit().setEnabled(false);
            opsEmpresas.getTxtNombre().setEnabled(false);
            opsEmpresas.getTxtTelefono().setEnabled(false);
            opsEmpresas.getCboCiudades().setEnabled(false);
            opsEmpresas.getBtnGuardar().setEnabled(false);
            opsEmpresas.getBtnModificar().setEnabled(false);
            opsEmpresas.getBtnBorrar().setEnabled(false);
        } else if (e.getSource() == opsEmpresas.getBtnModificar()) {
            nombre = opsEmpresas.getTxtNombre().getText();
            direccion = opsEmpresas.getTxtDireccion().getText();
            telefono = opsEmpresas.getTxtTelefono().getText();
            int rows = ciudad.obtenerCiudad().getRowCount();
            ResultSet rs = ciudad.obtenerTodo();
            String dato[] = new String[rows];
            try {
                int x = 0;
                while (rs.next()) {
                    dato[x] = rs.getString("ciudad");
                    x++;
                }
            } catch (SQLException ex) {
                Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
            }
            idCiudad = dato[opsEmpresas.getCboCiudades().getSelectedIndex()];
            correo = opsEmpresas.getTxtCorreoE().getText();
            try {
                nit = Integer.parseInt(opsEmpresas.getTxtNit().getText());
                empresa.insertaEditarEmpresa(nit, nombre, direccion, idCiudad, telefono, correo, false);
            } catch (NumberFormatException x) {
                System.out.print(x);
            }
            opsEmpresas.getTblListadoEmpresas().setModel(empresa.obtenerEmpresa());
            opsEmpresas.getTxtNit().setText("");
            opsEmpresas.getTxtNombre().setText("");
            opsEmpresas.getTxtDireccion().setText("");
            opsEmpresas.getTxtTelefono().setText("");
            opsEmpresas.getTxtCorreoE().setText("");
            opsEmpresas.getTxtCorreoE().setEnabled(false);
            opsEmpresas.getTxtDireccion().setEnabled(false);
            opsEmpresas.getTxtNit().setEnabled(false);
            opsEmpresas.getTxtNombre().setEnabled(false);
            opsEmpresas.getTxtTelefono().setEnabled(false);
            opsEmpresas.getCboCiudades().setEnabled(false);
            opsEmpresas.getBtnGuardar().setEnabled(false);
            opsEmpresas.getBtnModificar().setEnabled(false);
            opsEmpresas.getBtnBorrar().setEnabled(false);

        } else if (e.getSource() == opsEmpresas.getBtnBorrar()) {
            try {
                nit = Integer.parseInt(opsEmpresas.getTxtNit().getText());
                empresa.eliminarEmpresa(nit);
                opsEmpresas.getTblListadoEmpresas().setModel(empresa.obtenerEmpresa());
            } catch (Exception x) {
                System.out.print(x);
            }
            opsEmpresas.getTxtNit().setText("");
            opsEmpresas.getTxtNombre().setText("");
            opsEmpresas.getTxtDireccion().setText("");
            opsEmpresas.getTxtTelefono().setText("");
            opsEmpresas.getTxtCorreoE().setText("");
            opsEmpresas.getTxtCorreoE().setEnabled(false);
            opsEmpresas.getTxtDireccion().setEnabled(false);
            opsEmpresas.getTxtNit().setEnabled(false);
            opsEmpresas.getTxtNombre().setEnabled(false);
            opsEmpresas.getTxtTelefono().setEnabled(false);
            opsEmpresas.getCboCiudades().setEnabled(false);
            opsEmpresas.getBtnGuardar().setEnabled(false);
            opsEmpresas.getBtnModificar().setEnabled(false);
            opsEmpresas.getBtnBorrar().setEnabled(false);
        }

    }

    /**
     * Se encarga de controlar los eventos que pasan en la principal de los
     * reportes
     */
    private void eventosReportes(ActionEvent e) {
        if (e.getSource() == reportes.getBtnUsuariosRegistrados()) {
            reporte = new Reporte("Usuarios registrados");
            String ruta = "src/Reportes/usuarios.jrxml";
            reporte.imprimirReporte(ruta,0,0);

        } else if (e.getSource() == reportes.getBtnVerVacantes()) {
            reporte = new Reporte("vacantes por empresa");
            String ruta = "src/Reportes/Vacantes.jrxml";
            int nit = Integer.parseInt(JOptionPane.showInputDialog(null, "Nit "));
            reporte.imprimirReporte(ruta, nit, 0);
        } else if (e.getSource() == reportes.getBtnVerUsuarioEnVacantes()) {
            reporte = new Reporte("vacantes inscritas  por una persona");
            String ruta = "src/Reportes/VacantesPorUsuario.jrxml";
            int cedula = Integer.parseInt(JOptionPane.showInputDialog(null, "Cedula "));
            reporte.imprimirReporte(ruta, 0, cedula);

        }else if (e.getSource() == reportes.getBtnRegresar()) {
            vistaP.setVisible(true);
            reportes.setVisible(false);
        }
    }

    /**
     * Se encarga de controlar todos los eventos que aparecen en la vista de las
     * vacantes
     */
    private void eventosVacantes(MouseEvent e) {
        vacantes.getTxtCargo().setEnabled(true);
        vacantes.getTxtIDVacante().setEnabled(true);
        

        vacantes.getTxtSalario().setEnabled(true);
        vacantes.getBtnBorrar().setEnabled(true);
        vacantes.getBtnCancelar().setEnabled(true);
        vacantes.getBtnModificar().setEnabled(true);
        vacantes.getCboCiudad().setEnabled(true);
        int r = vacantes.getTblResultados().getSelectedRow();
        String res1 = vacantes.getTblResultados().getModel().getValueAt(r, 0).toString();
        String res2 = vacantes.getTblResultados().getModel().getValueAt(r, 1).toString();
        String res3 = vacantes.getTblResultados().getModel().getValueAt(r, 2).toString();
        String res4 = vacantes.getTblResultados().getModel().getValueAt(r, 3).toString();
        String res5 = vacantes.getTblResultados().getModel().getValueAt(r, 4).toString();
        String res6 = vacantes.getTblResultados().getModel().getValueAt(r, 5).toString();
        vacantes.getTxtIDVacante().setText(res1);
        
        vacantes.getTxtCargo().setText(res3);
        vacantes.getTxtSalario().setText(res5);
    }

    private void eventosVacantes(ActionEvent e) {
        if (e.getSource() == vacantes.getBtnBorrar()) {
            int id = Integer.parseInt(vacantes.getTxtIDVacante().getText());
            vacante.eliminarVacante(id);
            vacantes.getTblResultados().setModel(vacante.obtenerVacante());
            /**/
            vacantes.getTxtCargo().setText("");
            vacantes.getTxtIDVacante().setText("");
        

            vacantes.getTxtSalario().setText("");
            vacantes.getBtnBorrar().setEnabled(false);
            vacantes.getBtnCancelar().setEnabled(false);
            vacantes.getBtnGuardar().setEnabled(false);
            vacantes.getBtnModificar().setEnabled(false);
            vacantes.getCboCiudad().setEnabled(false);
        } else if (e.getSource() == vacantes.getBtnCancelar()) {
            vacantes.getTxtCargo().setText("");
            vacantes.getTxtIDVacante().setText("");
        

            vacantes.getTxtSalario().setText("");
            vacantes.getBtnBorrar().setEnabled(false);
            vacantes.getBtnCancelar().setEnabled(false);
            vacantes.getBtnGuardar().setEnabled(false);
            vacantes.getBtnModificar().setEnabled(false);
            vacantes.getCboCiudad().setEnabled(false);
        } else if (e.getSource() == vacantes.getBtnGuardar()) {
            int idVacante, nitEmpresa;
            String cargo, nombre, idCiudad;
            double salario;
            idVacante = Integer.parseInt(vacantes.getTxtIDVacante().getText());
            salario = Double.parseDouble(vacantes.getTxtSalario().getText());
            
            //nitEmpresa = Integer.parseInt(vacantes.getTxtNit().getText());
            cargo = vacantes.getTxtCargo().getText();
            
            int rows = ciudad.obtenerCiudad().getRowCount();
            String[] datos = new String[rows];
            ResultSet rs = ciudad.obtenerTodo();
            try {
                int x = 0;
                while (rs.next()) {
                    datos[x] = rs.getString("ciudad");
                    x++;
                }
            } catch (SQLException ex) {
                Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
            }
            idCiudad = datos[vacantes.getCboCiudad().getSelectedIndex()];
            vacante.insertaEditarVacante(idVacante, nitEmpresa, cargo, idCiudad, salario, true);
            vacantes.getTblResultados().setModel(vacante.obtenerVacante());
            vacantes.getTxtCargo().setText("");
            vacantes.getTxtIDVacante().setText("");
            

            vacantes.getTxtSalario().setText("");
            vacantes.getBtnBorrar().setEnabled(false);
            vacantes.getBtnCancelar().setEnabled(false);
            vacantes.getBtnGuardar().setEnabled(false);
            vacantes.getBtnModificar().setEnabled(false);
            vacantes.getCboCiudad().setEnabled(false);

        } else if (e.getSource() == vacantes.getBtnModificar()) {
            int idVacante, nitEmpresa;
            String cargo, nombre, idCiudad;
            double salario;
            idVacante = Integer.parseInt(vacantes.getTxtIDVacante().getText());
            salario = Double.parseDouble(vacantes.getTxtSalario().getText());
            
            cargo = vacantes.getTxtCargo().getText();

            int rows = ciudad.obtenerCiudad().getRowCount();
            String[] datos = new String[rows];
            ResultSet rs = ciudad.obtenerTodo();
            try {
                int x = 0;
                while (rs.next()) {
                    datos[x] = rs.getString("ciudad");
                    x++;
                }
            } catch (SQLException ex) {
                Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
            }
            idCiudad = datos[vacantes.getCboCiudad().getSelectedIndex()];
            vacante.insertaEditarVacante(idVacante, nitEmpresa, cargo, idCiudad, salario, false);
            vacantes.getTblResultados().setModel(vacante.obtenerVacante());
            vacantes.getTxtCargo().setText("");
            vacantes.getTxtIDVacante().setText("");
            

            vacantes.getTxtSalario().setText("");
            vacantes.getBtnBorrar().setEnabled(false);
            vacantes.getBtnCancelar().setEnabled(false);
            vacantes.getBtnGuardar().setEnabled(false);
            vacantes.getBtnModificar().setEnabled(false);
            vacantes.getCboCiudad().setEnabled(false);

        } else if (e.getSource() == vacantes.getBtnNueva()) {
            vacantes.getTxtCargo().setEnabled(true);
            vacantes.getTxtIDVacante().setEnabled(true);
            vacantes.getCmbEmpresa().setEnabled(true);
            vacantes.getBtnCancelar().setEnabled(true);
            vacantes.getTxtSalario().setEnabled(true);
            vacantes.getBtnGuardar().setEnabled(true);
            vacantes.getCboCiudad().setEnabled(true);

        } else if (e.getSource() == vacantes.getBtnRegresar()) {
            vacantes.setVisible(false);
            vistaPEmp.setVisible(true);
        }
    }

    private void eventosVacantes(WindowEvent e) {
        vacantes.getTblResultados().setModel(vacante.obtenerVacante());
        String[] datosCiudades = datosCBOCiudad();
        DefaultComboBoxModel dcbo = new DefaultComboBoxModel();
        for (int i = 0; i < datosCiudades.length; i++) {
            dcbo.addElement(datosCiudades[i]);
        }
        vacantes.getCboCiudad().setModel(dcbo);
        
        
        String[] datosEmpresas = datosCBOEmpresas();
        DefaultComboBoxModel dcboEmpresa = new DefaultComboBoxModel();
        for (int i = 0; i < datosEmpresas.length; i++) {
            dcboEmpresa.addElement(datosEmpresas[i]);
        }
        vacantes.getCmbEmpresa().setModel(dcboEmpresa);
    }

    /**
     * Se encarga de controlar todos los eventos que aparecen en la vista de los
     * usuarios
     */
    private void eventosUsuarios(ActionEvent e) {
        if (e.getSource() == usuarios.getBtnOperaciones()) {
            opsUs.setVisible(true);
            usuarios.setVisible(false);
        } else if (e.getSource() == usuarios.getBtnPostular()) {
            String[] opciones = {"Nueva postulacion", "Editar", "Cancelar"};
            int opcion = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Postulaciones", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
            if (opcion == 0) {
                int cc = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el número de cedula del aspirante:"));
                int idvacante = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese la ID de la vacante:"));
                usvac.insertaEditarUsuarioVacante(idvacante, cc, idvacante, cc, true);
            } else if (opcion == 1) {
                int cc = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el número de cedula del aspirante:"));
                int idvacantea = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese la ID de la vacante anterior:"));
                int idvacante = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese la ID de la vacante nueva:"));
                usvac.insertaEditarUsuarioVacante(idvacante, cc, idvacantea, cc, false);
            }
            System.out.println(opcion);
        } else if (e.getSource() == usuarios.getBtnRegresar()) {
            usuarios.setVisible(false);
            vistaP.setVisible(true);
        }
    }

    private void eventosOpsUs(ActionEvent e) {
        if (e.getSource() == opsUs.getBtnNuevo()) {
            opsUs.getTxtApellidos().setEnabled(true);
            opsUs.getTxtCedula().setEnabled(true);
            opsUs.getTxtDireccion().setEnabled(true);
            opsUs.getTxtFechaN().setEnabled(true);
            opsUs.getTxtNlvAcademico().setEnabled(true);
            opsUs.getTxtNombres().setEnabled(true);
            opsUs.getTxtTelefono().setEnabled(true);
            opsUs.getTxtXpLab().setEnabled(true);
            opsUs.getBtnCancelar().setEnabled(true);
            opsUs.getBtnGuardar().setEnabled(true);
            opsUs.getCboCuidades().setEnabled(true);
        } else if (e.getSource() == opsUs.getBtnBorrar()) {
            int cc = Integer.parseInt(opsUs.getTxtCedula().getText());
            user.eliminarUsuario(cc);
            opsUs.getTblUsuarios().setModel(user.obtenerUsuario());
            opsUs.getTxtApellidos().setText("");
            opsUs.getTxtCedula().setText("");
            opsUs.getTxtDireccion().setText("");
            opsUs.getTxtFechaN().setText("");
            opsUs.getTxtNlvAcademico().setText("");
            opsUs.getTxtNombres().setText("");
            opsUs.getTxtTelefono().setText("");
            opsUs.getTxtXpLab().setText("");
            opsUs.getCboCuidades().setSelectedIndex(0);
            opsUs.getTxtApellidos().setEnabled(false);
            opsUs.getTxtCedula().setEnabled(false);
            opsUs.getTxtDireccion().setEnabled(false);
            opsUs.getTxtFechaN().setEnabled(false);
            opsUs.getTxtNlvAcademico().setEnabled(false);
            opsUs.getTxtNombres().setEnabled(false);
            opsUs.getTxtTelefono().setEnabled(false);
            opsUs.getTxtXpLab().setEnabled(false);
            opsUs.getBtnCancelar().setEnabled(false);
            opsUs.getBtnGuardar().setEnabled(false);
            opsUs.getBtnModificar().setEnabled(false);
            opsUs.getBtnBorrar().setEnabled(false);
            opsUs.getCboCuidades().setEnabled(false);
        } else if (e.getSource() == opsUs.getBtnCancelar()) {
            opsUs.getTxtApellidos().setText("");
            opsUs.getTxtCedula().setText("");
            opsUs.getTxtDireccion().setText("");
            opsUs.getTxtFechaN().setText("");
            opsUs.getTxtNlvAcademico().setText("");
            opsUs.getTxtNombres().setText("");
            opsUs.getTxtTelefono().setText("");
            opsUs.getTxtXpLab().setText("");
            opsUs.getCboCuidades().setSelectedIndex(0);
            opsUs.getTxtApellidos().setEnabled(false);
            opsUs.getTxtCedula().setEnabled(false);
            opsUs.getTxtDireccion().setEnabled(false);
            opsUs.getTxtFechaN().setEnabled(false);
            opsUs.getTxtNlvAcademico().setEnabled(false);
            opsUs.getTxtNombres().setEnabled(false);
            opsUs.getTxtTelefono().setEnabled(false);
            opsUs.getTxtXpLab().setEnabled(false);
            opsUs.getBtnCancelar().setEnabled(false);
            opsUs.getBtnGuardar().setEnabled(false);
            opsUs.getBtnModificar().setEnabled(false);
            opsUs.getBtnBorrar().setEnabled(false);
            opsUs.getCboCuidades().setEnabled(false);
        } else if (e.getSource() == opsUs.getBtnModificar()) {
            int cc = Integer.parseInt(opsUs.getTxtCedula().getText());
            String nombres = opsUs.getTxtNombres().getText();
            String apell = opsUs.getTxtApellidos().getText();
            String dir = opsUs.getTxtDireccion().getText();
            String tel = opsUs.getTxtTelefono().getText();
            int rows = ciudad.obtenerCiudad().getRowCount();
            ResultSet rs = ciudad.obtenerTodo();
            String dato[] = new String[rows];
            try {
                int x = 0;
                while (rs.next()) {
                    dato[x] = rs.getString("ciudad");
                    x++;
                }
            } catch (SQLException ex) {
                Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
            }
            String ciud = dato[opsUs.getCboCuidades().getSelectedIndex()];
            String fn = opsUs.getTxtFechaN().getText();
            String xp = opsUs.getTxtXpLab().getText();
            String nvl = opsUs.getTxtNlvAcademico().getText();
            Date fnf = new Date(fn);
            user.insertaEditarUsuario(cc, nombres, apell, dir, tel, fnf, xp, nvl, ciud, false);
            opsUs.getTblUsuarios().setModel(user.obtenerUsuario());
            opsUs.getTxtApellidos().setText("");
            opsUs.getTxtCedula().setText("");
            opsUs.getTxtDireccion().setText("");
            opsUs.getTxtFechaN().setText("");
            opsUs.getTxtNlvAcademico().setText("");
            opsUs.getTxtNombres().setText("");
            opsUs.getTxtTelefono().setText("");
            opsUs.getTxtXpLab().setText("");
            opsUs.getCboCuidades().setSelectedIndex(0);
            opsUs.getTxtApellidos().setEnabled(false);
            opsUs.getTxtCedula().setEnabled(false);
            opsUs.getTxtDireccion().setEnabled(false);
            opsUs.getTxtFechaN().setEnabled(false);
            opsUs.getTxtNlvAcademico().setEnabled(false);
            opsUs.getTxtNombres().setEnabled(false);
            opsUs.getTxtTelefono().setEnabled(false);
            opsUs.getTxtXpLab().setEnabled(false);
            opsUs.getBtnCancelar().setEnabled(false);
            opsUs.getBtnGuardar().setEnabled(false);
            opsUs.getBtnModificar().setEnabled(false);
            opsUs.getBtnBorrar().setEnabled(false);
            opsUs.getCboCuidades().setEnabled(false);
        } else if (e.getSource() == opsUs.getBtnGuardar()) {
            int cc = Integer.parseInt(opsUs.getTxtCedula().getText());
            String nombres = opsUs.getTxtNombres().getText();
            String apell = opsUs.getTxtApellidos().getText();
            String dir = opsUs.getTxtDireccion().getText();
            String tel = opsUs.getTxtTelefono().getText();
            int rows = ciudad.obtenerCiudad().getRowCount();
            ResultSet rs = ciudad.obtenerTodo();
            String dato[] = new String[rows];
            try {
                int x = 0;
                while (rs.next()) {
                    dato[x] = rs.getString("ciudad");
                    x++;
                }
            } catch (SQLException ex) {
                Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
            }
            String ciud = dato[opsUs.getCboCuidades().getSelectedIndex()];
            String fn = opsUs.getTxtFechaN().getText();
            String xp = opsUs.getTxtXpLab().getText();
            String nvl = opsUs.getTxtNlvAcademico().getText();
            Date fnf = new Date(fn);
            user.insertaEditarUsuario(cc, nombres, apell, dir, tel, fnf, xp, nvl, ciud, true);
            opsUs.getTblUsuarios().setModel(user.obtenerUsuario());
            opsUs.getTxtApellidos().setText("");
            opsUs.getTxtCedula().setText("");
            opsUs.getTxtDireccion().setText("");
            opsUs.getTxtFechaN().setText("");
            opsUs.getTxtNlvAcademico().setText("");
            opsUs.getTxtNombres().setText("");
            opsUs.getTxtTelefono().setText("");
            opsUs.getTxtXpLab().setText("");
            opsUs.getCboCuidades().setSelectedIndex(0);
            opsUs.getTxtApellidos().setEnabled(false);
            opsUs.getTxtCedula().setEnabled(false);
            opsUs.getTxtDireccion().setEnabled(false);
            opsUs.getTxtFechaN().setEnabled(false);
            opsUs.getTxtNlvAcademico().setEnabled(false);
            opsUs.getTxtNombres().setEnabled(false);
            opsUs.getTxtTelefono().setEnabled(false);
            opsUs.getTxtXpLab().setEnabled(false);
            opsUs.getBtnCancelar().setEnabled(false);
            opsUs.getBtnGuardar().setEnabled(false);
            opsUs.getBtnModificar().setEnabled(false);
            opsUs.getBtnBorrar().setEnabled(false);
            opsUs.getCboCuidades().setEnabled(false);
        } else if (e.getSource() == opsUs.getBtnRegresar()) {
            opsUs.setVisible(false);
            usuarios.setVisible(true);
        }
    }

    private void eventosOpsUs(WindowEvent e) {
        opsUs.getTblUsuarios().setModel(user.obtenerUsuario());
        String[] datosCBO = datosCBOCiudad();
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        int limit = ciudad.obtenerCiudad().getRowCount();
        for (int i = 0; i < limit; i++) {
            dcbm.addElement(datosCBO[i]);
        }
        opsUs.getCboCuidades().setModel(dcbm);
    }

    private void eventosOpsUs(MouseEvent e) {
        int r = opsUs.getTblUsuarios().getSelectedRow();
        opsUs.getTxtApellidos().setEnabled(true);
        opsUs.getTxtCedula().setEnabled(true);
        opsUs.getTxtDireccion().setEnabled(true);
        opsUs.getTxtFechaN().setEnabled(true);
        opsUs.getTxtNlvAcademico().setEnabled(true);
        opsUs.getTxtNombres().setEnabled(true);
        opsUs.getTxtTelefono().setEnabled(true);
        opsUs.getTxtXpLab().setEnabled(true);
        opsUs.getBtnCancelar().setEnabled(true);
        opsUs.getBtnModificar().setEnabled(true);
        opsUs.getBtnBorrar().setEnabled(true);
        opsUs.getCboCuidades().setEnabled(true);
        opsUs.getTxtApellidos().setText(opsUs.getTblUsuarios().getModel().getValueAt(r, 2).toString());
        opsUs.getTxtCedula().setText(opsUs.getTblUsuarios().getModel().getValueAt(r, 0).toString());
        opsUs.getTxtDireccion().setText(opsUs.getTblUsuarios().getModel().getValueAt(r, 3).toString());
        opsUs.getTxtFechaN().setText(opsUs.getTblUsuarios().getModel().getValueAt(r, 5).toString());
        opsUs.getTxtNlvAcademico().setText(opsUs.getTblUsuarios().getModel().getValueAt(r, 7).toString());
        opsUs.getTxtNombres().setText(opsUs.getTblUsuarios().getModel().getValueAt(r, 1).toString());
        opsUs.getTxtTelefono().setText(opsUs.getTblUsuarios().getModel().getValueAt(r, 4).toString());
        opsUs.getTxtXpLab().setText(opsUs.getTblUsuarios().getModel().getValueAt(r, 6).toString());
    }

    /**
     * Controla los listeners de la clase Principal
     */
    private void listenersVistaP() {
        ActionListener evento = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                eventosVistaP(e);
            }
        };
        vistaP.getBtnNumeroCiudades().addActionListener(evento);
        vistaP.getBtnSalir().addActionListener(evento);
        vistaP.getBtnEmpresas().addActionListener(evento);
        vistaP.getBtnUsuarios().addActionListener(evento);
        vistaP.getBtnReportes().addActionListener(evento);
        vistaP.getMnCiudades().addActionListener(evento);
        vistaP.getMnDepartamentos().addActionListener(evento);
    }

    /**
     * Controla los listeners de la clase PrincipalEmpresas
     */
    private void listenersVistaPEmp() {
        ActionListener evento = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                eventosVistaPEmp(e);
            }
        };

        vistaPEmp.getBtnOperaciones().addActionListener(evento);
        vistaPEmp.getBtnRegresar().addActionListener(evento);
        vistaPEmp.getBtnVacantes().addActionListener(evento);
    }

    /**
     * Controla los listeners de la clase OperacionesEmpresas
     */
    private void listenersOpsEmpresas() {
        ActionListener evento = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                eventosOpsEmpresas(e);
            }
        };

        WindowListener wl = new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                eventosOpsEmpresas(e);
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowIconified(WindowEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                ///throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowActivated(WindowEvent e) {
                ///throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                eventosCiudades(e);
                eveentosDepartamentos(e);
                eventosOpsEmpresas(e);
                eventosVacantes(e);
                eventosOpsUs(e);
            }
        };

        MouseListener ml = new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                eventosOpsEmpresas(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        opsEmpresas.getTblListadoEmpresas().addMouseListener(ml);
        opsEmpresas.addWindowListener(wl);
        opsEmpresas.getBtnCancelar().addActionListener(evento);
        opsEmpresas.getBtnBorrar().addActionListener(evento);
        opsEmpresas.getBtnModificar().addActionListener(evento);
        opsEmpresas.getBtnGuardar().addActionListener(evento);
        opsEmpresas.getBtnNuevo().addActionListener(evento);
        opsEmpresas.getBtnRegresar().addActionListener(evento);

    }

    /**
     * Controla los listeners de la clase Vacantes
     */
    private void listenersVacantes() {
        ActionListener evento = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                eventosVacantes(e);
            }
        };
        WindowListener wl = new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
                eventosVacantes(e);
            }

            @Override
            public void windowClosing(WindowEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowClosed(WindowEvent e) {
                ///              throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowIconified(WindowEvent e) {
                //           throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                //         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowActivated(WindowEvent e) {
                //       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                eventosCiudades(e);
                eveentosDepartamentos(e);
                eventosOpsEmpresas(e);
                eventosVacantes(e);
                eventosOpsUs(e);
            }

        };
        MouseListener ml = new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                eventosVacantes(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        vacantes.getTblResultados().addMouseListener(ml);
        vacantes.addWindowListener(wl);
        vacantes.getBtnBorrar().addActionListener(evento);
        vacantes.getBtnCancelar().addActionListener(evento);
        vacantes.getBtnGuardar().addActionListener(evento);
        vacantes.getBtnModificar().addActionListener(evento);
        vacantes.getBtnNueva().addActionListener(evento);
        vacantes.getBtnRegresar().addActionListener(evento);
    }

    private void listenerReportes() {
        ActionListener evento = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                eventosReportes(e);
            }
        };
        reportes.getBtnRegresar().addActionListener(evento);
        reportes.getBtnUsuariosRegistrados().addActionListener(evento);
        reportes.getBtnVerUsuarioEnVacantes().addActionListener(evento);
        reportes.getBtnVerVacantes().addActionListener(evento);
    }

    /**
     * Controla los listeners de la clase Usuarios
     */
    private void listenersUsuarios() {
        ActionListener evento = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                eventosUsuarios(e);
            }
        };
        usuarios.getBtnOperaciones().addActionListener(evento);
        usuarios.getBtnPostular().addActionListener(evento);
        usuarios.getBtnRegresar().addActionListener(evento);
    }

    private void listenersOpsUs() {
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventosOpsUs(e);
            }
        };
        WindowListener wl = new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
                eventosOpsUs(e);
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                eventosCiudades(e);
                eveentosDepartamentos(e);
                eventosOpsEmpresas(e);
                eventosVacantes(e);
                eventosOpsUs(e);
            }

        };
        MouseListener ml = new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                eventosOpsUs(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };
        opsUs.addWindowListener(wl);
        opsUs.getBtnNuevo().addActionListener(evento);
        opsUs.getBtnBorrar().addActionListener(evento);
        opsUs.getBtnCancelar().addActionListener(evento);
        opsUs.getBtnModificar().addActionListener(evento);
        opsUs.getBtnRegresar().addActionListener(evento);
        opsUs.getBtnGuardar().addActionListener(evento);
        opsUs.getTblUsuarios().addMouseListener(ml);
    }

    private void listenersCiudades() {
        ActionListener eventos = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventosCiudades(e);
            }
        };

        WindowListener wl = new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
                eventosCiudades(e);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowClosed(WindowEvent e) {
                //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowIconified(WindowEvent e) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowActivated(WindowEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                eventosCiudades(e);
                eveentosDepartamentos(e);
                eventosOpsEmpresas(e);
                eventosVacantes(e);
                eventosOpsUs(e);
            }
        };

        MouseListener ml = new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                eventosCiudades(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };
        ciudades.getBtnAgregar().addActionListener(eventos);
        ciudades.getBtnEliminar().addActionListener(eventos);
        ciudades.getBtnSalir().addActionListener(eventos);
        ciudades.getTblResultados().addMouseListener(ml);
        ciudades.addWindowListener(wl);

    }

    private void eventosCiudades(ActionEvent e) {
        String idCiudad, nombre, departamento;
        idCiudad = ciudades.getTxtID().getText().toString().toUpperCase();
        if (e.getSource() == ciudades.getBtnSalir()) {
            ciudades.setVisible(false);
            vistaP.setVisible(true);
        } else if (e.getSource() == ciudades.getBtnAgregar()) {
            nombre = ciudades.getTxtNombre().getText().toString().toUpperCase();
            int rows = dpto.obtenerDepartamento().getRowCount();
            ResultSet rs = dpto.obtenerTodo();
            String dato[] = new String[rows];
            try {
                int x = 0;
                while (rs.next()) {
                    dato[x] = rs.getString("departamento");
                    x++;
                }
            } catch (SQLException ex) {
                Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
            }
            departamento = dato[ciudades.getCboDepartamento().getSelectedIndex()].toUpperCase();
            ciudad.insertaEditarCiudad(idCiudad, nombre, departamento, true);
            ciudades.getTblResultados().setModel(ciudad.obtenerCiudad());
            ciudades.getTxtID().setText("");
            ciudades.getTxtNombre().setText("");
        } else if (e.getSource() == ciudades.getBtnEliminar()) {
            ciudad.eliminarCiudad(idCiudad);
            ciudades.getTblResultados().setModel(ciudad.obtenerCiudad());
            ciudades.getTxtID().setText("");
            ciudades.getTxtNombre().setText("");
        }
    }

    private void eventosCiudades(MouseEvent e) {
        int r = ciudades.getTblResultados().getSelectedRow();
        String res2 = ciudades.getTblResultados().getModel().getValueAt(r, 1).toString();
        String res1 = ciudades.getTblResultados().getModel().getValueAt(r, 0).toString();
        ciudades.getTxtID().setText(res1);
        ciudades.getTxtNombre().setText(res2);
    }

    private void eventosCiudades(WindowEvent e) {
        ciudades.getTblResultados().setModel(ciudad.obtenerCiudad());
        String datos[] = datosCBODpto();
        int rows = dpto.obtenerDepartamento().getRowCount();
        dcbm = new DefaultComboBoxModel();
        for (int i = 0; i < rows; i++) {
            dcbm.addElement(datos[i]);
        }
        ciudades.getCboDepartamento().setModel(dcbm);
    }

    private void listenersDepartamentos() {
        ActionListener eventos = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eveentosDepartamentos(e);
            }
        };
        WindowListener wl = new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
                eveentosDepartamentos(e);
            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                eventosCiudades(e);
                eveentosDepartamentos(e);
                eventosOpsEmpresas(e);
                eventosVacantes(e);
                eventosOpsUs(e);
            }
        };
        MouseListener ml = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                eveentosDepartamentos(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };
        departamentos.getBtnAgregar().addActionListener(eventos);
        departamentos.getBtnEliminar().addActionListener(eventos);
        departamentos.getBtnSalir().addActionListener(eventos);
        departamentos.addWindowListener(wl);
        departamentos.getTblResultados().addMouseListener(ml);
    }

    private void eveentosDepartamentos(ActionEvent e) {
        if (e.getSource() == departamentos.getBtnSalir()) {
            departamentos.setVisible(false);
            vistaP.setVisible(true);
        } else if (e.getSource() == departamentos.getBtnAgregar()) {
            String ID = departamentos.getTxtID().getText().toUpperCase();
            String nombre = departamentos.getTxtNombre().getText().toUpperCase();
            dpto.insertaEditarDepartamento(nombre, ID, true);
            departamentos.getTblResultados().setModel(dpto.obtenerDepartamento());
            departamentos.getTxtID().setText("");
            departamentos.getTxtNombre().setText("");
        } else if (e.getSource() == departamentos.getBtnEliminar()) {
            String ID = departamentos.getTxtID().getText().toUpperCase();
            dpto.eliminarDepartamento(ID);
            departamentos.getTblResultados().setModel(dpto.obtenerDepartamento());
            departamentos.getTxtID().setText("");
            departamentos.getTxtNombre().setText("");
        }
    }

    private void eveentosDepartamentos(WindowEvent e) {
        departamentos.getTblResultados().setModel(dpto.obtenerDepartamento());
    }

    private void eveentosDepartamentos(MouseEvent e) {
        int r = (departamentos.getTblResultados().getSelectedRow());
        String res2 = departamentos.getTblResultados().getModel().getValueAt(r, 1).toString();
        String res1 = departamentos.getTblResultados().getModel().getValueAt(r, 0).toString();
        departamentos.getTxtID().setText(res1);
        departamentos.getTxtNombre().setText(res2);
    }

    private String[] datosCBODpto() {
        int rows = dpto.obtenerDepartamento().getRowCount();
        ResultSet rs = dpto.obtenerTodo();
        String dato[] = new String[rows];
        try {
            int x = 0;
            while (rs.next()) {
                dato[x] = rs.getString("nombre");
                x++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dato;
    }

    private String[] datosCBOCiudad() {
        int rows = ciudad.obtenerCiudad().getRowCount();
        ResultSet rs = ciudad.obtenerTodo();
        String dato[] = new String[rows];
        try {
            int x = 0;
            while (rs.next()) {
                dato[x] = rs.getString("nombre");
                x++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dato;
    }
    
    private String[] datosCBOEmpresas() {
        int rows = empresa.obtenerEmpresa().getRowCount();
        ResultSet rs = empresa.obtenerTodo();
        String dato[] = new String[rows];
        try {
            int x = 0;
            while (rs.next()) {
                String s = rs.getString("nombre") + " " + rs.getInt("nit");
                dato[x] = s;
                x++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dato;
    }
}