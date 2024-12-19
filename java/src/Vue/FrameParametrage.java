package src.Vue;

import javax.swing.*;
import src.Controleur;

import java.awt.*;

public class FrameParametrage extends JFrame {
	private Controleur ctrl;

    private PanelNotion panelNotion;
    private PanelRessource panelRessource;
    // Constructeur

    /**
     * Contructeur de la class FrameRessource
     * @param ctrl  Le contrôleur
     */
	public FrameParametrage(Controleur ctrl){
        this.ctrl = ctrl;

        this.setTitle("Les Ressources");
        this.setSize(650,275);
        this.setMinimumSize(this.getSize());
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.panelRessource = new PanelRessource(ctrl,this);
        this.panelNotion = new PanelNotion(ctrl,null);

        this.add(this.panelRessource, BorderLayout.WEST );
        this.add(this.panelNotion   ,BorderLayout.EAST );

        setVisible(true);
    }
    public void majPanelNotion(){
        this.panelNotion.setRessourceSelectionnee(this.panelRessource.getRessourceSelectionnee());
        this.panelNotion.maj();
    }
}