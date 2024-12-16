package src.Vue;

import src.Metier.Ressource;

import javax.swing.*;
import src.Controleur;

public class FrameNotion extends JFrame{
	private Controleur ctrl;
    private Ressource  ressource;

	public FrameNotion(Controleur ctrl, Ressource r ){
        this.ressource = r;
        this.ctrl = ctrl;

        System.out.println("Création de la frame Notion");

        this.setTitle("Les Notions");
        this.setSize(350,225);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelNotion(this.ctrl, this.ressource) );

        setVisible(true);
    }
}

