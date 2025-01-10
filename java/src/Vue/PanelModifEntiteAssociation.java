package src.Vue;

import src.Controleur;
import src.Metier.AssociationElement;
import src.Metier.Question;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PanelModifEntiteAssociation extends JFrame implements ActionListener
{
	private Controleur ctrl;

	private JPanel             panelAssociations; // Panel pour les associations
	private PanelBanque        panelBanque;
	private PanelModifQuestion panelModifQuestion ;
	private JButton            btnAjoutAssociation, btnEnregistrer;
	private JEditorPane        epEnonce;
	private JEditorPane        epExplication;

	private Question     q;
	private List<String> lstLiens;
	private String       cheminImg;
	private int          nombreAssociations = 0;
	/**
	 * Constructeur de la class PanelEntiteAssociation
	 * @param ctrl	Le contrôleur
	 */
	public PanelModifEntiteAssociation(PanelModifQuestion panelModifQuestion, Controleur ctrl, String cheminImg, List<String> lstLiens, PanelBanque panelBanque, JEditorPane enonce, JEditorPane explication, Question q)
	{
		this.ctrl          = ctrl;
		this.panelModifQuestion = panelModifQuestion;

		this.q = q;
		this.cheminImg	   = cheminImg;
		this.panelBanque   = panelBanque;
		this.lstLiens	   = lstLiens;

		this.epEnonce = enonce;
		this.epExplication = explication;


		setTitle("Créateur de Questions Entité-Association");
		setSize(600,400);
		setMinimumSize(this.getSize());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		this.setIconImage(new ImageIcon("java/data/Images/icon.png").getImage());

		// Panel pour la question
		JPanel panelQuestion = new JPanel(new BorderLayout());
		JLabel etiquetteQuestion = new JLabel("Question :");
		panelQuestion.add(etiquetteQuestion, BorderLayout.NORTH);

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
		if (!(this.q instanceof AssociationElement))
		{
			this.ajouterAssociation();
			this.ajouterAssociation();
		}
		else
		{
			AssociationElement ae = (AssociationElement) (this.q);
			for(String gauche : ae.getAssociations().keySet())
			{
				this.ajouterAssociation(new String[]{gauche,ae.getAssociations().get(gauche)});
			}
		}
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
		JTextField txtEntite      = new JTextField("Entité " + (++this.nombreAssociations), 10);
		JTextField txtAssociation = new JTextField(
				"Association " +this.nombreAssociations,
				10
		);
		JButton btnSupprimer = new JButton("Supprimer");

		// Listener pour supprimer une association
		btnSupprimer.addActionListener(e -> {
			this.panelAssociations.remove(panelAjoutAssociation);
			this.panelAssociations.revalidate();
			this.panelAssociations.repaint();
		});

		panelAjoutAssociation.add(txtEntite);
		panelAjoutAssociation.add(new JLabel(" -> "));
		panelAjoutAssociation.add(txtAssociation);
		panelAjoutAssociation.add(btnSupprimer);

		this.panelAssociations.add(panelAjoutAssociation);
		this.panelAssociations.revalidate();
		this.panelAssociations.repaint();
	}
	private void ajouterAssociation(String[] donnees)
	{
		JPanel panelAjoutAssociation = new JPanel();
		panelAjoutAssociation.setLayout(new BoxLayout(panelAjoutAssociation, BoxLayout.X_AXIS));

		// Champs pour les entités et les associations
		JTextField txtEntite = new JTextField(donnees[0], 15);
		JTextField txtAssociation = new JTextField(donnees[1], 15);
		JButton btnSupprimer = new JButton("Supprimer");

		// Listener pour supprimer une association
		btnSupprimer.addActionListener(e -> {
			this.panelAssociations.remove(panelAjoutAssociation);
			this.panelAssociations.revalidate();
			this.panelAssociations.repaint();
		});

		panelAjoutAssociation.add(txtEntite);
		panelAjoutAssociation.add(new JLabel(" -> "));
		panelAjoutAssociation.add(txtAssociation);
		panelAjoutAssociation.add(btnSupprimer);

		this.panelAssociations.add(panelAjoutAssociation);
		this.panelAssociations.revalidate();
		this.panelAssociations.repaint();
	}

	/**
	 * Methode enregistrerAssociations
	 */
	private void enregistrerAssociations() 
	{

		// Récupérer les entités et associations
		List<String> associations = new ArrayList<>();
		Component[] composants = this.panelAssociations.getComponents();
		for (Component composant : composants) 
		{
			if (composant instanceof JPanel) 
			{
				JPanel associationPanel = (JPanel) composant;
				JTextField txtEntite = (JTextField) associationPanel.getComponent(0);
				JTextField txtAssociation = (JTextField) associationPanel.getComponent(2);
				associations.add(txtEntite.getText() + " -> " + txtAssociation.getText());
			}
		}

		// Résumé des données
		StringBuilder resultats = new StringBuilder("Question : " + "\nAssociations :\n");
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

		this.ctrl.modifQuestionEntiteAssociation(
				entiteAssociation,
				this.cheminImg,
				this.lstLiens,
				this.q
			);

		if(this.panelBanque != null) {this.panelBanque.maj();}

		Question.sauvegarderFichier(this.q.getDossierChemin()+"/epEnonce.rtf", this.epEnonce);
		Question.sauvegarderFichier(this.q.getDossierChemin()+"/epExplication.rtf", this.epExplication);

		JOptionPane.showMessageDialog(
				this,
				resultats.toString(),
				"Résumé",
				JOptionPane.INFORMATION_MESSAGE
		);

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
			case "ajouterAssociation":
				ajouterAssociation();
				break;
			case "enregistrer":
				enregistrerAssociations();
		}
	}
}