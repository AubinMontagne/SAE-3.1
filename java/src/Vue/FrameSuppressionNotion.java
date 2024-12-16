package src.Vue;

import javax.swing.*;

import src.Controleur;
import src.Metier.Ressource;

public class FrameSuppressionNotion extends JFrame{

    public FrameSuppressionNotion( Controleur ctrl, Ressource r, PanelNotion panelNotion){
        System.out.println("Création de la frame SuppressionNotion");

        this.setTitle("Supprimer Notion");
        this.setSize(250,200);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelSupprimerNotion(ctrl, r, panelNotion,this) );

        setVisible(true);
    }
}