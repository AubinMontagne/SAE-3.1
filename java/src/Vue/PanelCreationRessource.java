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
	private Controleur ctrl;
	private JButton boutonConfirmer;
	private JTextField champID;
	private JTextField champNom;
	private PanelRessource panelRessource;
	private FrameCreationRessource frameCreationRessource;

	public PanelCreationRessource(Controleur ctrl, PanelRessource panelRessource, FrameCreationRessource frameCreationRessource){
		this.ctrl = ctrl;
		this.frameCreationRessource = frameCreationRessource;
		this.panelRessource = panelRessource;
		setLayout(new BorderLayout());

		UIManager.put("Label.font", new Font("Arial", Font.BOLD, 25));

		// Section supérieure
		JPanel panelConfiguration = new JPanel(new GridLayout(2, 2, 5, 5));
		panelConfiguration.setBorder(BorderFactory.createTitledBorder("Configuration"));

		JLabel labelID = new JLabel("Identifiant de la ressource :");
		this.champID = new JTextField();
		JLabel labalNom = new JLabel("Nom de la ressource :");
		this.champNom = new JTextField();

		panelConfiguration.add(labelID);
		panelConfiguration.add(champID);
		panelConfiguration.add(labalNom);
		panelConfiguration.add(champNom);

		add(panelConfiguration, BorderLayout.CENTER);

		// Section centrale
		JPanel panelSelection = new JPanel(new GridLayout(3, 2, 5, 5));
		panelSelection.setBorder(BorderFactory.createTitledBorder("Sélection"));

		// Section inférieure
		JPanel panelType = new JPanel(new GridLayout(1, 1, 5, 5));

		boutonConfirmer = new JButton("Confirmer");
		boutonConfirmer.addActionListener(this);

		panelType.add(boutonConfirmer);

		add(panelType, BorderLayout.SOUTH);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == boutonConfirmer) {
			String id = champID.getText().trim();
			String nom = champNom.getText().trim();

			if (id.isEmpty() || nom.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}

			Ressource r = new Ressource(id, nom);
			ctrl.ajouterRessource(r);
			champID.setText("");
			champNom.setText("");
			this.panelRessource.maj();

			JOptionPane.showMessageDialog(this, "Ressource ajoutée", "Succès", JOptionPane.INFORMATION_MESSAGE);

			this.frameCreationRessource.dispose();
		}
	}
}