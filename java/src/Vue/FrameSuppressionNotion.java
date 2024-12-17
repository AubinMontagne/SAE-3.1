package src.Vue;

import javax.swing.*;

import src.Controleur;
import src.Metier.Ressource;

public class FrameSuppressionNotion extends JFrame{

    // Controleur

    /**
     * Controleur de la class FrameSuppressionNotion
     * @param ctrl          Le contrôleur
     * @param r             La ressource
     * @param panelNotion   Le panel notion ou mettre la frame
     */
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