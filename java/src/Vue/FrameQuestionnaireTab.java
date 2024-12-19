package src.Vue;

import javax.swing.*;
import src.Controleur;
import src.Metier.*;
import java.awt.event.WindowListener;

public class FrameQuestionnaireTab extends JFrame implements WindowListener{
    private static int nbFrame = 0;
    private Controleur ctrl;

    // Constructeur
    /**
     * Constructeur de la class FrameQuestionnaireTab
     * @param ctrl          Le contrôleur
     * @param r             La ressource
     * @param titre         Le titre du questionnaire
     * @param chrono        Si le questionnaire est chronométré
     */
    public FrameQuestionnaireTab( Controleur ctrl, Ressource r, String titre, boolean chrono){
        this.ctrl = ctrl;

        this.setTitle             ("Tableau des Notions");
        this.add                  (new PanelQuestionnaireTab(ctrl, r, titre, chrono) );
        this.setSize              (this.getContentPane().getSize());
        this.setMinimumSize       (this.getSize());
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setVisible(true);
    }

    public static FrameQuestionnaireTab creerFrameQuestionnaireTab(Controleur ctrl){
        if(FrameQuestionnaireTab.nbFrame == 0){
            FrameQuestionnaireTab.nbFrame++;
            return new FrameQuestionnaireTab(ctrl);
        }
        return null;
    }

    public void windowOpened     (java.awt.event.WindowEvent e) {}
    public void windowClosing    (java.awt.event.WindowEvent e) {FrameBanque.nbFrame--;}
    public void windowClosed     (java.awt.event.WindowEvent e) {}
    public void windowIconified  (java.awt.event.WindowEvent e) {}
    public void windowDeiconified(java.awt.event.WindowEvent e) {}
    public void windowActivated  (java.awt.event.WindowEvent e) {}
    public void windowDeactivated(java.awt.event.WindowEvent e) {}
}