/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package loginingfilegui;

import javafx.scene.paint.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author nazarov
 */
public class LoginingFileGUI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrameGUI().setVisible(true);
            }
        });
        // TODO code application logic here
    }
    
}
