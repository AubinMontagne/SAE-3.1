package src.Vue;

import javax.swing.*;

import src.Controleur;
import src.Metier.Ressource;

public class FrameCreationNotion extends JFrame{
    private Controleur ctrl;

    public FrameCreationNotion( Controleur ctrl, Ressource r){
        this.ctrl = ctrl;

        System.out.println("CrÃ©ation de la frame CreaNotion");

        this.setTitle("Nouvelle Notion");
        this.setSize(250,150);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelCreaNotion(ctrl, r) );

        setVisible(true);
    }
}
