package Vue;

import src.Controleur;
import javax.swing.*;

public class FrameParam extends JFrame{
    //private Controleur ctrl;

    public FrameParam(/* Controleur ctrl*/){
        //this.ctrl = ctrl;

        System.out.printl("Création de la frame Paramètre");

        this.setTitle("Création des Ressources et Notions");
        this.setSize(200,200);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(new PanelParam(/*this.ctrl*/) );

        setVisible(true);
    }
}
