package src.Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;  
import java.util.List;

public class PanelEntiteAssociation extends JFrame implements ActionListener 
{

	private JPanel panelAssociations; // Panel pour les associations
	private int nombreAssociations = 0; // Nombre d'associations
	private JTextField champQuestion;
	private JButton boutonAjoutAssociation;
	private JButton boutonEnregistrer;

	public PanelEntiteAssociation() 
	{
		setTitle("Créateur de Questions Entité-Association");
		setSize(600,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// Panel pour la question
		JPanel panelQuestion = new JPanel(new BorderLayout());
		JLabel etiquetteQuestion = new JLabel("Question :");
		champQuestion = new JTextField();
		panelQuestion.add(etiquetteQuestion, BorderLayout.NORTH);
		panelQuestion.add(champQuestion, BorderLayout.CENTER);

		// Panel pour les associations
		panelAssociations = new JPanel();
		panelAssociations.setLayout(new BoxLayout(panelAssociations, BoxLayout.Y_AXIS));
		JScrollPane defilement = new JScrollPane(panelAssociations);

		// Bouton pour ajouter une association
		boutonAjoutAssociation = new JButton("Ajouter une association");
		boutonAjoutAssociation.setActionCommand("ajouterAssociation");

		// Bouton pour enregistrer
		boutonEnregistrer = new JButton("Enregistrer");
		boutonEnregistrer.setActionCommand("enregistrer");

		// Panel des boutons
		JPanel panelBoutons = new JPanel();
		panelBoutons.add(boutonAjoutAssociation);
		panelBoutons.add(boutonEnregistrer);

		// Ajout des composants à la fenêtre
		add(panelQuestion, BorderLayout.NORTH);
		add(defilement, BorderLayout.CENTER);
		add(panelBoutons, BorderLayout.SOUTH);

		// Activation des listeners
		boutonAjoutAssociation.addActionListener(this);
		boutonEnregistrer.addActionListener(this);
	}

	private void ajouterAssociation() 
	{
		JPanel panelAjoutAssociation = new JPanel();
		panelAjoutAssociation.setLayout(new BoxLayout(panelAjoutAssociation, BoxLayout.X_AXIS));

		// Champs pour les entités et les associations
		JTextField champEntite = new JTextField("Entité " + (++nombreAssociations), 10);
		JTextField champAssociation = new JTextField("Association " + nombreAssociations, 10);
		JButton boutonSupprimer = new JButton("Supprimer");

		// Listener pour supprimer une association
		boutonSupprimer.addActionListener(e -> {
			panelAssociations.remove(panelAjoutAssociation);
			panelAssociations.revalidate();
			panelAssociations.repaint();
		});

		panelAjoutAssociation.add(champEntite);
		panelAjoutAssociation.add(new JLabel(" -> "));
		panelAjoutAssociation.add(champAssociation);
		panelAjoutAssociation.add(boutonSupprimer);

		panelAssociations.add(panelAjoutAssociation);
		panelAssociations.revalidate();
		panelAssociations.repaint();
	}

	private void enregistrerAssociations() 
	{
		String question = champQuestion.getText();
		if (question.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Veuillez entrer une question.", "Erreur", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Récupérer les entités et associations
		List<String> associations = new ArrayList<>();
		Component[] composants = panelAssociations.getComponents();
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

		JOptionPane.showMessageDialog(this, resultats.toString(), "Résumé", JOptionPane.INFORMATION_MESSAGE);
	}

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
				break;
			default:
				break;
		}
	}


}