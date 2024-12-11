import javax.swing.*;

public class FrameRessource extends JFrame{

	public FrameRessource( ){
        //this.ctrl = ctrl;

        System.out.println("Création de la frame Ressource");


        this.setTitle("Les Ressources");
        this.setSize(300,215);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelRessource() );

        setVisible(true);
    }
}
