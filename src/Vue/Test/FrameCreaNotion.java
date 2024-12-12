
import javax.swing.*;

public class FrameCreaNotion extends JFrame{
	//private Controleur ctrl;

	public FrameCreaNotion( /*Controleur ctrl*/Ressource r){
		//this.ctrl = ctrl;

		System.out.println("Création de la frame CreaNotion");

		this.setTitle("Nouvelle Notion");
		this.setSize(250,150);
		this.setLocationRelativeTo(null);

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.add(new PanelCreaNotion(r) );

		setVisible(true);
	}
}

