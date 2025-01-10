package src.Vue;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import src.Controleur;
import src.Metier.Question;

public class PanelElimination extends JFrame implements ActionListener
{
	private Controleur ctrl;
	private JPanel 	   panelReponses;
	private JButton    btnAjoutReponse;
	private JButton    btnEnregistrer;
	private JLabel     lblRep, lblOrdre, lblPoint;

	private int    nombreReponses = 0;

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
	 * Constructeur de la class PanelElimination
	 * @param ctrl	Le contrôleur
	 */
	public PanelElimination(
			PanelCreationQuestion panelCreationQuestion   , Controleur ctrl,
			String cheminDossier   , String cheminImg     , List<String> lstLiens,
			int difficulte         , String notion        , int points, int temps,
			PanelBanque panelBanque, JEditorPane enonce   , JEditorPane explication,
			int idMax )
	{
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

		this.epEnonce      = epEnonce;
		this.epExplication = epExplication;

		setTitle("QCM Builder - Créateur de Question élimination");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		this.setIconImage(new ImageIcon("java/data/Images/icon.png").getImage());

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

		// Label pour les colonnes
		this.lblOrdre = new JLabel("Ordre d'éliminations");
		this.lblRep   = new JLabel("Réponse");
		this.lblPoint = new JLabel("Nombre de points");

		JPanel panelLabel = new JPanel(new GridLayout(1, 3, 5, 5 ) );
		panelLabel.add(this.lblRep);
		panelLabel.add(this.lblPoint);
		panelLabel.add(this.lblOrdre);
		panelLabel.add(new JLabel(""));

		// Panel des boutons
		JPanel panelBoutons = new JPanel();
		panelBoutons.add(this.btnAjoutReponse);
		panelBoutons.add(this.btnEnregistrer);

		this.add(panelLabel   , BorderLayout.NORTH);
		this.add(defilement   , BorderLayout.CENTER);
		this.add(panelBoutons , BorderLayout.SOUTH);

		// Activation des listeners
		this.btnAjoutReponse.addActionListener(this);
		this.btnEnregistrer.addActionListener(this);

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
		JTextField txtReponse      = new JTextField();
		JCheckBox  cbBonneReponse  = new JCheckBox ("Correcte");
		JButton    btnSupprimer    = new JButton   ("Supprimer");

		btnSupprimer.addActionListener(e -> {
			this.panelReponses.remove(panelAjoutReponse);
			this.panelReponses.revalidate();
			this.panelReponses.repaint();
		});

		JTextField txtOrdreElim    = new JTextField();
		JTextField txtPointNegatif = new JTextField();

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
		int cptRepCorecte = 0;
		for (Component composant : composants)
		{
			if (composant instanceof JPanel)
			{
				JPanel reponse = (JPanel) composant;
				JTextField txtReponse      = (JTextField) reponse.getComponent(0);
				JTextField txtOrdreElim    = (JTextField) reponse.getComponent(1);
				JTextField txtPointNegatif = (JTextField) reponse.getComponent(2);
				JCheckBox  cbCorrecte      = (JCheckBox)  reponse.getComponent(3);

				if( cbCorrecte.isSelected() )
				{
					cptRepCorecte ++;
				}

				try
				{
					Double.parseDouble(txtOrdreElim.getText());
					Double.parseDouble(txtPointNegatif.getText());
				}
				catch (NumberFormatException e)
				{
					JOptionPane.showMessageDialog(
							this, "L'ordre ainsi que les points doivent être des entiers ou des double."
					);
					return;
				}


				reponses.put(txtReponse.getText(), new Double[]{Double.parseDouble(txtPointNegatif.getText()), Double.parseDouble(txtOrdreElim.getText())});
				if (cbCorrecte.isSelected())
				{
					reponseCorrecte = txtReponse.getText();
				}
			}
		}
		if(cptRepCorecte == 0 || cptRepCorecte > 1)
		{
			JOptionPane.showMessageDialog(this, "Nombre incorect de reponse bonne");
			return;
		}

		// Création de la question
		this.ctrl.creerQuestionElimination(
				this.cheminDossier,
				this.difficulte,
				this.notion,
				this.temps,
				this.points,
				reponses,
				reponseCorrecte,
				this.cheminImg,
				this.lstLiens,
				this.idMax
		);

		if(this.panelBanque != null) {this.panelBanque.maj();}

		Question.sauvegarderFichier(this.cheminDossier+"/Enonce.rtf"     , this.epEnonce);
		Question.sauvegarderFichier(this.cheminDossier+"/Explication.rtf", this.epExplication);

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
		switch (commande) {
			case "ajouterReponse":
				ajouterReponse();
				break;
			case "enregistrer" ->
				enregistrerElimination();
			default:
				break;
		}
	}
}