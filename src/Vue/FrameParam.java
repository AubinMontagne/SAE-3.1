package Vue;

import src.Controleur;
import javax.swing;

public class FrameParam extends JFrame{
    private Controleur ctrl;

    public FrameParam( Controleur ctrl){
        this.ctrl = ctrl;

        System.out.printl("Création de la frame Paramètre");

        int width = (int) (screenSize.getWidth() * 0.8);
        int height = (int) (screenSize.getHeight() * 0.8);

        this.setTitle("Création des Ressources et Notions");
        this.setSize(width,height);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(new PanelParam(this.ctrl) );

        setVisible(true);
    }
}
public class FrameParam
{

}