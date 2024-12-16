package src.Vue;

import javax.swing.*;
import src.*;

public class FrameQuestionnaire extends JFrame{ 
    private Controleur ctrl;

    public FrameQuestionnaire( Controleur ctrl ){
        this.ctrl = ctrl;

        System.out.println("Création de la frame Questionnaire");

        this.setTitle("Nouveaux Questionnaire");
        this.setSize(600,175);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelQuestionnaire(ctrl) );

        setVisible(true);
    }
}