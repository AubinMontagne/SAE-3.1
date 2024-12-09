package Vue;

import src.Controleur;
import javax.swing;

public class FrameAccueil extends JFrame{
    private Controleur ctrl;

    public FrameAccueil( Controleur ctrl){
        this.ctrl = ctrl;

        System.out.printl("Création de la frame Accueil");

        int width = (int) (screenSize.getWidth() * 0.8);
        int height = (int) (screenSize.getHeight() * 0.8);

        this.setTitle("Page d'accueil");
        this.setSize(width,height);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(new PanelAccueil(this.ctrl) );

        setVisible(true);
    }
}