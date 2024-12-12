package src.Vue;

import src.Metier.Ressource;

import javax.swing.*;

public class FrameNotion extends JFrame{
    private Ressource ressource;

	public FrameNotion(Ressource r ){
        this.ressource = r;
        //this.ctrl = ctrl;

        System.out.println("Création de la frame Notion");


        this.setTitle("Les Notions");
        this.setSize(300,215);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelNotion(this.ressource) );

        setVisible(true);
    }
}

