package src.Vue;

import src.Controleur;
import src.Metier.Ressource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class PanelCreationRessource extends JPanel implements ActionListener {
	private Controleur 			   ctrl;
	private JButton 			   boutonConfirmer;
	private JTextField 			   champID;
	private JTextField 			   champNom;
	private FrameCreationRessource frameCreationRessource;

	/**
	 * Constructeur de la class PanelCreationRessource
	 * @param ctrl					 Le contôleur
	 */
	public PanelCreationRessource(Controleur ctrl ,FrameCreationRessource frameCreationRessource){
		this.ctrl 					= ctrl;
		this.frameCreationRessource = frameCreationRessource;

		setLayout(new BorderLayout());

		UIManager.put("Label.font", new Font("Arial", Font.BOLD, 11));

		// Section supérieure
		JPanel panelConfiguration = new JPanel(new GridLayout(2, 2, 5, 5));
		panelConfiguration.setBorder(BorderFactory.createTitledBorder("Configuration"));

		JLabel labelID = new JLabel("Identifiant de la ressource :");
		this.champID   = new JTextField();
		this.champID.setPreferredSize(new Dimension(10, 10));

		JLabel labalNom = new JLabel("Nom de la ressource :");
		this.champNom   = new JTextField();
		this.champNom.setPreferredSize(new Dimension(10, 10));

		panelConfiguration.add(labelID);
		panelConfiguration.add(this.champID);
		panelConfiguration.add(labalNom);
		panelConfiguration.add(this.champNom);

		add(panelConfiguration, BorderLayout.CENTER);

		// Section centrale
		JPanel panelSelection = new JPanel(new GridLayout(3, 2, 5, 5));
		panelSelection.setBorder(BorderFactory.createTitledBorder("Sélection"));

		// Section inférieure
		JPanel panelType = new JPanel(new GridLayout(1, 1, 5, 5));

		this.boutonConfirmer = new JButton("Confirmer");
		this.boutonConfirmer.addActionListener(this);

		panelType.add(this.boutonConfirmer);

		add(panelType, BorderLayout.SOUTH);
		setVisible(true);
	}

	// Methode
	/**
	 * Methode actionPerformed
	 * @param e L'évènement à traiter
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.boutonConfirmer) {
			String id  = this.champID.getText().trim();
			String nom = this.champNom.getText().trim();

			if (id.isEmpty() || nom.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}

			Ressource r = new Ressource(id, nom);
			this.ctrl.ajouterRessource(r);
			this.ctrl.miseAJourFichiers();

			JOptionPane.showMessageDialog(this, "Ressource ajoutée", "Succès", JOptionPane.INFORMATION_MESSAGE);

			this.frameCreationRessource.dispose();
		}
	}
}