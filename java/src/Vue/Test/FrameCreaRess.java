import javax.swing.*;

public class FrameCreaRess extends JFrame{
	//private Controleur ctrl;

	public FrameCreaRess( /*Controleur ctrl*/){
		//this.ctrl = ctrl;

		System.out.println("Création de la frame CreaRessource");

		this.setTitle("Nouvelle Ressource");
		this.setSize(400,150);
		this.setLocationRelativeTo(null);

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.add(new PanelCreaRess() );

		setVisible(true);
	}
}

