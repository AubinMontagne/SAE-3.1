package src.Vue;

import javax.swing.*;
import src.Controleur;

import java.awt.*;

public class FrameParametrage extends JFrame {
	private Controleur ctrl;

    // Constructeur

    /**
     * Contructeur de la class FrameRessource
     * @param ctrl  Le contrôleur
     */
	public FrameParametrage(Controleur ctrl){
        this.ctrl = ctrl;

        this.setTitle("Les Ressources");
        this.setSize(190,215);
        this.setMinimumSize(this.getSize());
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelRessource(ctrl), BorderLayout.WEST );
        this.add(new PanelNotion(ctrl,null), BorderLayout.EAST );

        setVisible(true);
    }
}