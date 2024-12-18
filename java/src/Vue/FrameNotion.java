package src.Vue;

import src.Metier.Ressource;

import javax.swing.*;
import src.Controleur;

import java.awt.event.WindowListener;

public class FrameNotion extends JFrame implements WindowListener {
    private static int nbFrame = 0;

    private Controleur ctrl;
    private Ressource  ressource;

    // Constructeur
    /**
     * Constructeur de la class FrameNotion
     * @param ctrl          Le contrôleur
     * @param r             La ressource
     */
	public FrameNotion(Controleur ctrl, Ressource r ){
        this.ressource = r;
        this.ctrl = ctrl;

        this.setTitle("Les Notions");
        this.setSize(350,225);
        this.setMinimumSize(this.getSize());
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelNotion(this.ctrl, this.ressource) );
        this.addWindowListener(this);

        setVisible(true);
    }

    public static FrameNotion creerFrameNotion(Controleur ctrl, Ressource r){
        if(FrameNotion.nbFrame <= 0){
            FrameNotion.nbFrame++;
            return new FrameNotion(ctrl, r);
        }
        return null;
    }

    public void windowOpened(java.awt.event.WindowEvent e) {
    }
    public void windowClosing(java.awt.event.WindowEvent e) {
        if (FrameNotion.nbFrame > 0){FrameNotion.nbFrame--;}
    }
    public void windowClosed(java.awt.event.WindowEvent e) {
    }
    public void windowIconified(java.awt.event.WindowEvent e) {
    }
    public void windowDeiconified(java.awt.event.WindowEvent e) {
    }
    public void windowActivated(java.awt.event.WindowEvent e) {
    }
    public void windowDeactivated(java.awt.event.WindowEvent e) {
    }
}

