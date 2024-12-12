package Vue;


//import src.Controleur;
import javax.swing.*;

public class FrameAccueil extends JFrame{
    //private Controleur ctrl;

    public FrameAccueil( /*Controleur ctrl*/){
        //this.ctrl = ctrl;

        System.out.println("Création de la frame Accueil");

        this.setTitle("Page d'accueil");
        this.setSize(200,200);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelAccueil() );

        setVisible(true);
    }
}