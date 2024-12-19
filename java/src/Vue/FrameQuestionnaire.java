package src.Vue;

import javax.swing.*;
import src.*;
import src.Metier.Ressource;

import java.awt.*;

public class FrameQuestionnaire extends JFrame{ 
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
}