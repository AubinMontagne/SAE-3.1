package Vue;

import src.Controleur;
import javax.swing;

public class FrameBanque extends JFrame{
    //private Controleur ctrl;

    public FrameBanque( /*Controleur ctrl*/){
        //this.ctrl = ctrl;

        System.out.printl("Création de la frame Banque");

        int width = (int) (screenSize.getWidth() * 0.8);
        int height = (int) (screenSize.getHeight() * 0.8);

        this.setTitle("Banque de question");
        this.setSize(width,height);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(new PanelBanque(/*this.ctrl*/) );

        setVisible(true);
    }
}