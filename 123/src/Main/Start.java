package Main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author FranciscoJavier
 */
import javax.swing.JOptionPane;
import Control.*;

public class Start {

    public static void main(String args[]) {
        Control c = new Control();
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Throwable ex) {
            JOptionPane.showMessageDialog(null, "Error :" + ex.getLocalizedMessage(), "Errores del sistema", JOptionPane.ERROR_MESSAGE);
        }
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                c.iniciar(true);
            }
        });
        hilo.start();
    }
}
