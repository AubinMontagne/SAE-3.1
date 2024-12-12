package src.Vue;

import javax.swing.*;
import src.Controleur;

public class FrameRessource extends JFrame{
	private Controleur ctrl;

	public FrameRessource(Controleur ctrl){
        this.ctrl = ctrl;

        System.out.println("Création de la frame Ressource");


        this.setTitle("Les Ressources");
        this.setSize(300,215);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelRessource(ctrl) );

        setVisible(true);
    }
}
