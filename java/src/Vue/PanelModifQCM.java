package src.Vue;

import src.Controleur;
import src.Metier.QCM;
import src.Metier.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class PanelModifQCM extends JFrame implements ActionListener {

	private Controleur  ctrl;
	private JPanel      panelReponses; // Panel pour les réponses
	private int         nombreReponses = 0; // Nombre de réponses
	private JButton     btnAjoutReponse;
	private JButton     btnEnregistrer;
	private boolean     modeReponseUnique; // Checkbox pour activer/désactiver le mode réponse unique

	private boolean     estModeUnique = false; // Par défaut, mode "plusieurs réponses correctes"
	private ButtonGroup groupReponses; // Utilisé pour le mode "réponse unique"

	private String cheminImg;
	private List<String> lstLiens;
	private JEditorPane epEnonce;
	private JEditorPane epExplication;

	private PanelBanque panelBanque;
	private PanelModifQuestion panelModifQuestion;
	private Question q;

	public PanelModifQCM(
			PanelModifQuestion panelModifQuestion   , Controleur ctrl, String cheminImg,
			List<String> lstLiens, PanelBanque panelBanque, JEditorPane epEnonce,
			JEditorPane epExplication, Question q, boolean estModeUnique ) {

		this.ctrl          = ctrl;
		this.panelModifQuestion = panelModifQuestion;

		this.q = q;
		this.cheminImg	   = cheminImg;
		this.panelBanque   = panelBanque;
		this.estModeUnique = estModeUnique;
		this.lstLiens	   = lstLiens;

		this.epEnonce = epEnonce;
		this.epExplication = epExplication;

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
		this.btnAjoutReponse = new JButton("Ajouter une réponse");
		this.btnAjoutReponse.setActionCommand("ajouterReponse");

		// Bouton pour enregistrer
		this.btnEnregistrer = new JButton("Enregistrer");
		this.btnEnregistrer.setActionCommand("enregistrer");

		// Panel des boutons
		JPanel panelBoutons = new JPanel();
		panelBoutons.add(this.btnAjoutReponse);
		panelBoutons.add(this.btnEnregistrer);

		// Ajout des composants à la fenêtre
		add(defilement   , BorderLayout.CENTER);
		add(panelBoutons , BorderLayout.SOUTH);

		// Activation des listeners
		this.btnAjoutReponse.addActionListener(this);
		this.btnEnregistrer .addActionListener(this);

		//Ajout de deux réponses pour rendre plus beau
		if(!(q instanceof QCM)) {
			this.ajouterReponse();
			this.ajouterReponse();
		} else {
			QCM qcm = (QCM) (q);
			for(String proposition : qcm.getReponses().keySet()){
				this.ajouterReponse(proposition,qcm.getReponses().get(proposition),qcm.estVraiouFaux());
			}
		}
	}

	private void ajouterReponse() {
		JPanel panelAjoutReponse = new JPanel();
		panelAjoutReponse.setLayout(new BoxLayout(panelAjoutReponse, BoxLayout.X_AXIS));
		JTextField txtReponse = new JTextField("Réponse " + (++nombreReponses));

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

		panelAjoutReponse.add(txtReponse);
		panelAjoutReponse.add(caseCorrecte);
		panelAjoutReponse.add(boutonSupprimer);

		this.panelReponses.add(panelAjoutReponse);
		this.panelReponses.revalidate();
		this.panelReponses.repaint();
	}

	private void ajouterReponse(String enonceProposition, Boolean estCorrect, Boolean estUnique) {
		JPanel panelAjoutReponse = new JPanel();
		panelAjoutReponse.setLayout(new BoxLayout(panelAjoutReponse, BoxLayout.X_AXIS));
		JTextField txtReponse = new JTextField(enonceProposition);

		JComponent caseCorrecte;
		if (this.estModeUnique && estUnique) {
			JRadioButton boutonRadio = new JRadioButton();
			if (this.groupReponses == null) {
				this.groupReponses = new ButtonGroup();
			}
			groupReponses.add(boutonRadio);
			if ( estCorrect){caseCorrecte = boutonRadio;}
			else {caseCorrecte = null;}
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

		panelAjoutReponse.add(txtReponse);
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
				JTextField txtReponse = (JTextField) panelReponse.getComponent(0);
				JComponent caseCorrecte = (JComponent) panelReponse.getComponent(1);

				String texteReponse = txtReponse.getText().trim();
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

		this.ctrl.modifQuestionQCM(
				this.estModeUnique,
				reponses,
				this.cheminImg,
				this.lstLiens,
				this.q
		);

		Question.sauvegarderFichier(this.q.getDossierChemin()+"/epEnonce.rtf", this.epEnonce);
		Question.sauvegarderFichier(this.q.getDossierChemin()+"/epExplication.rtf", this.epExplication);

		if(this.panelBanque != null) {this.panelBanque.maj();}

		Question.sauvegarderFichier(this.q.getDossierChemin()+"/epEnonce.rtf", this.epEnonce);
		Question.sauvegarderFichier(this.q.getDossierChemin()+"/epExplication.rtf", this.epExplication);

		JOptionPane.showMessageDialog(this, "Question enregistrée avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);

		this.panelModifQuestion.fermer();
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
				break;
		}
	}
}
