package src.Vue;

import javax.swing.*;
import src.Controleur;
import src.Metier.*;

public class FrameQuestionnaireTab extends JFrame{ 
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

        System.out.println("Création de la frame QuestionnaireTab");

        this.setTitle("Tableau des Notions");
        this.setSize(600,175);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelQuestionnaireTab(ctrl, r, titre, chrono) );

        setVisible(true);
    }
}