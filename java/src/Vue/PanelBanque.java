package src.Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Iterator;

import src.Metier.Question;
import src.Metier.QCM;
import src.Metier.EliminationReponse;
import src.Metier.AssociationElement;
import src.Metier.Notion;
import src.Controleur;
import src.Metier.Ressource;

public class PanelBanque extends JPanel implements  ActionListener, ItemListener {
	private boolean ignorerEvents = false;

    private Controleur           ctrl;
	private Notion               notion;
	private JButton              btnCreaQuest;
	private JButton              btnSupp;
	private JButton              btnModif;
    private JPanel               panelBanque;
    private JTable               tbQuestion;
	private JComboBox<Notion>    mdNotions;
	private JComboBox<Ressource> mdRessources;
	private ArrayList<Question>  listQ;

	// Constructeur

	/**
	 * Constructeur de la class PanelBanque
	 * @param ctrl	Le contrôleur
	 */
    public PanelBanque(Controleur ctrl){
        this.ctrl         = ctrl;
        this.panelBanque  = new JPanel();
		this.setLayout (new BorderLayout());
		this.setVisible(true);

        String[] tabEntetes = {"Enoncé", "Difficulté", "Ressource", "Notion", "Points", "Type de question"};

		this.listQ = this.ctrl.getQuestions();
        String[][] data = new String[this.listQ.size()][6];

		for(int i = 0; i < this.listQ.size();i++)
		{
			String typeQuestion = "";

			if(this.listQ.get(i) instanceof QCM)
			{
				if(((QCM) this.listQ.get(i)).estVraiouFaux())
					typeQuestion = "Réponse unique";
				else
					typeQuestion = "QCM";
			}else if(this.listQ.get(i) instanceof EliminationReponse){
				typeQuestion = "Elimination Réponse";
			} else {
				typeQuestion = "Association d'éléments";
			}

			data[i][0] = this.listQ.get(i).getEnonce();
			data[i][1] = this.listQ.get(i).getDifficulte().getNom();
			data[i][2] = this.listQ.get(i).getNotion().getRessourceAssociee().getNom();
			data[i][3] = this.listQ.get(i).getNotion().getNom();
			data[i][4] = "" + this.listQ.get(i).getPoint();
			data[i][5] = typeQuestion;
		}
        DefaultTableModel model = new DefaultTableModel(data, tabEntetes);
        this.tbQuestion = new JTable(model);

		this.mdRessources   = new JComboBox<>(ctrl.getRessources().toArray(new Ressource[0]));
		Ressource placeHolder = new Ressource(" "," ");
		this.mdRessources.insertItemAt(placeHolder, 0);
		this.mdRessources.setSelectedItem(placeHolder);
		this.mdNotions		= new JComboBox<>();

		JPanel panelParametre = new JPanel();

        // Nombre maximal de lignes sans scroll
        int maxVisibleRows = 5;

        // Calcul dynamique de la hauteur
        int rowHeight     = this.tbQuestion.getRowHeight();
        int headerHeight  = this.tbQuestion.getTableHeader().getHeight();
        int visibleHeight = rowHeight * Math.min(this.tbQuestion.getRowCount(), maxVisibleRows) + headerHeight;

        // Ajuster la taille visible de la table
        this.tbQuestion.setPreferredScrollableViewportSize(new Dimension(800, visibleHeight));

		JScrollPane scrollPane = new JScrollPane(this.tbQuestion);
		this.btnSupp           = new JButton("Supprimer Question");
		this.btnCreaQuest      = new JButton("Nouvelle Question");
		this.btnModif          = new JButton("Modifier Question");

		this.btnSupp.setBackground		(new Color(163,206,250));
		this.btnSupp.setFont			(new Font("Arial", Font.PLAIN, 22));
		this.btnCreaQuest.setBackground (new Color(163,206,250));
		this.btnCreaQuest.setFont	    (new Font("Arial", Font.PLAIN, 22));
		this.btnModif.setBackground		(new Color(163,206,250));
		this.btnModif.setFont			(new Font("Arial", Font.PLAIN, 22));


		panelParametre.add(this.mdRessources);
		panelParametre.add(this.mdNotions);

		this.panelBanque.add(panelParametre   , BorderLayout.NORTH );
		this.panelBanque.add(scrollPane       , BorderLayout.CENTER);
		this.panelBanque.add(this.btnSupp     , BorderLayout.SOUTH );
		this.panelBanque.add(this.btnCreaQuest, BorderLayout.SOUTH );
		this.panelBanque.add(this.btnModif    , BorderLayout.SOUTH );

        this.add(panelBanque);

		this.btnSupp     .addActionListener(this);
		this.btnCreaQuest.addActionListener(this);
		this.btnModif    .addActionListener(this);

		this.mdRessources.addItemListener(this);
		this.mdNotions   .addItemListener(this);
    }


	// Methode
	/**
	 * Methode actionPerformed
	 * @param e L'évènement à traiter
	 */
	public void actionPerformed(ActionEvent e){
        if ( this.btnCreaQuest == e.getSource())
			if (!((Ressource)(this.mdRessources.getSelectedItem())).getId().equals(" ")) {
            	FrameCreationQuestion.creerFrameCreationQuestion(this.ctrl, this, (Ressource)(this.mdRessources.getSelectedItem()), (Notion)(this.mdNotions.getSelectedItem()));
			} else {
				FrameCreationQuestion.creerFrameCreationQuestion(this.ctrl, this, null, null);
			}
		if (this.btnSupp == e.getSource()) {
			int row = this.tbQuestion.getSelectedRow();
			Iterator<Question> iterator = this.ctrl.getQuestions().iterator();
			while (iterator.hasNext()) {
				Question q = iterator.next();
				if (this.listQ.get(row) == q) {
					iterator.remove();
					this.ctrl.supprimerQuestion(q);
					this.maj();
					this.ctrl.miseAJourFichiers();
					break;
				}
			}
		}

		if(this.btnModif == e.getSource()){
			int row = this.tbQuestion.getSelectedRow();
			for(int i = 0; i < this.ctrl.getQuestions().size();i++) {
				if( this.listQ.get(row) == this.listQ.get(i)) {
					if (this.listQ.get(i) instanceof QCM) {
						HashMap<String, Boolean> hmReponses = ((QCM) this.listQ.get(i)).getReponses();
						FrameModifQuestion.creerFrameModifQCM(this.ctrl, this.listQ.get(i), hmReponses); // Question type questionnaire

					} else if (this.listQ.get(i) instanceof EliminationReponse) {
						HashMap<String, Double[]> hmReponses = ((EliminationReponse) this.listQ.get(i)).getHmReponses();
						FrameModifQuestion.creerFrameModifElimRep(this.ctrl, this.listQ.get(i), hmReponses); // Question type EliminationReponse

					} else {
						HashMap<String, String> hmReponses =  ((AssociationElement) this.listQ.get(i)).getAssociations();
						FrameModifQuestion.creerFrameModifAssoElt(this.ctrl, this.listQ.get(i), hmReponses); // Question type Association
					}
				}
			}
		}

	}

	public void maj(){
		String[] tabEntetes = {"Enoncé", "Difficulté", "Ressource", "Notion", "Points", "Type de question"};
		ArrayList<Question> questList;

		if (this.notion != null) {
			questList = this.ctrl.getQuestionsParNotion(notion);
		}else {
			questList = new ArrayList<>();
		}

		String[][] data = new String[questList.size()][6];

		for(int i = 0; i < questList.size();i++)
		{
			String typeQuestion = "";

			if(this.listQ.get(i) instanceof QCM)
			{
				if(((QCM) this.listQ.get(i)).estVraiouFaux())
					typeQuestion = "Réponse unique";
				else
					typeQuestion = "QCM";
			}else if(this.listQ.get(i) instanceof EliminationReponse){
				typeQuestion = "Elimination Réponse";
			} else {
				typeQuestion = "Association d'éléments";
			}

			data[i][0] =      questList.get(i).getEnonce();
			data[i][1] = this.listQ.get(i).getDifficulte().getNom();
			data[i][2] =      questList.get(i).getNotion().getRessourceAssociee().getNom();
			data[i][3] =      questList.get(i).getNotion().getNom();
			data[i][4] = "" + questList.get(i).getPoint();
			data[i][5] = 	typeQuestion;
		}


		DefaultTableModel model = new DefaultTableModel(data, tabEntetes);
		this.tbQuestion.setModel(model);

		this.ctrl.miseAJourFichiers();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (this.ignorerEvents) {
			return;
		}

		this.ignorerEvents = true;

		if (e.getSource() == this.mdRessources){
			if (this.mdRessources.getSelectedIndex() == 0 && ((Ressource)(this.mdRessources.getSelectedItem())).getId().equals(" ")) {
				this.mdRessources.removeItemAt(0);
			}

			Ressource ressource = (Ressource) this.mdRessources.getSelectedItem();
			this.notion = (Notion) this.mdNotions.getSelectedItem();

			this.mdRessources.setModel(new DefaultComboBoxModel<>(ctrl.getRessources().toArray(new Ressource[0])));
			this.mdNotions   .setModel(new DefaultComboBoxModel<>(ctrl.getNotionsParRessource(ressource).toArray(new Notion[0])));

			this.mdRessources.setSelectedItem(ressource);
			if(this.notion != null && ressource != null && this.notion.getRessourceAssociee().equals(ressource)){
				this.mdNotions.setSelectedItem(this.notion);
			} else {
				this.notion	= null;
			}
		} else
			if (e.getSource() == this.mdNotions){
				System.out.println("Notion");
				this.notion = (Notion) this.mdNotions.getSelectedItem();
		}

		this.ignorerEvents = false;
		this.maj();
	}
}
