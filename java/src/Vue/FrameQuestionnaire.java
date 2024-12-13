package src.Vue;

import javax.swing.*;
import src.Controleur;

public class FrameQuestionnaire extends JFrame{
    private Controleur ctrl;

    public FrameQuestionnaire( Controleur ctrl){
        this.ctrl = ctrl;

        System.out.println("Création de la frame Questionnaire");

        this.setTitle("Création du questionnaire");
        this.setSize(200,200);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // this.add(new PanelQuestionnaire(this.ctrl) );

        setVisible(true);
    }
}