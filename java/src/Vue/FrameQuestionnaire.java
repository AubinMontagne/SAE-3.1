package src.Vue;

import javax.swing.*;
import src.*;
import src.Metier.Ressource;

import java.awt.*;
import java.awt.event.WindowListener;

public class FrameQuestionnaire extends JFrame implements WindowListener{
    private static int nbFrame = 0;
    private Controleur ctrl;

    // Constructeur
    /**
     * Constructeur de la class FrameQuestionnaire
     * @param ctrl          Le contrôleur
     */
    public FrameQuestionnaire( Controleur ctrl ){
        this.ctrl = ctrl;

        this.setTitle             ("Nouveaux Questionnaire");
        this.setSize              (590,400);
        this.setMinimumSize       (this.getSize());
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelQuestionnaire(ctrl, this), BorderLayout.NORTH );

        setVisible(true);
    }
    public void majTab(Controleur ctrl, Ressource r, String titre, Boolean estChrono){
        this.setSize(590,450);
        this.add(new PanelQuestionnaireTab(ctrl,r,titre,estChrono), BorderLayout.SOUTH);
    }

    public static FrameQuestionnaire creerFrameQuestionnaire(Controleur ctrl){
        if(FrameQuestionnaire.nbFrame == 0){
            FrameQuestionnaire.nbFrame++;
            return new FrameQuestionnaire(ctrl);
        }
        return null;
    }
    public void windowOpened     (java.awt.event.WindowEvent e) {}
    public void windowClosing    (java.awt.event.WindowEvent e) {FrameQuestionnaire.nbFrame--;}
    public void windowClosed     (java.awt.event.WindowEvent e) {}
    public void windowIconified  (java.awt.event.WindowEvent e) {}
    public void windowDeiconified(java.awt.event.WindowEvent e) {}
    public void windowActivated  (java.awt.event.WindowEvent e) {}
    public void windowDeactivated(java.awt.event.WindowEvent e) {}
}