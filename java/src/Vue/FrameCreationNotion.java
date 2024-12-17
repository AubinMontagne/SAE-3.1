package src.Vue;

import javax.swing.*;

import src.Controleur;
import src.Metier.Ressource;

public class FrameCreationNotion extends JFrame{

    // Contructeur
    /**
     * Constructeur de la class FrameCreationNotion
     * @param ctrl          Le contrôleur
     * @param r             La ressource
     * @param panelNotion   Le panel notion ou mettre la frame
     */
    public FrameCreationNotion( Controleur ctrl, Ressource r, PanelNotion panelNotion){
        System.out.println("CrÃ©ation de la frame CreaNotion");

        this.setTitle("Nouvelle Notion");
        this.setSize(250,200);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelCreationNotion(ctrl, r, panelNotion,this) );

        setVisible(true);
    }
}
