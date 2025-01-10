package src.Vue;

import src.Controleur;
import src.Metier.Question;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashMap;
import java.util.List;

public class PanelModifElimination extends JFrame implements ActionListener
{
	private Controleur ctrl;
	private JPanel 	   panelReponses;
	private JButton    btnAjoutReponse;
	private JButton    btnEnregistrer;

	private int    nombreReponses = 0;

	private String cheminImg;
	private List<String> lstLiens;
	private Question q;

	private JEditorPane epEnonce;
	private JEditorPane epExplication;

	private PanelBanque panelBanque;
	private PanelModifQuestion panelModifQuestion;


	/**
	 * Constructeur de la class PanelElimination
	 * @param ctrl	Le contrôleur
	 */
	public PanelModifElimination(
			PanelModifQuestion panelModifQuestion   , Controleur ctrl,
			String cheminImg, List<String> lstLiens, PanelBanque panelBanque,
			JEditorPane enonce   , JEditorPane explication, Question q)
	{
		this.ctrl          = ctrl;
		this.panelModifQuestion = panelModifQuestion;

		this.q = q;
		this.cheminImg	   = cheminImg;
		this.panelBanque   = panelBanque;
		this.lstLiens	   = lstLiens;

		this.epEnonce = epEnonce;
		this.epExplication = epExplication;

		setTitle("QCM Builder - Créateur de Question élimination");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		this.setIconImage(new ImageIcon("java/data/Images/icon.png").getImage());

		JPanel panelQuestion     = new JPanel(new BorderLayout());
		JLabel etiquetteQuestion = new JLabel("Question :");
		panelQuestion.add(etiquetteQuestion, BorderLayout.NORTH);

		// Panel pour ajouter les réponses
		this.panelReponses 		   = new JPanel();
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
		add(panelQuestion, BorderLayout.NORTH);
		add(defilement   , BorderLayout.CENTER);
		add(panelBoutons , BorderLayout.SOUTH);

		// Activation des listeners
		this.btnAjoutReponse.addActionListener(this);
		this.btnEnregistrer .addActionListener(this);

		//Ajout de deux réponses pour rendre plus beau
		this.ajouterReponse();
		this.ajouterReponse();
	}

	// Methode

	/**
	 * Methode ajouterReponse
	 */
	private void ajouterReponse()
	{
		JPanel panelAjoutReponse   = new JPanel();
		panelAjoutReponse.setLayout(new BoxLayout(panelAjoutReponse, BoxLayout.X_AXIS));
		JTextField txtReponse      = new JTextField("Réponse " + (++this.nombreReponses));
		JCheckBox  cbBonneReponse  = new JCheckBox ("Correcte");
		JButton    btnSupprimer    = new JButton   ("Supprimer");

		btnSupprimer.addActionListener(e -> {
			this.panelReponses.remove(panelAjoutReponse);
			this.panelReponses.revalidate();
			this.panelReponses.repaint();
		});

		JTextField txtOrdreElim    = new JTextField("ordre elim");
		JTextField txtPointNegatif = new JTextField("pnt-");

		panelAjoutReponse.add(txtReponse);
		panelAjoutReponse.add(txtOrdreElim);
		panelAjoutReponse.add(txtPointNegatif);

		panelAjoutReponse.add(cbBonneReponse);
		panelAjoutReponse.add(btnSupprimer);

		this.panelReponses.add(panelAjoutReponse);
		this.panelReponses.revalidate();
		this.panelReponses.repaint();
	}

	/**
	 * Methode enregistrerElimination
	 */
	private void enregistrerElimination()
	{
		Component[] composants  = this.panelReponses.getComponents();

		HashMap<String, Double[]> reponses = new HashMap<>();
		String reponseCorrecte = "";
		for (Component composant : composants)
		{
			if (composant instanceof JPanel)
			{
				JPanel reponse = (JPanel) composant;
				JTextField txtReponse      = (JTextField) reponse.getComponent(0);
				JTextField txtOrdreElim    = (JTextField) reponse.getComponent(1);
				JTextField txtPointNegatif = (JTextField) reponse.getComponent(2);
				JCheckBox  cbCorrecte      = (JCheckBox)  reponse.getComponent(3);

				reponses.put(txtReponse.getText(), new Double[]{Double.parseDouble(txtPointNegatif.getText()), Double.parseDouble(txtOrdreElim.getText())});
				if (cbCorrecte.isSelected())
				{
					reponseCorrecte = txtReponse.getText();
				}
			}
		}

		// Création de la question
		this.ctrl.modifQuestionElimination(
				reponses,
				reponseCorrecte,
				this.cheminImg,
				this.lstLiens,
				this.q
		);

		if(this.panelBanque != null) {this.panelBanque.maj();}

		Question.sauvegarderFichier(this.q.getDossierChemin()+"/epEnonce.rtf", this.epEnonce);
		Question.sauvegarderFichier(this.q.getDossierChemin()+"/epExplication.rtf", this.epExplication);

		this.panelModifQuestion.fermer();
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
			case "ajouterReponse" -> ajouterReponse();
			case "enregistrer"    -> enregistrerElimination();
		}
	}
}