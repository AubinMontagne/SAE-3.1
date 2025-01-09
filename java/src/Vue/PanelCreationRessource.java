package src.Vue;

import src.Controleur;
import src.Metier.Ressource;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.TitledBorder;
public class PanelCreationRessource extends JPanel implements ActionListener {

	private Controleur 			   ctrl;
	private JButton 			   btnConfirmer;
	private JTextField 			   txtID, txtNom;
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
		TitledBorder titledBorder = BorderFactory.createTitledBorder("Configuration");
		titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 26));
		panelConfiguration.setBorder(titledBorder);

		JLabel labelID = new JLabel("Identifiant de la ressource :");
		labelID.setFont(new Font("Arial", Font.PLAIN, 20));
		this.txtID   = new JTextField();
		this.txtID.setPreferredSize(new Dimension(10, 10));

		JLabel labelNom = new JLabel("Nom de la ressource :");
		labelNom.setFont(new Font("Arial", Font.PLAIN, 20));
		this.txtNom   = new JTextField();
		this.txtNom.setPreferredSize(new Dimension(10, 10));

		panelConfiguration.add(labelID);
		panelConfiguration.add(this.txtID);
		panelConfiguration.add(labelNom);
		panelConfiguration.add(this.txtNom);

		add(panelConfiguration, BorderLayout.CENTER);

		// Section centrale
		JPanel panelSelection = new JPanel(new GridLayout(3, 2, 5, 5));
		panelSelection.setBorder(BorderFactory.createTitledBorder("Sélection"));

		// Section inférieure
		JPanel panelType = new JPanel(new GridLayout(1, 1, 5, 5));

		this.btnConfirmer = new JButton("Confirmer");
		btnConfirmer.setBackground(new Color(163,206,250));
		btnConfirmer.setFont      (new Font("Arial", Font.PLAIN, 22));
		this.btnConfirmer.addActionListener(this);

		panelType.add(this.btnConfirmer);

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
		if (e.getSource() == this.btnConfirmer) {
			String id  = this.txtID.getText().trim();
			String nom = this.txtNom.getText().trim();

			if (id.isEmpty() || nom.isEmpty()) {
				JOptionPane.showMessageDialog(
						this,
						"Veuillez remplir tous les champs",
						"Erreur",
						JOptionPane.ERROR_MESSAGE
				);
				return;
			}

			Ressource r = new Ressource(id, nom);
			this.ctrl.ajouterRessource(r);
			this.ctrl.miseAJourFichiers();

			JOptionPane.showMessageDialog(
					this,
					"Ressource ajoutée",
					"Succès",
					JOptionPane.INFORMATION_MESSAGE
			);

			this.frameCreationRessource.dispose();
		}
	}
}