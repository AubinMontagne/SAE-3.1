package src.Vue;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import src.Controleur;
import src.Metier.*;

public class PanelEntiteAssociation extends JFrame implements ActionListener {
	private Controleur ctrl;

	private JPanel panelAssociations; // Panel pour les associations

	private JTextField   txtQuestion;
	private JButton      btnAjoutAssociation, btnEnregistrer;
	private int          nombreAssociations = 0; // Nombre d'associations

	private String cheminDossier;
	private String cheminImg;
	private int    difficulte;
	private String notion;
	private int    points;
	private int    temps;
	private List<String> lstLiens;
	private JEditorPane epEnonce;
	private JEditorPane epExplication;
	private int 		idMax;

	private PanelBanque panelBanque;
	private PanelCreationQuestion panelCreationQuestion;

	/**
	 * Constructeur de la class PanelEntiteAssociation
	 * @param ctrl	Le contrôleur
	 */
	public PanelEntiteAssociation(PanelCreationQuestion panelCreationQuestion, Controleur ctrl, String cheminDossier, String cheminImg, List<String> lstLiens, int difficulte, String notion, int points, int temps, PanelBanque panelBanque, JEditorPane enonce, JEditorPane explication, int idMax) {
		this.ctrl          = ctrl;
		this.panelCreationQuestion = panelCreationQuestion;

		this.cheminDossier = cheminDossier;
		this.cheminImg	   = cheminImg;
		this.panelBanque   = panelBanque;
		this.difficulte    = difficulte;
		this.notion        = notion;
		this.points        = points;
		this.temps         = temps;
		this.lstLiens	   = lstLiens;
		this.idMax   	   = idMax;

		this.epEnonce = epEnonce;
		this.epExplication = epExplication;


		setTitle("Créateur de Questions Entité-Association");
		setSize(600,400);
		setMinimumSize(this.getSize());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		this.setIconImage(new ImageIcon("java/data/Images/icon.png").getImage());

		// Panel pour la question
		JPanel panelQuestion = new JPanel(new BorderLayout());
		JLabel etiquetteQuestion = new JLabel("Question :");
		this.txtQuestion = new JTextField();
		panelQuestion.add(etiquetteQuestion, BorderLayout.NORTH);
		panelQuestion.add(this.txtQuestion, BorderLayout.CENTER);

		// Panel pour les associations
		this.panelAssociations = new JPanel();
		this.panelAssociations.setLayout(new BoxLayout(this.panelAssociations, BoxLayout.Y_AXIS));
		JScrollPane defilement = new JScrollPane(this.panelAssociations);

		// Bouton pour ajouter une association
		this.btnAjoutAssociation = new JButton("Ajouter une association");
		this.btnAjoutAssociation.setActionCommand("ajouterAssociation");

		// Bouton pour enregistrer
		this.btnEnregistrer = new JButton("Enregistrer");
		this.btnEnregistrer.setActionCommand("enregistrer");

		// Panel des boutons
		JPanel panelBoutons = new JPanel();
		panelBoutons.add(this.btnAjoutAssociation);
		panelBoutons.add(this.btnEnregistrer);

		// Ajout des composants à la fenêtre
		add(panelQuestion, BorderLayout.NORTH);
		add(defilement   , BorderLayout.CENTER);
		add(panelBoutons , BorderLayout.SOUTH);

		// Activation des listeners
        this.btnAjoutAssociation.addActionListener(this);
		this.btnEnregistrer.addActionListener(this);

		//Ajout de deux réponses pour rendre plus beau
		this.ajouterAssociation();
		this.ajouterAssociation();
	}

	// Methode
	/**
	 * Methode ajouterAssociation
	 */
	private void ajouterAssociation() 
	{
		JPanel panelAjoutAssociation = new JPanel();
		panelAjoutAssociation.setLayout(new BoxLayout(panelAjoutAssociation, BoxLayout.X_AXIS));

		// Champs pour les entités et les associations
		JTextField champEntite = new JTextField("Entité " + (++this.nombreAssociations), 10);
		JTextField champAssociation = new JTextField(
				"Association " +this.nombreAssociations,
				10
		);
		JButton boutonSupprimer = new JButton("Supprimer");

		// Listener pour supprimer une association
		boutonSupprimer.addActionListener(e -> {
			this.panelAssociations.remove(panelAjoutAssociation);
			this.panelAssociations.revalidate();
			this.panelAssociations.repaint();
		});

		panelAjoutAssociation.add(champEntite);
		panelAjoutAssociation.add(new JLabel(" -> "));
		panelAjoutAssociation.add(champAssociation);
		panelAjoutAssociation.add(boutonSupprimer);

		this.panelAssociations.add(panelAjoutAssociation);
		this.panelAssociations.revalidate();
		this.panelAssociations.repaint();
	}

	/**
	 * Methode enregistrerAssociations
	 */
	private void enregistrerAssociations() 
	{
		String question = this.txtQuestion.getText();
		if (question.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Veuillez entrer une question.", "Erreur", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Récupérer les entités et associations
		List<String> associations = new ArrayList<>();
		Component[] composants = this.panelAssociations.getComponents();
		for (Component composant : composants) 
		{
			if (composant instanceof JPanel) 
			{
				JPanel associationPanel = (JPanel) composant;
				JTextField champEntite = (JTextField) associationPanel.getComponent(0);
				JTextField champAssociation = (JTextField) associationPanel.getComponent(2);
				associations.add(champEntite.getText() + " -> " + champAssociation.getText());
			}
		}

		// Résumé des données
		StringBuilder resultats = new StringBuilder("Question : " + question + "\nAssociations :\n");
		for (String association : associations) 
		{
			resultats.append(association).append("\n");
		}



		HashMap<String, String> entiteAssociation = new HashMap<>();

		for (String association : associations)
		{
			String[] entiteAssociationSplit = association.split(" -> ");
			entiteAssociation.put(entiteAssociationSplit[0], entiteAssociationSplit[1]);
		}

		this.ctrl.creerQuestionEntiteAssociation(
				this.cheminDossier,
				this.difficulte,
				this.notion,
				this.temps,
				this.points,
				entiteAssociation,
				this.cheminImg,
				this.lstLiens,
				this.idMax
			);

		if(this.panelBanque != null) {this.panelBanque.maj();}

		JOptionPane.showMessageDialog(
				this,
				resultats.toString(),
				"Résumé",
				JOptionPane.INFORMATION_MESSAGE
		);

        this.panelCreationQuestion.actualiser();
        dispose();
	}

	/**
	 * Methode actionPerformed
	 * @param e L'évènement à traiter
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String commande = e.getActionCommand();
		switch (commande) 
		{
			case "ajouterAssociation":
				ajouterAssociation();
				break;
			case "enregistrer":
				enregistrerAssociations();
		}
	}
}