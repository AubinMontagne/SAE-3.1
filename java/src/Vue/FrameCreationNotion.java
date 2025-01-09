package src.Vue;

import javax.swing.*;

import src.Controleur;
import src.Metier.Notion;
import src.Metier.Ressource;

import java.awt.event.WindowListener;

public class FrameCreationNotion extends JFrame implements WindowListener {
    private static int nbFrame = 0;

    /**
     * Constructeur de la class FrameCreationNotion
     * @param ctrl          Le contrôleur
     * @param r             La ressource
     * @param panelNotion   Le panel notion ou mettre la frame
     */
    public FrameCreationNotion( Controleur ctrl, Ressource r, PanelNotion panelNotion){

        this.setTitle             ("QCM Buider - Nouvelle Notion");
        this.setSize              (250,200);
        this.setMinimumSize       (this.getSize());
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("java/data/Images/icon.png").getImage());


        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelCreationNotion(ctrl, r, panelNotion,this) );
        this.addWindowListener(this);

        setVisible(true);
    }

    public static FrameCreationNotion creerFrameCreationNotion(Controleur ctrl, Ressource r, PanelNotion panelNotion){
        if(FrameCreationNotion.nbFrame == 0){
            FrameCreationNotion.nbFrame++;
            return new FrameCreationNotion(ctrl, r, panelNotion);
        }
        return null;
    }

    public void windowOpened     (java.awt.event.WindowEvent e) {}
    public void windowClosing    (java.awt.event.WindowEvent e) {FrameCreationNotion.nbFrame--;}
    public void windowClosed     (java.awt.event.WindowEvent e) {}
    public void windowIconified  (java.awt.event.WindowEvent e) {}
    public void windowDeiconified(java.awt.event.WindowEvent e) {}
    public void windowActivated  (java.awt.event.WindowEvent e) {}
    public void windowDeactivated(java.awt.event.WindowEvent e) {}
}