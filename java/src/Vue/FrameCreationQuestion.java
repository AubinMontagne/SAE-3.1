package src.Vue;

import src.Controleur;
import javax.swing.*;

public class FrameCreationQuestion extends JFrame{ 
    private Controleur ctrl;

    public FrameCreationQuestion( Controleur ctrl){
        this.ctrl = ctrl;

        System.out.println("Création de la frame CreaQuestion");

        this.setTitle("Création de la question");
        this.setSize(800,500);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelCreationQuestion(ctrl) );

        setVisible(true);
    }
}