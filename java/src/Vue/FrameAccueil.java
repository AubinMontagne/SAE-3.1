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

        System.out.println("Création de la frame Accueil");

        this.setTitle("Page d'accueil");
        this.setSize(200,200);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(new PanelAccueil(ctrl) );

        setVisible(true);
    }
}