package src.Vue;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;
import src.Controleur;
import src.Metier.Question;

public class PanelQCM extends JFrame implements ActionListener {

	private Controleur  ctrl;
	private JPanel      panelReponses; // Panel pour les réponses
	private int         nombreReponses = 0; // Nombre de réponses
	private JButton     boutonAjoutReponse;
	private JButton     boutonEnregistrer;
	private boolean     modeReponseUnique; // Checkbox pour activer/désactiver le mode réponse unique

	private boolean     estModeUnique = false; // Par défaut, mode "plusieurs réponses correctes"
	private ButtonGroup groupReponses; // Utilisé pour le mode "réponse unique"

	private String cheminDossier;
	private String cheminImg;
	private int    difficulte;
	private String notion;
	private int    points;
	private int    temps;
	private List<String> lstLiens;
	private JEditorPane enonce;
	private JEditorPane explication;

	private PanelBanque panelBanque;

	public PanelQCM(Controleur ctrl, String cheminDossier, String cheminImg, List<String> lstLiens, int difficulte, String notion, int points, int temps, PanelBanque panelBanque, boolean estModeUnique, JEditorPane enonce, JEditorPane explication) {
		this.ctrl          = ctrl;

		this.cheminDossier = cheminDossier;
		this.cheminImg	   = cheminImg;
		this.panelBanque   = panelBanque;
		this.estModeUnique = estModeUnique;
		this.difficulte    = difficulte;
		this.notion        = notion;
		this.points        = points;
		this.temps         = temps;
		this.lstLiens	   = lstLiens;

		this.enonce = enonce;
		this.explication = explication;

		setTitle("QCM Builder - Créateur de QCM");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		this.setIconImage(new ImageIcon("java/data/Images/icon.png").getImage());

		// Panel pour ajouter les réponses
		this.panelReponses = new JPanel();
		this.panelReponses.setLayout(new BoxLayout(this.panelReponses, BoxLayout.Y_AXIS));
		JScrollPane defilement = new JScrollPane(this.panelReponses);

		// Bouton pour ajouter une réponse
		this.boutonAjoutReponse = new JButton("Ajouter une réponse");
		this.boutonAjoutReponse.setActionCommand("ajouterReponse");

		// Bouton pour enregistrer
		this.boutonEnregistrer = new JButton("Enregistrer");
		this.boutonEnregistrer.setActionCommand("enregistrer");

		// Panel des boutons
		JPanel panelBoutons = new JPanel();
		panelBoutons.add(this.boutonAjoutReponse);
		panelBoutons.add(this.boutonEnregistrer);

		// Ajout des composants à la fenêtre
		add(defilement   , BorderLayout.CENTER);
		add(panelBoutons , BorderLayout.SOUTH);

		// Activation des listeners
		this.boutonAjoutReponse.addActionListener(this);
		this.boutonEnregistrer .addActionListener(this);

		//Ajout de deux réponses pour rendre plus beau
		this.ajouterReponse();
		this.ajouterReponse();
	}

	private void ajouterReponse() {
		JPanel panelAjoutReponse = new JPanel();
		panelAjoutReponse.setLayout(new BoxLayout(panelAjoutReponse, BoxLayout.X_AXIS));
		JTextField champReponse = new JTextField("Réponse " + (++nombreReponses));

		JComponent caseCorrecte;
		if (this.estModeUnique) {
			JRadioButton boutonRadio = new JRadioButton();
			if (this.groupReponses == null) {
				this.groupReponses = new ButtonGroup();
			}
			groupReponses.add(boutonRadio);
			caseCorrecte = boutonRadio;
		} else {
			caseCorrecte = new JCheckBox("Correcte");
		}

		JButton boutonSupprimer = new JButton("Supprimer");
		boutonSupprimer.addActionListener(e -> {
			if (caseCorrecte instanceof JRadioButton && this.groupReponses != null) {
				this.groupReponses.remove((JRadioButton) caseCorrecte);
			}
			this.panelReponses.remove(panelAjoutReponse);
			this.panelReponses.revalidate();
			this.panelReponses.repaint();
		});

		panelAjoutReponse.add(champReponse);
		panelAjoutReponse.add(caseCorrecte);
		panelAjoutReponse.add(boutonSupprimer);

		this.panelReponses.add(panelAjoutReponse);
		this.panelReponses.revalidate();
		this.panelReponses.repaint();
	}

	private void enregistrerQCMAvecHashMap() {

		if (this.panelReponses.getComponentCount() == 0) {
			JOptionPane.showMessageDialog(this, "Veuillez ajouter au moins une réponse.", "Erreur", JOptionPane.ERROR_MESSAGE);
			return;
		}

		HashMap<String, Boolean> reponses = new HashMap<>();
		boolean auMoinsUneReponseCorrecte = false;

		for (Component composant : this.panelReponses.getComponents()) {
			if (composant instanceof JPanel) {
				JPanel panelReponse = (JPanel) composant;
				JTextField champReponse = (JTextField) panelReponse.getComponent(0);
				JComponent caseCorrecte = (JComponent) panelReponse.getComponent(1);

				String texteReponse = champReponse.getText().trim();
				boolean estCorrecte = caseCorrecte instanceof JCheckBox
						? ((JCheckBox) caseCorrecte).isSelected()
						: ((JRadioButton) caseCorrecte).isSelected();

				if (texteReponse.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Une réponse ne peut pas être vide.", "Erreur", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (reponses.containsKey(texteReponse)) {
					JOptionPane.showMessageDialog(this, "Une réponse dupliquée a été détectée : " + texteReponse, "Erreur", JOptionPane.ERROR_MESSAGE);
					return;
				}
				reponses.put(texteReponse, estCorrecte);
				if (estCorrecte) {
					auMoinsUneReponseCorrecte = true;
				}
			}
		}

		if (!auMoinsUneReponseCorrecte) {
			JOptionPane.showMessageDialog(this, "Veuillez sélectionner au moins une réponse correcte.", "Erreur", JOptionPane.ERROR_MESSAGE);
			return;
		}

		int idMax = 0;
		for(Question q : ctrl.getQuestions())
		{
			if(q.getId() > idMax)
			{
				idMax = q.getId();
			}
		}

		this.ctrl.creerQuestionQCM(
				this.cheminDossier,
				this.difficulte,
				this.notion,
				this.temps,
				this.points,
				false,
				reponses,
				this.cheminImg,
				this.lstLiens,
				idMax
		);

		Question.sauvegarderFichier(this.cheminDossier+"/enonce.rtf", this.enonce);
		Question.sauvegarderFichier(this.cheminDossier+"/explication.rtf", this.explication);

		if(this.panelBanque != null) {this.panelBanque.maj();}

		JOptionPane.showMessageDialog(this, "Question enregistrée avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);

		dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String commande = e.getActionCommand();
		switch (commande) {
			case "ajouterReponse":
				ajouterReponse();
				break;

			case "enregistrer":
				enregistrerQCMAvecHashMap();
				break;

			default:
				System.out.println("Commande inconnue : " + commande);
				break;
		}
	}
}
