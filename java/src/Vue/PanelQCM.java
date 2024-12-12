package src.Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.Controleur;

public class PanelQCM extends JFrame implements ActionListener 
{

	// FONCTION EXPLICATION A RAJOUTER

	private Controleur ctrl;
	private JPanel panelReponses; // Panel pour les réponses
	private int nombreReponses = 0; // Nombre de réponses
	private JTextField champQuestion;
	private JButton boutonAjoutReponse;
	private JButton boutonEnregistrer;

	public PanelQCM(Controleur ctrl) 
	{
		this.ctrl = ctrl;
		setTitle("Créateur de QCM");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel panelQuestion = new JPanel(new BorderLayout());
		JLabel etiquetteQuestion = new JLabel("Question :");
		champQuestion = new JTextField();
		panelQuestion.add(etiquetteQuestion, BorderLayout.NORTH);
		panelQuestion.add(champQuestion, BorderLayout.CENTER);

		// Panel pour ajouter les réponses
		panelReponses = new JPanel();
		panelReponses.setLayout(new BoxLayout(panelReponses, BoxLayout.Y_AXIS));
		JScrollPane defilement = new JScrollPane(panelReponses);

		// Bouton pour ajouter une réponse
		boutonAjoutReponse = new JButton("Ajouter une réponse");
		boutonAjoutReponse.setActionCommand("ajouterReponse");

		// Bouton pour enregistrer
		boutonEnregistrer = new JButton("Enregistrer");
		boutonEnregistrer.setActionCommand("enregistrer");

		// Panel des boutons
		JPanel panelBoutons = new JPanel();
		panelBoutons.add(boutonAjoutReponse);
		panelBoutons.add(boutonEnregistrer);

		// Ajout des composants à la fenêtre
		add(panelQuestion, BorderLayout.NORTH);
		add(defilement, BorderLayout.CENTER);
		add(panelBoutons, BorderLayout.SOUTH);

		// Activation des listeners
		boutonAjoutReponse.addActionListener(this);
		boutonEnregistrer.addActionListener(this);
	}

	private void ajouterReponse() 
	{
		JPanel panelAjoutReponse = new JPanel();
		panelAjoutReponse.setLayout(new BoxLayout(panelAjoutReponse, BoxLayout.X_AXIS));
		JTextField champReponse = new JTextField("Réponse " + (++nombreReponses));
		JCheckBox caseBonneReponse = new JCheckBox("Correcte");
		JButton boutonSupprimer = new JButton("Supprimer");

		boutonSupprimer.addActionListener(e -> {
			panelReponses.remove(panelAjoutReponse);
			panelReponses.revalidate();
			panelReponses.repaint();
		});

		panelAjoutReponse.add(champReponse);
		panelAjoutReponse.add(caseBonneReponse);
		panelAjoutReponse.add(boutonSupprimer);

		panelReponses.add(panelAjoutReponse);
		panelReponses.revalidate();
		panelReponses.repaint();
	}

	private void enregistrerQCM() 
	{
		String question = champQuestion.getText();
		if (question.isEmpty()) 
		{
			JOptionPane.showMessageDialog(this, "Veuillez entrer une question.", "Erreur", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// Résumé de la question faite (debug)
		StringBuilder resultats = new StringBuilder("Question : " + question + "\nRéponses :\n");
		Component[] composants = panelReponses.getComponents();
		for (Component composant : composants) 
		{
			if (composant instanceof JPanel) 
			{
				JPanel reponse = (JPanel) composant;
				JTextField champReponse = (JTextField) reponse.getComponent(0);
				JCheckBox estCorrecte = (JCheckBox) reponse.getComponent(1);
				resultats.append("- ").append(champReponse.getText())
						.append(estCorrecte.isSelected() ? " (correcte)\n" : " (incorrecte)\n");
			}
		}
		JOptionPane.showMessageDialog(this, resultats.toString(), "Résumé", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String commande = e.getActionCommand();
		switch (commande) {
			case "ajouterReponse":
				ajouterReponse();
				break;
			case "enregistrer":
				enregistrerQCM();
				break;
			default:
				break;
		}
	}

	
}
