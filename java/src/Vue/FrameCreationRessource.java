package src.Vue;

import src.Controleur;

import javax.swing.*;
import java.awt.event.WindowListener;

public class FrameCreationRessource extends JFrame implements WindowListener {
    private static int nbFrame = 0;

    private Controleur ctrl;

    // Constructeur
    /**
     * Constructeur de la class FrameCreationRessource
     * @param ctrl              Le contrôleur
     */
    private FrameCreationRessource(Controleur ctrl){
        this.ctrl = ctrl;

        this.setTitle             ("Création de la question");
        this.setSize              (250,200);
        this.setMinimumSize       (this.getSize());
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelCreationRessource(ctrl, this) );
        this.addWindowListener(this);

        setVisible(true);
    }

    public static FrameCreationRessource creerFrameCreationRessource(Controleur ctrl){
        if(FrameCreationRessource.nbFrame == 0){
            FrameCreationRessource.nbFrame++;
            return new FrameCreationRessource(ctrl);
        }
        return null;
    }

    public void windowOpened     (java.awt.event.WindowEvent e) {}
    public void windowClosing    (java.awt.event.WindowEvent e) {FrameCreationRessource.nbFrame--;}
    public void windowClosed     (java.awt.event.WindowEvent e) {}
    public void windowIconified  (java.awt.event.WindowEvent e) {}
    public void windowDeiconified(java.awt.event.WindowEvent e) {}
    public void windowActivated  (java.awt.event.WindowEvent e) {}
    public void windowDeactivated(java.awt.event.WindowEvent e) {}
}