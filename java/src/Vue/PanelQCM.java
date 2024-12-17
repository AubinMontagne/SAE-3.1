package src.Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

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

	int 	difficulte;
	String 	notion;
	int 	points;
	int 	temps;

	public PanelQCM(Controleur ctrl,int difficulte,String notion,int points,int temps)
	{
		this.ctrl = ctrl;

		this.difficulte=difficulte;
		this.notion=notion;
		this.points=points;
		this.temps=temps;

		setTitle("Créateur de QCM");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

	private void enregistrerQCMAvecHashMap() {
		String question = champQuestion.getText().trim();
		if (question.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Veuillez entrer une question.", "Erreur", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (panelReponses.getComponentCount() == 0) {
			JOptionPane.showMessageDialog(this, "Veuillez ajouter au moins une réponse.", "Erreur", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Prépare une HashMap temporaire pour les réponses
		HashMap<String, Boolean> reponses = new HashMap<>();
		boolean auMoinsUneReponseCorrecte = false;

		for (Component composant : panelReponses.getComponents()) {
			if (composant instanceof JPanel) {
				JPanel panelReponse = (JPanel) composant;
				JTextField champReponse = (JTextField) panelReponse.getComponent(0);
				JCheckBox caseCorrecte = (JCheckBox) panelReponse.getComponent(1);

				String texteReponse = champReponse.getText().trim();
				boolean estCorrecte = caseCorrecte.isSelected();

				// Validation des réponses
				if (texteReponse.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Une réponse ne peut pas être vide.", "Erreur", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (reponses.containsKey(texteReponse)) {
					JOptionPane.showMessageDialog(this, "Une réponse dupliquée a été détectée : " + texteReponse, "Erreur", JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Ajout à la HashMap temporaire
				reponses.put(texteReponse, estCorrecte);
				if (estCorrecte) {
					auMoinsUneReponseCorrecte = true;
				}
			}
		}

		// Vérifie qu'au moins une réponse correcte est présente
		if (!auMoinsUneReponseCorrecte) {
			JOptionPane.showMessageDialog(this, "Veuillez sélectionner au moins une réponse correcte.", "Erreur", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Appelle le contrôleur pour créer la question
		this.ctrl.creerQuestionQCM(
				question,
				difficulte,
				notion,
				temps,
				points,
				false, // "vraiOuFaux" est fixé à false ici
				reponses
		);

		JOptionPane.showMessageDialog(this, "Question enregistrée avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
		ctrl.getMetier().saveQuestions("java/data/");
		dispose(); // Ferme la fenêtre après l'enregistrement
	}


	@Override
	public void actionPerformed(ActionEvent e)
	{
		String commande = e.getActionCommand();
		switch (commande) {
			case "ajouterReponse":
				// Ajoute une nouvelle réponse à l'interface
				ajouterReponse();
				break;

			case "enregistrer":
				// Gère l'enregistrement du QCM avec la validation des réponses
				enregistrerQCMAvecHashMap();
				break;

			default:
				System.out.println("Commande inconnue : " + commande);
				break;
		}
	}


}
