package src.Vue;

import javax.swing.*;
import src.Controleur;

public class FrameRessource extends JFrame{
	private Controleur ctrl;

    // Constructeur

    /**
     * Contructeur de la class FrameRessource
     * @param ctrl  Le contrôleur
     */
	public FrameRessource(Controleur ctrl){
        this.ctrl = ctrl;

        this.setTitle("Les Ressources");
        this.setSize(190,215);
        this.setMinimumSize(this.getSize());
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelRessource(ctrl) );

        setVisible(true);
    }
}
