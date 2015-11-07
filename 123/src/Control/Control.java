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
import java.util.Locale;
import javax.swing.JOptionPane;

public class Control {
    /* Declaracion de variabes */

    private Modelo.Vacante vacante = new Modelo.Vacante();
    private Modelo.Departamento dpto = new Modelo.Departamento();
    private Modelo.Ciudad ciudad = new Modelo.Ciudad();
    private Modelo.Empresa empresa = new Empresa();
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
    public void iniciar(Boolean bandera) {
        if (bandera) {
            vistaP.setVisible(bandera);
        } else {
            JOptionPane.showMessageDialog(vistaP.getRootPane(), "No se mostrará el programa", "Informacón", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Se encarga de controlar todos los eventos que aparecen en la vista
     * principal
     */
    private void eventosVistaP(ActionEvent e) {
        if (e.getSource() == vistaP.getBtnSalir()) {
            Thread thrd = new Thread(new Runnable() {
                @Override
                public void run() {
                    vistaP.dispose();
                    vistaPEmp.dispose();
                    opsEmpresas.dispose();
                    vacantes.dispose();
                    usuarios.dispose();
                    reportes.dispose();
                    opsUs.dispose();
                    ciudades.dispose();
                    departamentos.dispose();
                }
            });
            thrd.start();
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

    }

    private void eventosOpsEmpresas(ActionEvent e) {
        String nombre, direccion, telefono, idCiudad, correo;
        int nit;

        /* Operaciones sobre empresas*/ if (e.getSource() == opsEmpresas.getBtnRegresar()) {
            opsEmpresas.setVisible(false);
            vistaPEmp.setVisible(true);
        } else if (e.getSource() == opsEmpresas.getBtnNuevo()) {
            opsEmpresas.getTxtNit().setEditable(true);
            opsEmpresas.getTxtNombre().setEditable(true);
            opsEmpresas.getTxtDireccion().setEditable(true);
            opsEmpresas.getTxtTelefono().setEditable(true);
            opsEmpresas.getTxtCorreoE().setEditable(true);
            opsEmpresas.getCboCiudades().setEnabled(true);
        } else if (e.getSource() == opsEmpresas.getBtnCancelar()) {
            opsEmpresas.getTxtNit().setText("");
            opsEmpresas.getTxtNit().setEditable(false);
            opsEmpresas.getTxtNombre().setText("");
            opsEmpresas.getTxtNombre().setEditable(false);
            opsEmpresas.getTxtDireccion().setText("");
            opsEmpresas.getTxtDireccion().setEditable(false);
            opsEmpresas.getTxtTelefono().setText("");
            opsEmpresas.getTxtTelefono().setEditable(false);
            opsEmpresas.getTxtCorreoE().setText("");
            opsEmpresas.getTxtCorreoE().setEditable(false);
            opsEmpresas.getCboCiudades().setSelectedIndex(0);
            opsEmpresas.getCboCiudades().setEnabled(false);
        } else if (e.getSource() == opsEmpresas.getBtnGuardar()) {
            nombre = opsEmpresas.getTxtNombre().getText();
            direccion = opsEmpresas.getTxtDireccion().getText();
            telefono = opsEmpresas.getTxtTelefono().getText();
            idCiudad = "73001";
            correo = opsEmpresas.getTxtCorreoE().getText();
            try {
                nit = Integer.parseInt(opsEmpresas.getTxtNit().getText());
                empresa.insertaEditarEmpresa(nit, nombre, direccion, idCiudad, telefono, correo, true);
                opsEmpresas.getTblListadoEmpresas().setModel(empresa.obtenerEmpresa());
            } catch (NumberFormatException x) {
                System.out.print(e);

            }
            opsEmpresas.getTblListadoEmpresas().setModel(empresa.obtenerEmpresa());

        } else if (e.getSource() == opsEmpresas.getBtnModificar()) {

            nombre = opsEmpresas.getTxtNombre().getText();
            direccion = opsEmpresas.getTxtDireccion().getText();
            telefono = opsEmpresas.getTxtTelefono().getText();
            idCiudad = "73001";
            correo = opsEmpresas.getTxtCorreoE().getText();
            try {
                nit = Integer.parseInt(opsEmpresas.getTxtNit().getText());
                empresa.insertaEditarEmpresa(nit, nombre, direccion, idCiudad, telefono, correo, false);

            } catch (NumberFormatException x) {
                System.out.print(x);

            }
            opsEmpresas.getTblListadoEmpresas().setModel(empresa.obtenerEmpresa());
            opsEmpresas.getTblListadoEmpresas().setModel(empresa.obtenerEmpresa());

        } else if (e.getSource() == opsEmpresas.getBtnBorrar()) {
            try {
                nit = Integer.parseInt(opsEmpresas.getTxtNit().getText());
                empresa.eliminarEmpresa(nit);
                opsEmpresas.getTblListadoEmpresas().setModel(empresa.obtenerEmpresa());
            } catch (Exception x) {
                System.out.print(x);
            }
            
        }

    }

    /**
     * Se encarga de controlar los eventos que pasan en la principal de los
     * reportes
     */
    private void eventosReportes(ActionEvent e) {
        if (e.getSource() == reportes.getBtnUsuariosRegistrados()) {
            reporte = new Reporte("Usuarios registrados");
            String ruta = "C:\\Users\\Carlos Baez\\Google Drive\\Agencia de empleo CAFE\\src\\Reportes\\Usuarios.jrxml";
            reporte.imprimirReporte(ruta, 1, 0);

        } else if (e.getSource() == reportes.getBtnVerVacantes()) {
            reporte = new Reporte("vacantes por empresa");
            String ruta = "C:\\Users\\Carlos Baez\\Google Drive\\Agencia de empleo CAFE\\src\\Reportes\\Vacantes.jrxml";
            int nit = Integer.parseInt(JOptionPane.showInputDialog(null, "Nit "));
            reporte.imprimirReporte(ruta, nit, 0);
        } else if (e.getSource() == reportes.getBtnVerUsuarioEnVacantes()) {
            reporte = new Reporte("vacantes inscritas  por una persona");
            String ruta = "C:\\Users\\Carlos Baez\\Google Drive\\Agencia de empleo CAFE\\src\\Reportes\\VacantesPorUsuario.jrxml";
            int cedula = Integer.parseInt(JOptionPane.showInputDialog(null, "Cedula "));
            reporte.imprimirReporte(ruta, 0, cedula);

        } else if (e.getSource() == reportes.getBtnRegresar()) {
            vistaP.setVisible(true);
            reportes.setVisible(false);
        }
       

    }

    /**
     * Se encarga de controlar todos los eventos que aparecen en la vista de las
     * vacantes
     */
    private void eventosVacantes(ActionEvent e) {
        if (e.getSource() == vacantes.getBtnBorrar()) {
            JOptionPane.showMessageDialog(vistaP.getRootPane(), "en desarrollo");
        } else if (e.getSource() == vacantes.getBtnCancelar()) {
            JOptionPane.showMessageDialog(vistaP.getRootPane(), "en desarrollo");
        } else if (e.getSource() == vacantes.getBtnGuardar()) {
            try {
                int idVacante, nitEmpresa;
                String cargo, nombre, idCiudad;
                double salario;

                idVacante = Integer.parseInt(vacantes.getTxtIDVacante().getText());
                salario = Double.parseDouble(vacantes.getTxtSalario().getText());
                nitEmpresa = Integer.parseInt(vacantes.getTxtNit().getText());

                cargo = vacantes.getTxtCargo().getText();
                nombre = vacantes.getTxtNombreVacante().getText();
                idCiudad = "73001";

                vacante.insertaEditarVacante(idVacante, nitEmpresa, cargo, idCiudad, salario, true);
                vacantes.getTblResultados().setModel(vacante.obtenerVacante());
            } catch (Exception x) {
            }

        } else if (e.getSource() == vacantes.getBtnModificar()) {
            try {
                int idVacante, nitEmpresa;
                String cargo, nombre, idCiudad;
                double salario;

                idVacante = Integer.parseInt(vacantes.getTxtIDVacante().getText());
                salario = Double.parseDouble(vacantes.getTxtSalario().getText());
                nitEmpresa = Integer.parseInt(vacantes.getTxtNit().getText());

                cargo = vacantes.getTxtCargo().getText();
                nombre = vacantes.getTxtNombreVacante().getText();
                idCiudad = "73001";

                vacante.insertaEditarVacante(idVacante, nitEmpresa, cargo, idCiudad, salario, false);
                vacantes.getTblResultados().setModel(vacante.obtenerVacante());
            } catch (Exception x) {
            }

        } else if (e.getSource() == vacantes.getBtnNueva()) {
            vacantes.getTxtCargo().setEditable(true);
            vacantes.getTxtIDVacante().setEnabled(true);
            vacantes.getTxtNombreVacante().setEnabled(true);
            vacantes.getTxtNit().setEnabled(true);
            vacantes.getTxtSalario().setEnabled(true);

        } else if (e.getSource() == vacantes.getBtnRegresar()) {
            vacantes.setVisible(false);
            vistaPEmp.setVisible(true);
        }
    }

    private void eventosVacantes(WindowEvent e) {
        vacantes.getTblResultados().setModel(vacante.obtenerVacante());
    }

    /**
     * Se encarga de controlar todos los eventos que aparecen en la vista de los
     * usuarios
     */
    private void eventosUsuarios(ActionEvent e) {
        if (e.getSource() == usuarios.getBtnConsultarVacantes()) {
            JOptionPane.showMessageDialog(vistaP.getRootPane(), "en desarrollo");
        } else if (e.getSource() == usuarios.getBtnOperaciones()) {
            JOptionPane.showMessageDialog(vistaP.getRootPane(), "en desarrollo");
        } else if (e.getSource() == usuarios.getBtnPostular()) {
            JOptionPane.showMessageDialog(vistaP.getRootPane(), "en desarrollo");
        } else if (e.getSource() == usuarios.getBtnRegresar()) {
            usuarios.setVisible(false);
            vistaP.setVisible(true);
        }
    }

    private void eventosOpsUs(ActionEvent e) {

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
                //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };

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
                //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        };
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
        usuarios.getBtnConsultarVacantes().addActionListener(evento);
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
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        };
        ciudades.getBtnAgregar().addActionListener(eventos);
        ciudades.getBtnEliminar().addActionListener(eventos);
        ciudades.getBtnSalir().addActionListener(eventos);
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
            departamento = ciudades.getTxtDepartamento().getText().toString().toUpperCase();

            ciudad.insertaEditarCiudad(idCiudad, nombre, departamento, true);
            ciudades.getTblResultados().setModel(ciudad.obtenerCiudad());

        } else if (e.getSource() == ciudades.getBtnEliminar()) {
            ciudad.eliminarCiudad(idCiudad);
            ciudades.getTblResultados().setModel(ciudad.obtenerCiudad());
        }
    }

    private void eventosCiudades(WindowEvent e) {
        ciudades.getTblResultados().setModel(ciudad.obtenerCiudad());
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
        } else if (e.getSource() == departamentos.getBtnEliminar()) {
            String ID = departamentos.getTxtID().getText().toUpperCase();
            dpto.eliminarDepartamento(ID);
            departamentos.getTblResultados().setModel(dpto.obtenerDepartamento());
        }
    }

    public void eveentosDepartamentos(WindowEvent e) {
        departamentos.getTblResultados().setModel(dpto.obtenerDepartamento());
    }

    public void eveentosDepartamentos(MouseEvent e) {
        int r = (departamentos.getTblResultados().getSelectedRow());
        String res2 = departamentos.getTblResultados().getModel().getValueAt(r, 1).toString();
        String res1 = departamentos.getTblResultados().getModel().getValueAt(r, 0).toString();
        departamentos.getTxtID().setText(res1);
        departamentos.getTxtNombre().setText(res2);
    }

}
