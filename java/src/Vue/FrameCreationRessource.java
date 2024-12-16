package src.Vue;

import src.Controleur;

import javax.swing.*;

public class FrameCreationRessource extends JFrame{
    private Controleur ctrl;

    public FrameCreationRessource(Controleur ctrl, PanelRessource panelRessource){
        this.ctrl = ctrl;

        System.out.println("Création de la frame CreaRessource");

        this.setTitle("Création de la question");
        this.setSize(250,200);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelCreationRessource(ctrl, panelRessource, this) );

        setVisible(true);
    }
}