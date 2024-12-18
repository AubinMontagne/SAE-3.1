package src.Vue;

import javax.swing.*;
import src.*;

public class FrameQuestionnaire extends JFrame{ 
    private Controleur ctrl;

    // Constructeur
    /**
     * Constructeur de la class FrameQuestionnaire
     * @param ctrl          Le contrôleur
     */
    public FrameQuestionnaire( Controleur ctrl ){
        this.ctrl = ctrl;

        this.setTitle("Nouveaux Questionnaire");
        this.setSize(590,175);
        this.setMinimumSize(this.getSize());
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelQuestionnaire(ctrl) );

        setVisible(true);
    }
}