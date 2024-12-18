package src.Vue;

import src.Controleur;
import javax.swing.*;

public class FrameAccueil extends JFrame{
    private Controleur ctrl;

    // Contructeur
    /**
     * Constructeur de la class FrameAccueil
     * @param ctrl  Le contrôleur
     */
    public FrameAccueil( Controleur ctrl){
        this.ctrl = ctrl;

        this.setTitle("Page d'accueil");
        this.setSize(240,200);
        this.setMinimumSize(this.getSize());
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(new PanelAccueil(ctrl) );

        setVisible(true);
    }
}